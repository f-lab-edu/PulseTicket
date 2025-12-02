package personnel.jupitorsendsme.pulseticket.exception.seat;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.entity.Seat;

@Getter
public class SeatNumberOutOfRangeException extends RuntimeException {
	private final Seat seat;
	private final HttpStatus httpStatus;

	public SeatNumberOutOfRangeException(Seat seat) {
		super(String.format("좌석번호 범위 오류 - eventId: %d, seatNumber: %d, totalSeats: %d",
			seat.getEventId(), seat.getSeatNumber(), seat.getEvent() != null ? seat.getEvent().getTotalSeats() : null));
		this.seat = seat;
		this.httpStatus = HttpStatus.BAD_REQUEST;
	}
}
