package personnel.jupitorsendsme.pulseticket.exception.reservation;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import lombok.NonNull;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;

public class InvalidReservationStatusException extends RuntimeException implements ErrorResponse {
	private final Reservation.ReservationStatus status;
	private final HttpStatus httpStatus;

	public InvalidReservationStatusException(Reservation reservation) {
		super(String.format("유효하지 않은 상태값 - class : Reservation, status : %s", reservation.getStatus()));
		this.status = reservation.getStatus();
		this.httpStatus = HttpStatus.BAD_REQUEST;
	}

	@Override
	@NonNull
	public HttpStatusCode getStatusCode() {
		return this.httpStatus;
	}

	@Override
	@NonNull
	public ProblemDetail getBody() {
		ProblemDetail detail = ProblemDetail.forStatusAndDetail(this.httpStatus,
			String.format("유효하지 않은 예약 상태: %s", this.status));
		detail.setTitle("유효하지 않은 예약 상태");

		return detail;
	}
}
