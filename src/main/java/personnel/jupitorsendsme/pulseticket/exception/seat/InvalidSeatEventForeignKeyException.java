package personnel.jupitorsendsme.pulseticket.exception.seat;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import lombok.NonNull;
import personnel.jupitorsendsme.pulseticket.entity.Seat;

public class InvalidSeatEventForeignKeyException extends RuntimeException implements ErrorResponse {
	private final Long eventId;
	private final HttpStatus httpStatus;

	public InvalidSeatEventForeignKeyException(Seat seat) {
		super(String.format("Seat :: 유효하지 않은 외부키 - 이벤트 id 값 - eventId: %d", seat.getEvent().getId()));
		this.eventId = seat.getEvent().getId();
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
			String.format("이벤트 ID %d는 유효하지 않은 외래키임", this.eventId));
		detail.setTitle("유효하지 않은 이벤트 외래키");

		return detail;
	}
}
