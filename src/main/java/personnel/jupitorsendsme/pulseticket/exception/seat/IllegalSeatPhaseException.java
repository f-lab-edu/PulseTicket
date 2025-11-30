package personnel.jupitorsendsme.pulseticket.exception.seat;

import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.entity.Seat;

@Getter
public class IllegalSeatPhaseException extends RuntimeException {
	private final Long eventId;
	private final Integer seatNumber;
	private final Seat.SeatStatus status;

	public IllegalSeatPhaseException(Seat seat) {
		super("더 이상 좌석 상태 진행 불가");
		this.eventId = seat.getEventId();
		this.seatNumber = seat.getSeatNumber();
		this.status = seat.getStatus();
	}
}
