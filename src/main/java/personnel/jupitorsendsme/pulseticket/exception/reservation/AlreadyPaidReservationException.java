package personnel.jupitorsendsme.pulseticket.exception.reservation;

import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;

/**
 * 이미 결제된 예약에 대해 결재를 시도할때
 */
@Getter
public class AlreadyPaidReservationException extends RuntimeException {
	private final Long reservationId;

	public AlreadyPaidReservationException(Reservation reservation) {
		super("이미 결제된 예약 - reservationId: " + reservation.getId());
		this.reservationId = reservation.getId();
	}
}
