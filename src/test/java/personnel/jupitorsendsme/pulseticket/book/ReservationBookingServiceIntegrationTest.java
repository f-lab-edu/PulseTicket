package personnel.jupitorsendsme.pulseticket.book;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingRequest;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingResponse;
import personnel.jupitorsendsme.pulseticket.entity.Event;
import personnel.jupitorsendsme.pulseticket.entity.Seat;
import personnel.jupitorsendsme.pulseticket.repository.EventRepository;
import personnel.jupitorsendsme.pulseticket.repository.ReservationRepository;
import personnel.jupitorsendsme.pulseticket.repository.SeatRepository;
import personnel.jupitorsendsme.pulseticket.service.ReservationBookingService;
import personnel.jupitorsendsme.pulseticket.service.ReservationQueryService;
import personnel.jupitorsendsme.pulseticket.service.UserManagementService;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional
public class ReservationBookingServiceIntegrationTest {

	private final ReservationBookingService reservationBookingService;
	private final ReservationQueryService reservationQueryService;
	private final ReservationRepository reservationRepository;
	private final UserManagementService userManagementService;

	private final EventRepository eventRepository;
	private final SeatRepository seatRepository;

	private ReservationBookingRequest request;

	@BeforeEach
	void setUp() {
		Event testEvent = Event
			.builder()
			.name("TestConcert")
			.totalSeats(50)
			.build();
		testEvent = eventRepository.save(testEvent);

		Seat testSeat = Seat.builder()
			.event(testEvent)
			.eventId(testEvent.getId())
			.seatNumber(8)
			.status(Seat.SeatStatus.AVAILABLE)
			.build();
		testSeat = seatRepository.save(testSeat);

		request = ReservationBookingRequest
			.builder()
			.eventId(testEvent.getId())
			.seatNumber(testSeat.getSeatNumber())
			.loginId("ReservationBookingServiceIntegrationTestUserLoginId")
			.password("ReservationBookingServiceIntegrationTestUserPassword")
			.build();

		userManagementService.createUser(request);
	}

	/**
	 * 예약 테스트 <br>
	 */
	@Test
	public void bookTest() {
		ReservationBookingResponse response = reservationBookingService.book(request);

		// reservation 데이터 확인
		assertThat(reservationRepository.findById(response.getReservationId())).isNotNull();

		// seat 업데이트 됬는지 확인
		assertThat(reservationQueryService.getSeat(request).getStatus()).isEqualTo(Seat.SeatStatus.RESERVED);
	}
}
