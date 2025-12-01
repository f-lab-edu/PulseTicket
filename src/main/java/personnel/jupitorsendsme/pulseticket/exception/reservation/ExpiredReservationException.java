package personnel.jupitorsendsme.pulseticket.exception.reservation;

import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;

/**
 * 만료된 예약에 대해 결제를 시도할 때
 */
@Getter
public class ExpiredReservationException extends RuntimeException {
	private final Long reservationId;

	public ExpiredReservationException(Reservation reservation) {
		super("만료된 예약 - reservationId: " + reservation.getId());
		this.reservationId = reservation.getId();
	}
}
