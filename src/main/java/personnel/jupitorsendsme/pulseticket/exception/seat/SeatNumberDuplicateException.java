package personnel.jupitorsendsme.pulseticket.exception.seat;

import personnel.jupitorsendsme.pulseticket.entity.Seat;

import lombok.Getter;

@Getter
public class SeatNumberDuplicateException extends RuntimeException {
	private final Seat seat;

	public SeatNumberDuplicateException(Seat seat) {
		super(String.format("좌석번호 중복 - eventId: %d, seatNumber: %d", seat.getEventId(), seat.getSeatNumber()));
		this.seat = seat;
	}
}
