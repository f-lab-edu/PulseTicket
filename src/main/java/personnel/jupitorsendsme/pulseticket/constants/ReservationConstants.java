package personnel.jupitorsendsme.pulseticket.constants;

/**
 * 예약 관련 상수
 */
public class ReservationConstants {

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
	 * Pending :
	 * Reserved : 예약된 상태 <br>
	 *
	 */
	public enum ReservationStatus {
		PENDING,
		CONFIRMED,
		CANCELLED,
		EXPIRED
	}
}
