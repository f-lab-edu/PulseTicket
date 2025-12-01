package personnel.jupitorsendsme.pulseticket.exception.seat;

import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.entity.Seat;

@Getter
public class SeatNotAvailableException extends RuntimeException {
	private final Long eventId;
	private final Integer seatNumber;
	private final Seat.SeatStatus status;

	public SeatNotAvailableException(Seat seat) {
		super(String.format("예약이 불가능한 좌석 - eventId: %d, seatNumber: %d, status: %s",
			seat.getEventId(), seat.getSeatNumber(), seat.getStatus()));
		this.eventId = seat.getEventId();
		this.seatNumber = seat.getSeatNumber();
		this.status = seat.getStatus();
	}
}
