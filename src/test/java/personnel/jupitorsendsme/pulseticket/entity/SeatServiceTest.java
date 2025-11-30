package personnel.jupitorsendsme.pulseticket.entity;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.exception.InvalidForeignKeyException;
import personnel.jupitorsendsme.pulseticket.repository.EventRepository;
import personnel.jupitorsendsme.pulseticket.service.SeatService;

@DataJpaTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import(SeatService.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SeatServiceTest {

	private final SeatService seatService;
	private final EventRepository eventRepository;

	private Long testEventId;

	@BeforeAll
	void setUp() {

		Event testEvent = Event
			.builder()
			.name("TestEvent")
			.totalSeats(10)
			.build();

		testEventId = eventRepository.save(testEvent).getId();
	}

	@Test
	void createValidSeat_success() {
		Seat seat = Seat.builder()
			.eventId(testEventId)
			.seatNumber(10)
			.build();

		seatService.createValidSeat(seat);
	}

	@Test
	void createValidSeat_InvalidForeignKey() {

		Long invalidEventId = -1L;

		Seat seat = Seat.builder()
			.eventId(invalidEventId)
			.seatNumber(10)
			.build();

		assertThatThrownBy(() -> seatService.createValidSeat(seat))
			.isInstanceOf(InvalidForeignKeyException.class);
	}

	// 좌석번호 범위 및 중복 검증
	@Test
	void createValidSeat_seatNumberBelowMinimum_throws() {
		Seat seat = Seat.builder()
			.eventId(testEventId)
			.seatNumber(0)
			.build();

		assertThatThrownBy(() -> seatService.createValidSeat(seat))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void createValidSeat_seatNumberAboveTotal_throws() {
		Seat seat = Seat.builder()
			.eventId(testEventId)
			.seatNumber(11)
			.build();

		assertThatThrownBy(() -> seatService.createValidSeat(seat))
			.isInstanceOf(IllegalArgumentException.class);
	}

	@Test
	void createValidSeat_seatNumberDuplicate_throws() {
		Seat firstSeat = Seat.builder()
			.eventId(testEventId)
			.seatNumber(1)
			.build();
		seatService.createValidSeat(firstSeat);

		Seat duplicateSeat = Seat.builder()
			.eventId(testEventId)
			.seatNumber(1)
			.build();

		assertThatThrownBy(() -> seatService.createValidSeat(duplicateSeat))
			.isInstanceOf(IllegalArgumentException.class);
	}
}
