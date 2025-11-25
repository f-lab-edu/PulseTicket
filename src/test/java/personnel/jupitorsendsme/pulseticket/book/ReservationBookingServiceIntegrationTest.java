package personnel.jupitorsendsme.pulseticket.book;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingRequest;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingResponse;
import personnel.jupitorsendsme.pulseticket.entity.Seat;
import personnel.jupitorsendsme.pulseticket.repository.ReservationRepository;
import personnel.jupitorsendsme.pulseticket.service.ReservationBookingService;
import personnel.jupitorsendsme.pulseticket.service.ReservationQueryService;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class ReservationBookingServiceIntegrationTest {

	private final ReservationBookingService reservationBookingService;
	private final ReservationQueryService reservationQueryService;
	private final ReservationRepository reservationRepository;

	/**
	 * 예약 동작 테스트 <br>
	 */
	@Test
	public void bookTest() {
		ReservationBookingRequest request =
			new ReservationBookingRequest("testUser", "testUserPassword", 1L, 10);

		ReservationBookingResponse response = reservationBookingService.book(request);

		// reservation 데이터 확인
		assertThat(reservationRepository.findById(response.getReservationId())).isNotNull();

		// seat 업데이트 됬는지 확인
		assertThat(reservationQueryService.getSeat(request).getStatus()).isNotEqualTo(Seat.SeatStatus.AVAILABLE);
	}
}
