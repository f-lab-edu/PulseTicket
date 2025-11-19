package personnel.jupitorsendsme.pulseticket.service.reservationBooking;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.constants.ReservationConstants;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingRequest;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingResponse;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;
import personnel.jupitorsendsme.pulseticket.entity.Seat;
import personnel.jupitorsendsme.pulseticket.entity.User;
import personnel.jupitorsendsme.pulseticket.interfaces.ReservationBookingService;
import personnel.jupitorsendsme.pulseticket.interfaces.ReservationQueryService;
import personnel.jupitorsendsme.pulseticket.interfaces.UserManagementService;
import personnel.jupitorsendsme.pulseticket.repository.ReservationRepository;
import personnel.jupitorsendsme.pulseticket.repository.SeatRepository;
import personnel.jupitorsendsme.pulseticket.repository.UserRepository;

/**
 * 기본 좌석 예약 서비스
 */
@Service
@RequiredArgsConstructor
public class ReservationBookingServiceDefault implements ReservationBookingService {

	@Qualifier("default")
	private final UserManagementService userManagementService;
	@Qualifier("default")
	private final ReservationQueryService reservationQueryService;
	private final ReservationRepository reservationRepo;
	private final UserRepository userRepo;
	private final SeatRepository seatRepo;

	@Override
	@Transactional
	public ReservationBookingResponse book(ReservationBookingRequest request) {

		// 예약 신청자 이름
		String userId = request.getUserId();
		// 예약 신청자의 패스워드
		String password = request.getPassword();
		// 예약 신청하고자 하는 event 의 고유 id
		Long eventId = request.getEventId();
		// 예약 신청하려고 하는 좌석 번호
		Integer seatNumber = request.getSeatNumber();

		ReservationBookingResponse response = ReservationBookingResponse.builder()
			.isSuccess(false)
			.build();

		// 사용자 유효성 여부 (User Entity 조회할때와 합칠 순 없나 ?) + 시트 좌석 예약 가능 여부 (이것도 Seat Entity 조회시와 합칠 순 없나?)
		if (!userManagementService.isUserValid(userId, password) || !reservationQueryService.isSpecificSeatAvailable(
			eventId, seatNumber)) {
			return response;
		}
		
		User user = userRepo.findUserByUserId(userId).orElseThrow(RuntimeException::new);

		Seat seat = seatRepo.findSeatByEventIdAndSeatNumber(eventId, seatNumber).orElseThrow(RuntimeException::new);

		Reservation reservation = Reservation.builder()
			.user(user)
			.seat(seat)
			.status(ReservationConstants.ReservationStatus.PENDING)
			.expiresAt(LocalDateTime.now().plusHours(24))
			.build();

		Reservation created = reservationRepo.save(reservation);

		response.setReservationId(created.getId());
		response.setSuccess(true);

		return response;
	}
}
