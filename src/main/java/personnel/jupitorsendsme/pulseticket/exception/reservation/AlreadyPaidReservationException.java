package personnel.jupitorsendsme.pulseticket.exception.reservation;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import lombok.NonNull;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;

/**
 * 이미 결제된 예약에 대해 결재를 시도할때
 */
public class AlreadyPaidReservationException extends RuntimeException implements ErrorResponse {
	private final Long reservationId;
	private final HttpStatus httpStatus;

	public AlreadyPaidReservationException(Reservation reservation) {
		super("이미 결제된 예약 - reservationId: " + reservation.getId());
		this.reservationId = reservation.getId();
		this.httpStatus = HttpStatus.CONFLICT;
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
			String.format("예약 ID %d는 이미 결제됨", this.reservationId));
		detail.setTitle("이미 결제된 예약");

		return detail;
	}
}
