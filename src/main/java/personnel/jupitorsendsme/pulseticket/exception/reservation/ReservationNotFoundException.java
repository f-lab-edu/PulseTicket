package personnel.jupitorsendsme.pulseticket.exception.reservation;

import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;

@Getter
public class ReservationNotFoundException extends RuntimeException {
	private final Long reservationId;

	public ReservationNotFoundException(ReservationRequest request) {
		super("예약을 찾을 수 없음 - reservationId: " + request.getReservationId());
		this.reservationId = request.getReservationId();
	}
}
