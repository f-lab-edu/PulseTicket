package personnel.jupitorsendsme.pulseticket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingResponse;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;
import personnel.jupitorsendsme.pulseticket.entity.Seat;
import personnel.jupitorsendsme.pulseticket.entity.User;
import personnel.jupitorsendsme.pulseticket.repository.ReservationRepository;

/**
 * 기본 좌석 예약 서비스
 */
@Service
@RequiredArgsConstructor
public class ReservationBookingService {

	private final UserManagementService userManagementService;
	private final SeatManagementService seatManagementService;
	private final ReservationRepository reservationRepository;

	/**
	 * 좌석 예약
	 * @param request 예약에 필요한 정보
	 * @return 예약 성공여부 (isSuccess), 예약 번호 (reservationId)
	 */
	@Transactional
	public ReservationBookingResponse book(ReservationRequest request) {
		User user = userManagementService.getValidUser(request);
		Seat seat = seatManagementService.getAvailableSeat(request);

		Reservation created = makeReservation(user, seat);

		return ReservationBookingResponse.success(created);
	}

	private Reservation makeReservation(User user, Seat seat) {
		Reservation reserve = Reservation.reserve(user, seat);
		Reservation created = reservationRepository.save(reserve);

		seat.proceed();

		return created;
	}
}
