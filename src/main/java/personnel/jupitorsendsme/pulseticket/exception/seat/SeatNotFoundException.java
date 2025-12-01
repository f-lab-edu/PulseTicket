package personnel.jupitorsendsme.pulseticket.exception.seat;

import lombok.Getter;

@Getter
public class SeatNotFoundException extends RuntimeException {
	private final Long eventId;
	private final Integer seatNumber;

	public SeatNotFoundException(Long eventId, Integer seatNumber) {
		super(String.format("좌석 정보 검색 안됨 - eventId: %d, seatNumber: %d", eventId, seatNumber));
		this.eventId = eventId;
		this.seatNumber = seatNumber;
	}
}
