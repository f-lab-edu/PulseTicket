package personnel.jupitorsendsme.pulseticket.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.constants.ReservationConstants;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingRequest;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingResponse;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;
import personnel.jupitorsendsme.pulseticket.entity.Seat;
import personnel.jupitorsendsme.pulseticket.entity.User;
import personnel.jupitorsendsme.pulseticket.repository.ReservationRepository;
import personnel.jupitorsendsme.pulseticket.repository.SeatRepository;

/**
 * 기본 좌석 예약 서비스
 */
@Service
@RequiredArgsConstructor
@SuppressWarnings("DefaultAnnotationParam")
@Transactional(readOnly = false)
public class ReservationBookingService {

	private final UserManagementService userManagementService;
	private final ReservationQueryService reservationQueryService;
	private final ReservationRepository reservationRepository;
	private final SeatRepository seatRepository;

	/**
	 * 좌석 예약
	 * @param request 예약에 필요한 정보
	 * @return 예약 성공여부 (isSuccess), 예약 번호 (reservationId)
	 */
	public ReservationBookingResponse book(ReservationBookingRequest request) {

		Optional<User> user = userManagementService.findValidUser(request);
		Optional<Seat> seat = reservationQueryService.findAvailableSeat(request);

		if (user.isEmpty() || seat.isEmpty()) {
			return ReservationBookingResponse.builder()
				.isSuccess(false)
				.build();
		}

		Reservation reservation = Reservation.builder()
			.user(user.get())
			.seat(seat.get())
			.status(ReservationConstants.ReservationStatus.PENDING)
			.expiresAt(LocalDateTime.now().plus(ReservationConstants.RESERVATION_EXPIRATION))
			.build();
		Reservation created = reservationRepository.save(reservation);

		seat.get().setStatus(ReservationConstants.SeatStatus.RESERVED);
		seatRepository.save(seat.get());

		return ReservationBookingResponse.builder()
			.isSuccess(true)
			.reservationId(created.getId())
			.build();
	}
}
