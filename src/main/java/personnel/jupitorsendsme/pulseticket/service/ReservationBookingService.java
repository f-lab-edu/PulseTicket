package personnel.jupitorsendsme.pulseticket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingRequest;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingResponse;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;
import personnel.jupitorsendsme.pulseticket.entity.Seat;
import personnel.jupitorsendsme.pulseticket.entity.User;
import personnel.jupitorsendsme.pulseticket.repository.ReservationRepository;

/**
 * 기본 좌석 예약 서비스
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ReservationBookingService {

	private final UserManagementService userManagementService;
	private final ReservationQueryService reservationQueryService;
	private final ReservationRepository reservationRepository;

	/**
	 * 좌석 예약
	 * @param request 예약에 필요한 정보
	 * @return 예약 성공여부 (isSuccess), 예약 번호 (reservationId)
	 */
	public ReservationBookingResponse book(ReservationBookingRequest request) {
		User user = loadUser(request);
		Seat seat = loadSeat(request);

		Reservation created = makeReservation(user, seat);

		return ReservationBookingResponse.success(created);
	}

	private User loadUser(ReservationBookingRequest request) {
		return userManagementService.getValidUser(request);
	}

	private Seat loadSeat(ReservationBookingRequest request) {
		return reservationQueryService.findAvailableSeat(request);
	}

	private Reservation makeReservation(User user, Seat seat) {
		Reservation reserve = Reservation.reserve(user, seat);
		Reservation created = reservationRepository.save(reserve);

		seat.reserve();

		return created;
	}
}
