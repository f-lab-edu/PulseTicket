package personnel.jupitorsendsme.pulseticket.exception.seat;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import lombok.NonNull;
import personnel.jupitorsendsme.pulseticket.entity.Seat;

public class SeatNumberOutOfRangeException extends RuntimeException implements ErrorResponse {
	private final Seat seat;
	private final HttpStatus httpStatus;

	public SeatNumberOutOfRangeException(Seat seat) {
		super(String.format("좌석번호 범위 오류 - eventId: %d, seatNumber: %d, totalSeats: %d",
			seat.getEvent().getId(), seat.getSeatNumber(), seat.getEvent().getTotalSeats()));
		this.seat = seat;
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
			String.format("좌석 번호 %d는 전체 좌석 수 %d를 초과함",
				this.seat.getSeatNumber(), this.seat.getEvent().getTotalSeats()));
		detail.setTitle("좌석 번호 범위 초과");

		return detail;
	}
}
