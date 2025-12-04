package personnel.jupitorsendsme.pulseticket.exception.reservation;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;

/**
 * 만료된 예약에 대해 결제를 시도할 때
 */
@Getter
public class ExpiredReservationException extends RuntimeException {
	private final Long reservationId;
	private final HttpStatus httpStatus;

	public ExpiredReservationException(Reservation reservation) {
		super("만료된 예약 - reservationId: " + reservation.getId());
		this.reservationId = reservation.getId();
		this.httpStatus = HttpStatus.BAD_REQUEST;
	}
}
