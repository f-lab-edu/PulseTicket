package personnel.jupitorsendsme.pulseticket.exception.seat;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;

@Getter
public class SeatNotFoundException extends RuntimeException {
	private final Long eventId;
	private final Integer seatNumber;
	private final HttpStatus httpStatus;

	public SeatNotFoundException(ReservationRequest request) {
		super(String.format("좌석 정보 검색 안됨 - eventId: %d, seatNumber: %d",
			request.getEventId(), request.getSeatNumber()));
		this.eventId = request.getEventId();
		this.seatNumber = request.getSeatNumber();
		this.httpStatus = HttpStatus.NOT_FOUND;
	}
}
