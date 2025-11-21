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
		SOLD;

		public SeatStatus reserve() {
			switch (this) {
				case RESERVED -> throw new IllegalStateException("이미 예약된 좌석");
				case SOLD -> throw new IllegalStateException("이미 결제완료된 좌석");
			}

			return RESERVED;
		}

		public SeatStatus sold() {
			switch (this) {
				case AVAILABLE -> throw new IllegalStateException("예약 가능한 좌석 상태. 예약이 되고 나서 결제해야 함");
				case SOLD -> throw new IllegalStateException("이미 판매됨");
			}
			return SOLD;
		}
	}

	/**
	 * Reservation 테이블의 status 컬럼에 해당하는 상태 <br>
	 * Pending : 예약 직후 상태. 아직 결제는 안한 상태 <br>
	 * CONFIRMED : 결제해서 예약된 상태 <br>
	 * CANCELLED : 예약이 취소된 상태 <br>
	 * Expired : 예약은 했으나 일정시간 결제를 안해서 만료된 상태.
	 */
	public enum ReservationStatus {

		PENDING,
		CONFIRMED,
		CANCELLED,
		EXPIRED;

		public ReservationStatus confirm() {
			if (this != PENDING) {
				throw new IllegalStateException("예약 대기가 아닌 상태에서 예약 확정 불가, 현재 상태 : " + this);
			}
			return CONFIRMED;
		}

		public ReservationStatus cancel() {
			switch (this) {
				case CANCELLED -> throw new IllegalStateException("취소된 예약");
				case EXPIRED -> throw new IllegalStateException("만료된 예약");
			}
			return CANCELLED;
		}

		public ReservationStatus expire() {
			switch (this) {
				case CONFIRMED -> throw new IllegalStateException("이미 결제된 예약");
				case EXPIRED -> throw new IllegalStateException("만료된 예약");
			}
			return EXPIRED;
		}
	}
}
