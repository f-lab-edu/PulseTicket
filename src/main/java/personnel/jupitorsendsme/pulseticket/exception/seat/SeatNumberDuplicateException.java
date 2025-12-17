package personnel.jupitorsendsme.pulseticket.exception.seat;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import lombok.NonNull;
import personnel.jupitorsendsme.pulseticket.entity.Seat;

public class SeatNumberDuplicateException extends RuntimeException implements ErrorResponse {
	private final Seat seat;
	private final HttpStatus httpStatus;

	public SeatNumberDuplicateException(Seat seat) {
		super(String.format("좌석번호 중복 - eventId: %d, seatNumber: %d",
			seat.getEvent().getId(), seat.getSeatNumber()));
		this.seat = seat;
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
			String.format("이벤트 ID %d에 좌석 번호 %d가 이미 존재함",
				this.seat.getEvent().getId(), this.seat.getSeatNumber()));
		detail.setTitle("좌석 번호 중복");

		return detail;
	}
}
