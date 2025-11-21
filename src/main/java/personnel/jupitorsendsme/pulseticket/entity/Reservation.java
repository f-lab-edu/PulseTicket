package personnel.jupitorsendsme.pulseticket.entity;

import java.time.Duration;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 예약 정보를 관리하는 엔티티
 */
@Entity
@Table(name = "reservations")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation extends BaseEntity {

	/**
	 * 예약 만료 시간
	 * 임시로 24시간으로 설정
	 */
	public static final Duration RESERVATION_EXPIRATION = Duration.ofHours(24);

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

	/**
	 * 예약 고유 식별자
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 예약한 사용자
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	/**
	 * 예약된 좌석
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seat_id", nullable = false)
	private Seat seat;

	/**
	 * 예약 대상 이벤트
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id", nullable = false)
	private Event event;

	/**
	 * 예약 상태 (PENDING, CONFIRMED, CANCELLED)
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
	private ReservationStatus status;

	/**
	 * 예약 만료 일시
	 */
	@Column(name = "expires_at", nullable = false)
	private LocalDateTime expiresAt;

	/**
	 * 예약 확정 일시
	 */
	@Column(name = "confirmed_at")
	private LocalDateTime confirmedAt;

	/**
	 * 예약 취소 일시
	 */
	@Column(name = "cancelled_at")
	private LocalDateTime cancelledAt;
}
