package personnel.jupitorsendsme.pulseticket.service.reservationBooking;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

/**
 * 기본 좌석 예약 서비스
 */
@Service
@RequiredArgsConstructor
@SuppressWarnings("DefaultAnnotationParam")
@Transactional(readOnly = false)
public class ReservationBookingServiceDefault implements ReservationBookingService {

	@Qualifier("default")
	private final UserManagementService userManagementService;
	@Qualifier("default")
	private final ReservationQueryService reservationQueryService;
	private final ReservationRepository reservationRepository;
	private final SeatRepository seatRepository;

	@Override
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
