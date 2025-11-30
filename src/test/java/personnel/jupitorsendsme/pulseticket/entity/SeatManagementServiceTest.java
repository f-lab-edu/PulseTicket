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
import personnel.jupitorsendsme.pulseticket.service.SeatManagementService;

@DataJpaTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import(SeatManagementService.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SeatManagementServiceTest {

	private final SeatManagementService seatManagementService;
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

	/**
	 * 유효한 이벤트와 좌석번호면 저장 성공해야 함
	 */
	@Test
	void createValidSeat_success() {
		Seat seat = Seat.builder()
			.eventId(testEventId)
			.seatNumber(10)
			.build();

		seatManagementService.createValidSeat(seat);
	}

	/**
	 * 존재하지 않는 이벤트 ID면 InvalidForeignKeyException 발생해야 함
	 */
	@Test
	void createValidSeat_InvalidForeignKey() {

		Long invalidEventId = -1L;

		Seat seat = Seat.builder()
			.eventId(invalidEventId)
			.seatNumber(10)
			.build();

		assertThatThrownBy(() -> seatManagementService.createValidSeat(seat))
			.isInstanceOf(InvalidForeignKeyException.class);
	}

	/**
	 * 좌석번호가 1 미만이면 IllegalArgumentException 발생해야 함
	 */
	@Test
	void createValidSeat_seatNumberBelowMinimum_throws() {
		Seat seat = Seat.builder()
			.eventId(testEventId)
			.seatNumber(0)
			.build();

		assertThatThrownBy(() -> seatManagementService.createValidSeat(seat))
			.isInstanceOf(IllegalArgumentException.class);
	}

	/**
	 * 좌석번호가 totalSeats 초과면 IllegalArgumentException 발생해야 함
	 */
	@Test
	void createValidSeat_seatNumberAboveTotal_throws() {
		Seat seat = Seat.builder()
			.eventId(testEventId)
			.seatNumber(11)
			.build();

		assertThatThrownBy(() -> seatManagementService.createValidSeat(seat))
			.isInstanceOf(IllegalArgumentException.class);
	}

	/**
	 * 동일 이벤트에서 좌석번호가 중복되면 IllegalArgumentException 발생해야 함
	 */
	@Test
	void createValidSeat_seatNumberDuplicate_throws() {
		Seat firstSeat = Seat.builder()
			.eventId(testEventId)
			.seatNumber(1)
			.build();
		seatManagementService.createValidSeat(firstSeat);

		Seat duplicateSeat = Seat.builder()
			.eventId(testEventId)
			.seatNumber(1)
			.build();

		assertThatThrownBy(() -> seatManagementService.createValidSeat(duplicateSeat))
			.isInstanceOf(IllegalArgumentException.class);
	}
}
