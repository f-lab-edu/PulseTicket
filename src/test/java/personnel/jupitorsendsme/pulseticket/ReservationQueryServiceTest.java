package personnel.jupitorsendsme.pulseticket;

import static org.assertj.core.api.Assertions.*;

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
			.build());

		seatRepository.save(Seat.builder()
			.eventId(testEvent.getId())
			.seatNumber(1)
			.status(Seat.SeatStatus.AVAILABLE)
			.build());
	}

	@Test
	void isBookingEventAvailable_returnsTrueWhenAvailableSeatExists() {

		ReservationRequest request = ReservationRequest.builder()
			.eventId(testEvent.getId())
			.build();

		boolean available = reservationQueryService.isBookingEventAvailable(request);

		assertThat(available).isTrue();
	}
}
