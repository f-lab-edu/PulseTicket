package personnel.jupitorsendsme.pulseticket.exception.seat;

import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;

@Getter
public class SeatNotFoundException extends RuntimeException {
	private final Long eventId;
	private final Integer seatNumber;

	public SeatNotFoundException(ReservationRequest request) {
		super(
			String.format("좌석 정보 검색 안됨 - eventId: %d, seatNumber: %d", request.getEventId(), request.getSeatNumber()));
		this.eventId = request.getEventId();
		this.seatNumber = request.getSeatNumber();
	}
}
