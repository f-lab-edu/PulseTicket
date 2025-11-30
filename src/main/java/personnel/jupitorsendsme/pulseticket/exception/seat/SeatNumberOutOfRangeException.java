package personnel.jupitorsendsme.pulseticket.exception.seat;

import lombok.Getter;

@Getter
public class SeatNumberOutOfRangeException extends RuntimeException {
	private final Long eventId;
	private final Integer seatNumber;
	private final Integer totalSeats;

	public SeatNumberOutOfRangeException(Long eventId, Integer seatNumber, Integer totalSeats) {
		super("좌석번호 범위 오류 - eventId: " + eventId + ", seatNumber: " + seatNumber + ", totalSeats: " + totalSeats);
		this.eventId = eventId;
		this.seatNumber = seatNumber;
		this.totalSeats = totalSeats;
	}
}
