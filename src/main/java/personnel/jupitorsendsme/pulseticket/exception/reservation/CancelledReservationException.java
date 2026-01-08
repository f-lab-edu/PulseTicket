package personnel.jupitorsendsme.pulseticket.exception.reservation;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import lombok.NonNull;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;

/**
 * 취소된 예약에 대해 결재를 시도할 때
 */
public class CancelledReservationException extends RuntimeException implements ErrorResponse {
	private final Long reservationId;
	private final HttpStatus httpStatus;

	public CancelledReservationException(Reservation reservation) {
		super("이미 취소된 예약 - reservationId: " + reservation.getId());
		this.reservationId = reservation.getId();
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
			String.format("예약 ID %d는 이미 취소됨", this.reservationId));
		detail.setTitle("취소된 예약");

		return detail;
	}
}
