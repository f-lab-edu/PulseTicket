package personnel.jupitorsendsme.pulseticket.unit;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.entity.Event;
import personnel.jupitorsendsme.pulseticket.entity.Seat;
import personnel.jupitorsendsme.pulseticket.exception.seat.InvalidSeatEventForeignKeyException;
import personnel.jupitorsendsme.pulseticket.exception.seat.SeatNumberDuplicateException;
import personnel.jupitorsendsme.pulseticket.exception.seat.SeatNumberOutOfRangeException;
import personnel.jupitorsendsme.pulseticket.repository.EventRepository;
import personnel.jupitorsendsme.pulseticket.service.SeatManagementService;

@DataJpaTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import(SeatManagementService.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SeatManagementServiceTest {

	private final SeatManagementService seatManagementService;
	private final EventRepository eventRepository;

	private Event testEvent;

	@BeforeAll
	void setUp() {
		testEvent = Event
			.builder()
			.name("TestEvent")
			.totalSeats(10)
			.ticketPrice(BigDecimal.valueOf(15000L))
			.build();

		testEvent = eventRepository.save(testEvent);
	}

	/**
	 * 좌석 생성 기본 테스트
	 */
	@Test
	void createSeat_success() {
		Seat seat = Seat.builder()
			.event(testEvent)
			.seatNumber(10)
			.build();

		seatManagementService.createSeat(seat);
	}

	/**
	 * 좌석 생성 - F key 위반
	 */
	@Test
	void createSeat_InvalidForeignKey() {
		Event invalidEvent = Event.builder().id(-1L).build();

		Seat seat = Seat.builder()
			.event(invalidEvent)
			.seatNumber(10)
			.build();

		assertThatThrownBy(() -> seatManagementService.createSeat(seat))
			.isInstanceOf(InvalidSeatEventForeignKeyException.class);
	}

	/**
	 * 좌석 생성 - 좌석번호가 1 미만
	 */
	@Test
	void createSeat_seatNumberBelowMinimum_throws() {
		Seat seat = Seat.builder()
			.event(testEvent)
			.seatNumber(0)
			.build();

		assertThatThrownBy(() -> seatManagementService.createSeat(seat))
			.isInstanceOf(SeatNumberOutOfRangeException.class);
	}

	/**
	 * 좌석 생성 - 좌석 번호 초과
	 */
	@Test
	void createSeat_seatNumberAboveTotal_throws() {
		Seat seat = Seat.builder()
			.event(testEvent)
			.seatNumber(11)
			.build();

		assertThatThrownBy(() -> seatManagementService.createSeat(seat))
			.isInstanceOf(SeatNumberOutOfRangeException.class);
	}

	/**
	 * 좌석 생성 - 중복 좌석일 경우
	 */
	@Test
	void createSeat_seatNumberDuplicate_throws() {
		Seat firstSeat = Seat.builder()
			.event(testEvent)
			.seatNumber(1)
			.build();
		seatManagementService.createSeat(firstSeat);

		Seat duplicateSeat = Seat.builder()
			.event(testEvent)
			.seatNumber(1)
			.build();

		assertThatThrownBy(() -> seatManagementService.createSeat(duplicateSeat))
			.isInstanceOf(SeatNumberDuplicateException.class);
	}
}
