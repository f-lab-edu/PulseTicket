package personnel.jupitorsendsme.pulseticket.exception.seat;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import lombok.NonNull;
import personnel.jupitorsendsme.pulseticket.entity.Seat;

public class SeatNotAvailableException extends RuntimeException implements ErrorResponse {
	private final Long eventId;
	private final Integer seatNumber;
	private final Seat.SeatStatus status;
	private final HttpStatus httpStatus;

	public SeatNotAvailableException(Seat seat) {
		super(String.format("예약이 불가능한 좌석 - eventId: %d, seatNumber: %d, status: %s",
			seat.getEvent().getId(), seat.getSeatNumber(), seat.getStatus()));
		this.eventId = seat.getEvent().getId();
		this.seatNumber = seat.getSeatNumber();
		this.status = seat.getStatus();
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
			String.format("이벤트 ID %d의 좌석 번호 %d는 현재 상태(%s)로 인해 예약 불가능", this.eventId, this.seatNumber, this.status));
		detail.setTitle("예약 불가능한 좌석");

		return detail;
	}
}
