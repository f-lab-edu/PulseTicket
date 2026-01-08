package personnel.jupitorsendsme.pulseticket.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.dto.ReservationQueryResponse;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;
import personnel.jupitorsendsme.pulseticket.entity.Seat;
import personnel.jupitorsendsme.pulseticket.exception.reservation.AlreadyPaidReservationException;
import personnel.jupitorsendsme.pulseticket.exception.reservation.CancelledReservationException;
import personnel.jupitorsendsme.pulseticket.exception.reservation.ExpiredReservationException;
import personnel.jupitorsendsme.pulseticket.exception.reservation.InvalidReservationStatusException;
import personnel.jupitorsendsme.pulseticket.exception.reservation.ReservationNotFoundException;
import personnel.jupitorsendsme.pulseticket.repository.ReservationRepository;
import personnel.jupitorsendsme.pulseticket.repository.SeatRepository;

@Service
@RequiredArgsConstructor
public class ReservationQueryService {

	private final SeatRepository seatRepository;
	private final ReservationRepository reservationRepository;

	/**
	 * 특정 이벤트에 대한 예약 가능 여부. <br>
	 * 좌석이 일단 비어있기만 하면 (하나라도 예약 가능한 상태라면) true <br>
	 * @param request 확인하려는 이벤트 id 가 담긴 request <br>
	 * @return 예약 가능 여부 <br>
	 */
	@Transactional(readOnly = true)
	public boolean isBookingEventAvailable(ReservationRequest request) {
		return seatRepository.existsByEvent_IdAndStatus(request.getEventId(), Seat.SeatStatus.AVAILABLE);
	}

	/**
	 * 특정 사용자에 대한 예약 목록 조회 <br>
	 * @param request 확인하고자 하는 사용자의 id 와 password 가 담긴 객체 <br>
	 * @return 예약 목록이 담긴 DTO <br>
	 */
	@Transactional(readOnly = true)
	public List<ReservationQueryResponse> inquiryUserReservations(ReservationRequest request) {
		List<Reservation> reservations = reservationRepository.findByUser_LoginId(request.getLoginId());

		return ReservationQueryResponse.from(reservations);
	}

	@Transactional(readOnly = true)
	public Reservation getReservation(ReservationRequest request) {
		return reservationRepository.findById(request.getReservationId())
			.orElseThrow(() -> new ReservationNotFoundException(request));
	}

	@Transactional(readOnly = true)
	public Reservation getPayableReservation(ReservationRequest request) {
		Reservation reservation = this.getReservation(request);

		// 예약 상태별 결제 가능 여부 검증
		switch (reservation.getStatus()) {
			case PENDING -> {
				return reservation;
			}
			case CONFIRMED -> throw new AlreadyPaidReservationException(reservation);
			case CANCELLED -> throw new CancelledReservationException(reservation);
			case EXPIRED -> throw new ExpiredReservationException(reservation);
			default -> throw new InvalidReservationStatusException(reservation);
		}
	}
}
