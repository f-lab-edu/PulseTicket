package personnel.jupitorsendsme.pulseticket.exception.seat;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.entity.Seat;

@Getter
public class InvalidSeatEventForeignKeyException extends RuntimeException {
	private final Long eventId;
	private final HttpStatus httpStatus;

	public InvalidSeatEventForeignKeyException(Seat seat) {
		super(String.format("Seat :: 유효하지 않은 외부키 - 이벤트 id 값 - eventId: %d", seat.getEvent().getId()));
		this.eventId = seat.getEvent().getId();
		this.httpStatus = HttpStatus.BAD_REQUEST;
	}
}
