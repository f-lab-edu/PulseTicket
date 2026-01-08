package personnel.jupitorsendsme.pulseticket.exception.reservation;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import lombok.NonNull;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;

public class ReservationNotFoundException extends RuntimeException implements ErrorResponse {
	private final Long reservationId;
	private final HttpStatus httpStatus;

	public ReservationNotFoundException(ReservationRequest request) {
		super("예약을 찾을 수 없음 - reservationId: " + request.getReservationId());
		this.reservationId = request.getReservationId();
		this.httpStatus = HttpStatus.NOT_FOUND;
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
			String.format("예약 ID %d를 찾을 수 없음", this.reservationId));
		detail.setTitle("예약을 찾을 수 없음");

		return detail;
	}
}
