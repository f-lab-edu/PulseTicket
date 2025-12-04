package personnel.jupitorsendsme.pulseticket.unit;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;
import personnel.jupitorsendsme.pulseticket.entity.Event;
import personnel.jupitorsendsme.pulseticket.entity.Seat;
import personnel.jupitorsendsme.pulseticket.repository.EventRepository;
import personnel.jupitorsendsme.pulseticket.repository.SeatRepository;
import personnel.jupitorsendsme.pulseticket.service.ReservationQueryService;

@DataJpaTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import(ReservationQueryService.class)
public class ReservationQueryServiceTest {
	private final SeatRepository seatRepository;
	private final EventRepository eventRepository;
	private final ReservationQueryService reservationQueryService;
	private Event testEvent;

	@BeforeEach
	void setUp() {
		testEvent = eventRepository.save(Event.builder()
			.name("Test Event")
			.totalSeats(10)
			.ticketPrice(BigDecimal.valueOf(15000L))
			.build());

		seatRepository.save(Seat.builder()
			.event(testEvent)
			.seatNumber(1)
			.status(Seat.SeatStatus.AVAILABLE)
			.build());
	}

	/**
	 * 이벤트가 예약 가능한지에 대한 기본 테스트
	 */
	@Test
	void isBookingEventAvailable_success() {

		ReservationRequest request = ReservationRequest.builder()
			.eventId(testEvent.getId())
			.build();

		boolean available = reservationQueryService.isBookingEventAvailable(request);

		assertThat(available).isTrue();
	}
}
