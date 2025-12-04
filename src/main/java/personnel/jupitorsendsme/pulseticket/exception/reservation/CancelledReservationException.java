package personnel.jupitorsendsme.pulseticket.exception.reservation;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;

/**
 * 취소된 예약에 대해 결재를 시도할 때
 */
@Getter
public class CancelledReservationException extends RuntimeException {
	private final Long reservationId;
	private final HttpStatus httpStatus;

	public CancelledReservationException(Reservation reservation) {
		super("이미 취소된 예약 - reservationId: " + reservation.getId());
		this.reservationId = reservation.getId();
		this.httpStatus = HttpStatus.BAD_REQUEST;
	}
}
