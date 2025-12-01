package personnel.jupitorsendsme.pulseticket.exception;

import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;
import personnel.jupitorsendsme.pulseticket.entity.Seat;

@Getter
public class InvalidStatusException extends RuntimeException {
	private final String entityType;
	private final String invalidStatus;

	public InvalidStatusException(Reservation reservation) {
		this(Reservation.class.getSimpleName(), reservation.getStatus().name());
	}

	public InvalidStatusException(Seat seat) {
		this(Seat.class.getSimpleName(), seat.getStatus().name());
	}

	public InvalidStatusException(String entityType, String invalidStatus) {
		super(String.format("유효하지 않은 상태값 - %s: %s", entityType, invalidStatus));
		this.entityType = entityType;
		this.invalidStatus = invalidStatus;
	}
}
