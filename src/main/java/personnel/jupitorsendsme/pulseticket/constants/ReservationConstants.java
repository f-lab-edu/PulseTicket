package personnel.jupitorsendsme.pulseticket.constants;

import java.time.Duration;

/**
 * 예약 관련 상수
 */
public class ReservationConstants {

	/**
	 * 예약 만료 시간
	 * 임시로 24시간으로 설정
	 */
	public static final Duration RESERVATION_EXPIRATION = Duration.ofHours(24);

	/**
	 * Seats 테이블의 status 컬럼에 해당하는 상태 <br>
	 * Available : 예약 가능 <br>
	 * Reserved : 예약된 상태 <br>
	 * Sold : 예약이 구매된 상태 (paid)
	 */
	public enum SeatStatus {
		AVAILABLE,
		RESERVED,
		SOLD
	}

	/**
	 * Reservation 테이블의 status 컬럼에 해당하는 상태 <br>
	 * Pending : 예약 직후 상태. 아직 결제는 안한 상태 <br>
	 * Reserved : 예약된 상태 <br>
	 * Expired : 예약은 했으나 일정시간 결제를 안해서 만료된 상태.
	 */
	public enum ReservationStatus {
		PENDING,
		CONFIRMED,
		CANCELLED,
		EXPIRED
	}
}
