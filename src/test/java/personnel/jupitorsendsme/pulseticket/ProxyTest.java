package personnel.jupitorsendsme.pulseticket;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.entity.Event;
import personnel.jupitorsendsme.pulseticket.entity.Seat;
import personnel.jupitorsendsme.pulseticket.repository.EventRepository;
import personnel.jupitorsendsme.pulseticket.repository.SeatRepository;

@DataJpaTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ProxyTest {

	private final TestEntityManager entityManager;
	private final EventRepository eventRepository;
	private final SeatRepository seatRepository;

	/**
	 * getId() 호출 시 프록시가 초기화되는지 확인
	 */
	@Test
	void testProxyGetId() {
		// 준비
		Event event = eventRepository.save(Event.builder()
			.name("Test Event")
			.totalSeats(10)
			.ticketPrice(BigDecimal.valueOf(10000))
			.build());

		Seat seat = seatRepository.save(Seat.builder()
			.event(event)
			.seatNumber(1)
			.build());

		entityManager.flush();
		entityManager.clear();

		// 실행
		Seat foundSeat = seatRepository.findById(seat.getId()).orElseThrow(() -> new RuntimeException("Empty"));

		System.out.println("=== Seat 조회 완료 ===");
		System.out.println("Event 프록시 클래스: " + foundSeat.getEvent().getClass());

		System.out.println("=== getId() 호출 시작 ===");
		Long eventId = foundSeat.getEvent().getId();
		System.out.println("=== getId() 호출 완료 ===");
		System.out.println("Event ID: " + eventId);
		System.out.println("=== getTotalSeats() 호출 시작 ===");
		Integer totalSeats = foundSeat.getEvent().getTotalSeats();
		System.out.println("=== getTotalSeats() 호출 완료 ===");
		System.out.println("total Seats: " + totalSeats);

		assertThat(eventId).isEqualTo(event.getId());
	}
}
