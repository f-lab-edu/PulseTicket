package personnel.jupitorsendsme.pulseticket.exception.seat;

import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.entity.Seat;

@Getter
public class SeatNotAvailableException extends RuntimeException {
	private final Long eventId;
	private final Integer seatNumber;
	private final Seat.SeatStatus status;

	public SeatNotAvailableException(Seat seat) {
		super("예약이 불가능한 좌석 - 이벤트 번호 : " + seat.getEventId() + ", 좌석 번호 : " + seat.getSeatNumber() + ", 좌석 상태 : "
			+ seat.getStatus());
		this.eventId = seat.getEventId();
		this.seatNumber = seat.getSeatNumber();
		this.status = seat.getStatus();
	}
}
