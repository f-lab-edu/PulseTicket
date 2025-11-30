package personnel.jupitorsendsme.pulseticket.exception.seat;

import lombok.Getter;

@Getter
public class SeatNotFoundException extends RuntimeException {
	private final Long eventId;
	private final Integer seatNumber;

	public SeatNotFoundException(Long eventId, Integer seatNumber) {
		super("좌석 정보 검색 안됨 - 이벤트 번호 : " + eventId + ", 좌석 번호 : " + seatNumber);
		this.eventId = eventId;
		this.seatNumber = seatNumber;
	}
}
