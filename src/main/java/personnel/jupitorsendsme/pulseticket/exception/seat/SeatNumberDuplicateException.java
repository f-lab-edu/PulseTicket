package personnel.jupitorsendsme.pulseticket.exception.seat;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.entity.Seat;

@Getter
public class SeatNumberDuplicateException extends RuntimeException {
	private final Seat seat;
	private final HttpStatus httpStatus;

	public SeatNumberDuplicateException(Seat seat) {
		super(String.format("좌석번호 중복 - eventId: %d, seatNumber: %d",
			seat.getEventId(), seat.getSeatNumber()));
		this.seat = seat;
		this.httpStatus = HttpStatus.CONFLICT;
	}
}
