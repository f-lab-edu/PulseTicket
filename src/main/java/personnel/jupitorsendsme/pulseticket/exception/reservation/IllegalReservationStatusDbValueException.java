package personnel.jupitorsendsme.pulseticket.exception.reservation;

import lombok.Getter;

@Getter
public class IllegalReservationStatusDbValueException extends RuntimeException {
	private final Integer dbValue;

	public IllegalReservationStatusDbValueException(Integer dbValue) {
		super("유효하지 않은 예약 상태값 - dbValue : " + dbValue);
		this.dbValue = dbValue;
	}
}
