package personnel.jupitorsendsme.pulseticket.exception.seat;

import lombok.Getter;

@Getter
public class SeatNumberDuplicateException extends RuntimeException {
	private final Long eventId;
	private final Integer seatNumber;

	public SeatNumberDuplicateException(Long eventId, Integer seatNumber) {
		super("좌석번호 중복 - eventId: " + eventId + ", seatNumber: " + seatNumber);
		this.eventId = eventId;
		this.seatNumber = seatNumber;
	}
}
