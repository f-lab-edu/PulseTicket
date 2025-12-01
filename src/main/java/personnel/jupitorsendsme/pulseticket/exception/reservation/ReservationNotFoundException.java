package personnel.jupitorsendsme.pulseticket.exception.reservation;

import lombok.Getter;

@Getter
public class ReservationNotFoundException extends RuntimeException {
	private final Long reservationId;

	public ReservationNotFoundException(Long reservationId) {
		super("예약을 찾을 수 없음 - reservationId: " + reservationId);
		this.reservationId = reservationId;
	}
}
