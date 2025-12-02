package personnel.jupitorsendsme.pulseticket.exception.seat;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.entity.Seat;

@Getter
public class IllegalSeatPhaseException extends RuntimeException {
	private final Long eventId;
	private final Integer seatNumber;
	private final Seat.SeatStatus status;
	private final HttpStatus httpStatus;

	public IllegalSeatPhaseException(Seat seat) {
		super(String.format("더 이상 좌석 상태 진행 불가 - eventId: %d, seatNumber: %d, currentStatus: %s",
			seat.getEventId(), seat.getSeatNumber(), seat.getStatus()));
		this.eventId = seat.getEventId();
		this.seatNumber = seat.getSeatNumber();
		this.status = seat.getStatus();
		this.httpStatus = HttpStatus.BAD_REQUEST;
	}
}
