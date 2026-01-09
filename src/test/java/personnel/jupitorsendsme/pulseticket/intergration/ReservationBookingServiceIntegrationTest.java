package personnel.jupitorsendsme.pulseticket.intergration;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingResponse;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;
import personnel.jupitorsendsme.pulseticket.entity.Event;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;
import personnel.jupitorsendsme.pulseticket.entity.Seat;
import personnel.jupitorsendsme.pulseticket.entity.User;
import personnel.jupitorsendsme.pulseticket.repository.EventRepository;
import personnel.jupitorsendsme.pulseticket.repository.ReservationRepository;
import personnel.jupitorsendsme.pulseticket.repository.SeatRepository;
import personnel.jupitorsendsme.pulseticket.repository.UserRepository;
import personnel.jupitorsendsme.pulseticket.service.HashingServiceArgon2id;
import personnel.jupitorsendsme.pulseticket.service.ReservationBookingService;
import personnel.jupitorsendsme.pulseticket.service.ReservationQueryService;
import personnel.jupitorsendsme.pulseticket.service.SeatManagementService;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional
public class ReservationBookingServiceIntegrationTest {

	final String validTestUserPassword = "testUserPassword";
	private final ReservationBookingService reservationBookingService;
	private final SeatManagementService seatManagementService;
	private final ReservationQueryService reservationQueryService;
	private final HashingServiceArgon2id passwordHashingService;
	private final UserRepository userRepository;
	private final EventRepository eventRepository;
	private final SeatRepository seatRepository;
	private final ReservationRepository reservationRepository;

	/**
	 * 예약 테스트
	 */
	@Test
	@DisplayName("좌석 예약시 reservation 데이터가 1개 생기고, 해당하는 Seat 데이터의 상태가 RESERVED")
	public void bookTest() {
		User testUser = createTestUser();
		Event testEvent = createTestEvent();
		Seat testSeat = createTestSeat(testEvent);
		ReservationRequest testRequest = createValidTestReservationRequest(testUser, testEvent, testSeat);

		// 테스트 시작
		ReservationBookingResponse response = reservationBookingService.book(testRequest);

		// reservation 데이터 확인
		assertThat(reservationRepository.findById(response.getReservationId())).isPresent();
		Iterable<Reservation> all = reservationRepository.findAll();
		assertThat(all).hasSize(1);

		// seat 업데이트 됬는지 확인
		assertThat(seatManagementService.getSeat(testRequest).getStatus()).isEqualTo(Seat.SeatStatus.RESERVED);
	}

	@Test
	@DisplayName("예약 취소시 reservation 의 상태가 cancel 로 업데이트 되고 해당하는 Seat 의 상태가 Available 로 업데이트")
	public void cancelTest() {
		User testUser = createTestUser();
		Event testEvent = createTestEvent();
		Seat testSeat = createTestSeat(testEvent);
		Reservation testReservation = createTestReservation(testUser, testEvent, testSeat);
		ReservationRequest testRequest = createValidTestReservationRequest(testUser, testEvent, testSeat,
			testReservation);

		reservationBookingService.cancel(testRequest);

		assertThat(reservationQueryService.getReservation(testRequest).getStatus()).isEqualTo(
			Reservation.ReservationStatus.CANCELLED);
		assertThat(seatManagementService.getSeat(testRequest).getStatus()).isEqualTo(Seat.SeatStatus.AVAILABLE);
	}

	User createTestUser() {
		User user = User
			.builder()
			.loginId("testUserId")
			.passwordHash(passwordHashingService.hash(validTestUserPassword))
			.build();

		return userRepository.save(user);
	}

	Event createTestEvent() {
		Event testEvent = Event
			.builder()
			.name("TestConcert")
			.totalSeats(50)
			.ticketPrice(BigDecimal.valueOf(5000))
			.build();
		return eventRepository.save(testEvent);
	}

	Seat createTestSeat(Event testEvent) {
		Seat testSeat = Seat.builder()
			.event(testEvent)
			.seatNumber(10)
			.status(Seat.SeatStatus.AVAILABLE)
			.build();
		return seatRepository.save(testSeat);
	}

	Reservation createTestReservation(User testUser, Event testEvent, Seat testSeat) {
		Reservation reservation = Reservation
			.builder()
			.user(testUser)
			.event(testEvent)
			.seat(testSeat)
			.expiresAt(LocalDateTime.now().plus(Reservation.RESERVATION_EXPIRATION))
			.build();
		return reservationRepository.save(reservation);
	}

	ReservationRequest createValidTestReservationRequest(User testUser, Event testEvent, Seat testSeat) {
		return ReservationRequest
			.builder()
			.loginId(testUser.getLoginId())
			.password(validTestUserPassword)
			.eventId(testEvent.getId())
			.seatNumber(testSeat.getSeatNumber())
			.build();
	}

	ReservationRequest createValidTestReservationRequest(User testUser, Event testEvent, Seat testSeat,
		Reservation testReservation) {
		return ReservationRequest
			.builder()
			.loginId(testUser.getLoginId())
			.password(validTestUserPassword)
			.eventId(testEvent.getId())
			.seatNumber(testSeat.getSeatNumber())
			.reservationId(testReservation.getId())
			.build();
	}
}
