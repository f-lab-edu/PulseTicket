package personnel.jupitorsendsme.pulseticket.exception.seat;

import lombok.Getter;

@Getter
public class IllegalSeatStatusDbValueException extends RuntimeException {
	private final Integer dbValue;

	public IllegalSeatStatusDbValueException(Integer dbValue) {
		super("유효하지 않은 좌석 상태값 : " + dbValue);
		this.dbValue = dbValue;
	}
}
