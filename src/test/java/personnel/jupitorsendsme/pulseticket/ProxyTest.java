package personnel.jupitorsendsme.pulseticket;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.entity.Event;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;
import personnel.jupitorsendsme.pulseticket.entity.Seat;
import personnel.jupitorsendsme.pulseticket.entity.User;
import personnel.jupitorsendsme.pulseticket.repository.EventRepository;
import personnel.jupitorsendsme.pulseticket.repository.ReservationRepository;
import personnel.jupitorsendsme.pulseticket.repository.SeatRepository;
import personnel.jupitorsendsme.pulseticket.repository.UserRepository;

@DataJpaTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ProxyTest {

	private final TestEntityManager entityManager;
	private final EventRepository eventRepository;
	private final SeatRepository seatRepository;
	private final UserRepository userRepository;
	private final ReservationRepository reservationRepository;

	@Test
	@DisplayName("Seat 의 Entity 필드의 getId() 를 호출시 select 문을 호출하는 지,"
		+ "그리고 Event 의 다른 속성을 조회 시 Select 문을 호출하는지 확인")
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

	@Test
	@DisplayName("Reservation.reserve() 메서드에서 seat.getEvent() 호출 시"
		+ "Event 프록시가 저장되는지, 아니면 실제 Event 조회를 위한 SELECT 문이 실행되는지 확인")
	void testReservationSaveWithEventProxy() {
		// User, Event, Seat 생성 및 저장
		User user = userRepository.save(User.builder()
			.loginId("testuser")
			.passwordHash("hash")
			.build());

		Event event = eventRepository.save(Event.builder()
			.name("Concert Event")
			.totalSeats(100)
			.ticketPrice(BigDecimal.valueOf(50000))
			.build());

		Seat seat = seatRepository.save(Seat.builder()
			.event(event)
			.seatNumber(10)
			.build());

		// 영속성 컨텍스트 초기화
		entityManager.flush();
		entityManager.clear();

		// Seat 조회 (Event는 LAZY 로딩이므로 프록시로 로드됨)
		Seat foundSeat = seatRepository.findById(seat.getId())
			.orElseThrow(() -> new RuntimeException("Seat not found"));

		User foundUser = userRepository.findById(user.getId())
			.orElseThrow(() -> new RuntimeException("User not found"));

		System.out.println("=== Seat 조회 완료 ===");
		System.out.println("Seat의 Event 프록시 클래스: " + foundSeat.getEvent().getClass());

		// Reservation.reserve() 호출
		System.out.println("\n=== Reservation.reserve() 호출 시작 ===");
		System.out.println("seat.getEvent() 호출 전 - SELECT 문 실행 여부 확인");

		// reserve 메서드 내부에서 seat.getEvent() 호출
		Reservation reservation = Reservation.reserve(foundUser, foundSeat);

		System.out.println("=== Reservation.reserve() 호출 완료 ===");
		System.out.println("Reservation의 Event 클래스: " + reservation.getEvent().getClass());

		// Event ID 접근 시 프록시 초기화 여부 확인
		System.out.println("\n=== Event ID 접근 시작 ===");
		Long eventId = reservation.getEvent().getId();
		System.out.println("=== Event ID 접근 완료 ===");
		System.out.println("Event ID: " + eventId);

		// 저장시 Event 문 호출 확인
		System.out.println("\n=== reservation 저장 시작 ===");
		reservationRepository.save(reservation);
		System.out.println("\n=== reservation 저장 완료 ===");

		// 검증
		assertThat(reservation.getEvent()).isNotNull();
		assertThat(reservation.getSeat()).isEqualTo(foundSeat);
		assertThat(reservation.getUser()).isEqualTo(foundUser);
		assertThat(eventId).isEqualTo(event.getId());
	}
}
