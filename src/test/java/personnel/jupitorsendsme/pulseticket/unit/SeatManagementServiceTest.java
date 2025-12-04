package personnel.jupitorsendsme.pulseticket.unit;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
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
import personnel.jupitorsendsme.pulseticket.repository.SeatRepository;
import personnel.jupitorsendsme.pulseticket.service.SeatManagementService;

@DataJpaTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import(SeatManagementService.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SeatManagementServiceTest {

	private final SeatManagementService seatManagementService;
	private final EventRepository eventRepository;
	private final SeatRepository seatRepository;
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
	@DisplayName("좌석생성이 성공하면 Seat 레코드가 생성되고, seatManagementService.createSeat 이며 생성된 Seat 데이터가 1개.")
	void createSeat_success() {
		Seat seat = Seat.builder()
			.event(testEvent)
			.seatNumber(10)
			.build();

		assertThat(seatManagementService.createSeat(seat)).isNotNull();
		Iterable<Seat> all = seatRepository.findAll();
		assertThat(all).isNotEmpty();
		assertThat(all).hasSize(1);
	}

	/**
	 * 좌석 생성 - F key 위반
	 */
	@Test
	@DisplayName("좌석생성시 ")
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
	@DisplayName("좌석생성시 좌석을 생성하려는 좌석번호가 0이하인 경우 SeatNumberOutOfRangeException 발생")
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
	@DisplayName("좌석생성시 좌석을 생성하려는 번호가 Event 의 최대 좌석번호를 넘기는 경우 SeatNumberOutOfRangeException 발생")
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
	@DisplayName("좌석생성시 해당 좌석이 이미 생성되어 있는 경우 SeatNumberDuplicateException 발생")
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
