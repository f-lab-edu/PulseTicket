package personnel.jupitorsendsme.pulseticket.intergration;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;
import personnel.jupitorsendsme.pulseticket.entity.Event;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;
import personnel.jupitorsendsme.pulseticket.entity.Seat;
import personnel.jupitorsendsme.pulseticket.entity.User;
import personnel.jupitorsendsme.pulseticket.exception.payment.InsufficientPaymentException;
import personnel.jupitorsendsme.pulseticket.exception.reservation.AlreadyPaidReservationException;
import personnel.jupitorsendsme.pulseticket.exception.reservation.CancelledReservationException;
import personnel.jupitorsendsme.pulseticket.exception.reservation.ExpiredReservationException;
import personnel.jupitorsendsme.pulseticket.exception.reservation.ReservationNotFoundException;
import personnel.jupitorsendsme.pulseticket.exception.seat.SeatNotFoundException;
import personnel.jupitorsendsme.pulseticket.exception.user.InvalidCredentialException;
import personnel.jupitorsendsme.pulseticket.exception.user.UserNotFoundException;
import personnel.jupitorsendsme.pulseticket.repository.EventRepository;
import personnel.jupitorsendsme.pulseticket.repository.ReservationRepository;
import personnel.jupitorsendsme.pulseticket.repository.SeatRepository;
import personnel.jupitorsendsme.pulseticket.repository.UserRepository;
import personnel.jupitorsendsme.pulseticket.service.EventManagementService;
import personnel.jupitorsendsme.pulseticket.service.HashingServiceArgon2id;
import personnel.jupitorsendsme.pulseticket.service.PayManagementService;
import personnel.jupitorsendsme.pulseticket.service.ReservationQueryService;
import personnel.jupitorsendsme.pulseticket.service.UserManagementService;

@DataJpaTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({PayManagementService.class, HashingServiceArgon2id.class, ReservationQueryService.class,
	UserManagementService.class, EventManagementService.class, TestEntityManager.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PayManagementServiceTest {

	private final PayManagementService payManagementService;
	private final HashingServiceArgon2id hashingServiceArgon2id;
	// TODO : 왜 지금 테스트에서 testEntityManager 로 flush, clear 를 해야하는지 알아내야 한다.
	private final TestEntityManager testEntityManager;
	private final UserRepository userRepository;
	private final EventRepository eventRepository;
	private final SeatRepository seatRepository;
	private final ReservationRepository reservationRepository;

	private User testUser;
	private Event testEvent;
	private Seat testSeat;
	private Reservation testReservation;

	private final String testUserLoginId = "test-user";
	private final String testUserPassword = "test-user-password";
	String testUserPasswordHash;
	private final BigDecimal testTicketPrice = BigDecimal.valueOf(50000);
	ReservationRequest validRequest;

	@BeforeEach
	void setUp() {
		testUserPasswordHash = hashingServiceArgon2id.hash(testUserPassword);

		reservationRepository.deleteAll();
		seatRepository.deleteAll();
		eventRepository.deleteAll();
		userRepository.deleteAll();

		// 유효한 사용자
		this.testUser = this.userRepository.save(
			User.builder()
				.loginId(testUserLoginId)
				.passwordHash(testUserPasswordHash)
				.build()
		);

		// 테스트 이벤트, 좌석, 예약 생성
		this.testEvent = this.eventRepository.save(
			Event.builder()
				.name("Test Event")
				.totalSeats(10)
				.ticketPrice(testTicketPrice)
				.build()
		);

		this.testSeat = this.seatRepository.save(
			Seat.builder()
				.event(this.testEvent)
				.eventId(this.testEvent.getId())
				.seatNumber(1)
				.status(Seat.SeatStatus.AVAILABLE)
				.build()
		);

		this.testReservation = this.reservationRepository.save(
			Reservation.reserve(this.testUser, this.testSeat)
		);

		// flush만 하고 clear는 안 함 → 엔티티들이 영속 상태 유지
		testEntityManager.flush();

		validRequest = createValidReservationRequest();
	}

	/**
	 * 예약 결제 - 유효하지 않는 사용자
	 */
	@Test
	void payReservation_userNotFound() {
		ReservationRequest request = createValidReservationRequest();
		request.setLoginId("notValid");
		request.setPassword("notValid");

		assertThatThrownBy(() -> payManagementService.payReservation(request))
			.isInstanceOf(UserNotFoundException.class);
	}

	@Test
	void payReservation_unauthorizedUser() {
		ReservationRequest request = createValidReservationRequest();
		request.setLoginId(testUserLoginId);
		request.setPassword("notValid");

		assertThatThrownBy(() -> payManagementService.payReservation(request))
			.isInstanceOf(InvalidCredentialException.class);
	}

	/**
	 * 예약 결제 - 예약 정보가 유효하지 않을때
	 */
	@Test
	void payReservation_invalidReservation() {
		ReservationRequest request = createValidReservationRequest();
		request.setReservationId(-1L);

		assertThatThrownBy(() -> payManagementService.payReservation(request))
			.isInstanceOf(ReservationNotFoundException.class);
	}

	/**
	 * 예약 결제 - 이미 결제 된 경우
	 */
	@Test
	void payReservation_alreadyPaid() {
		testReservation.confirm();

		assertThatThrownBy(() -> payManagementService.payReservation(validRequest))
			.isInstanceOf(AlreadyPaidReservationException.class);
	}

	/**
	 * 예약 결제 - 이미 취소된 예약
	 */
	@Test
	void payReservation_cancelledReservation() {
		testReservation.cancel();

		assertThatThrownBy(() -> payManagementService.payReservation(validRequest))
			.isInstanceOf(CancelledReservationException.class);
	}

	/**
	 * 예약 결제 - 이미 만료된 예약
	 */
	@Test
	void payReservation_expiredReservation() {
		testReservation.expire();

		assertThatThrownBy(() -> payManagementService.payReservation(validRequest))
			.isInstanceOf(ExpiredReservationException.class);
	}

	/**
	 * 예약 결제 - 티켓 값이 모자랄때
	 */
	@Test
	void payReservation_notEnoughMoney() {
		// event 로딩을 위해 clear 필요
		testSeat.proceed();
		testEntityManager.flush();
		testEntityManager.clear();

		ReservationRequest request = createValidReservationRequest();
		request.setPaymentAmount(BigDecimal.valueOf(-10000));

		assertThatThrownBy(() -> payManagementService.payReservation(request))
			.isInstanceOf(InsufficientPaymentException.class);
	}

	/**
	 * 예약 결제 기본 테스트
	 */
	@Test
	void payReservation_success() {
		// event 로딩을 위해 clear 필요, 좌석 상태 변경 (AVAILABLE -> RESERVED)
		testSeat.proceed();
		testEntityManager.flush();
		testEntityManager.clear();

		// 메서드 실행
		payManagementService.payReservation(validRequest);

		// 예약 상태 업데이트 확인
		Reservation reservation = reservationRepository.findById(validRequest.getReservationId())
			.orElseThrow(() -> new ReservationNotFoundException(validRequest));
		assertThat(reservation.getStatus()).isEqualTo(Reservation.ReservationStatus.CONFIRMED);

		// 좌석 상태 업데이트 확인
		Seat updatedSeat = seatRepository.findByEvent_IdAndSeatNumber(validRequest.getEventId(),
				validRequest.getSeatNumber())
			.orElseThrow(
				() -> new SeatNotFoundException(validRequest)
			);
		assertThat(updatedSeat.getStatus()).isEqualTo(Seat.SeatStatus.SOLD);
	}

	ReservationRequest createValidReservationRequest() {
		return ReservationRequest
			.builder()
			.eventId(testEvent.getId())
			.seatNumber(testSeat.getSeatNumber())
			.loginId(testUser.getLoginId())
			.password(testUserPassword)
			.reservationId(testReservation.getId())
			.paymentAmount(testEvent.getTicketPrice())
			.build();
	}
}
