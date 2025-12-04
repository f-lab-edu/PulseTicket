package personnel.jupitorsendsme.pulseticket.exception.reservation;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;

@Getter
public class InvalidReservationStatusException extends RuntimeException {
	private final Reservation.ReservationStatus status;
	private final HttpStatus httpStatus;

	public InvalidReservationStatusException(Reservation reservation) {
		super(String.format("유효하지 않은 상태값 - class : Reservation, status : %s", reservation.getStatus()));
		this.status = reservation.getStatus();
		this.httpStatus = HttpStatus.BAD_REQUEST;
	}
}
