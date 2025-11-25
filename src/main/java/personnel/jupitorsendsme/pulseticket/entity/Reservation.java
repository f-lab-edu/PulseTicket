package personnel.jupitorsendsme.pulseticket.entity;

import java.time.Duration;
import java.time.LocalDateTime;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
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
	 * 예약 고유 식별자
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 예약한 사용자
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private User user;
	/**
	 * 예약된 좌석
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seat_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Seat seat;
	/**
	 * 예약 대상 이벤트
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Event event;
	/**
	 * 예약 상태 (PENDING, CONFIRMED, CANCELLED)
	 */
	@Convert(converter = ReservationStatusConverter.class)
	@Column(columnDefinition = "smallint", nullable = false)
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

	/**
	 * JPA 영속화를 위해 Reservation 객체를 리턴하는 메서드
	 * @param user 저장할 User 정보
	 * @param seat 저장할 Seat 정보
	 * @return 저장할 Reservation 객체
	 */
	public static Reservation reserve(User user, Seat seat, Event event) {
		return Reservation.builder()
			.user(user)
			.seat(seat)
			.event(event)
			.status(Reservation.ReservationStatus.PENDING)
			.expiresAt(LocalDateTime.now().plus(Reservation.RESERVATION_EXPIRATION))
			.build();
	}

	/**
	 * 현제 예약 상태를 예약 확인 상태로 변경
	 */
	public void confirm() {
		if (this.status != ReservationStatus.PENDING) {
			throw new IllegalStateException("예약 대기가 아닌 상태에서 예약 확정 불가, 현재 상태 : " + this);
		}
		this.status = ReservationStatus.CONFIRMED;
	}

	/**
	 * 예약 상태를 취소로 변경
	 */
	public void cancel() {
		switch (this.status) {
			case CANCELLED -> throw new IllegalStateException("취소된 예약");
			case EXPIRED -> throw new IllegalStateException("만료된 예약");
		}
		this.status = ReservationStatus.CANCELLED;
	}

	/**
	 * 예약 상태를 만료로 변경
	 */
	public void expire() {
		switch (this.status) {
			case CONFIRMED -> throw new IllegalStateException("이미 결제된 예약");
			case EXPIRED -> throw new IllegalStateException("만료된 예약");
		}
		this.status = ReservationStatus.EXPIRED;
	}

	/**
	 * Reservation 테이블의 status 컬럼에 해당하는 상태 <br>
	 * Pending : 예약 직후 상태. 아직 결제는 안한 상태 <br>
	 * CONFIRMED : 결제해서 예약된 상태 <br>
	 * CANCELLED : 예약이 취소된 상태 <br>
	 * Expired : 예약은 했으나 일정시간 결제를 안해서 만료된 상태.
	 */
	@Getter
	public enum ReservationStatus {
		PENDING((short)1),
		CONFIRMED((short)2),
		CANCELLED((short)3),
		EXPIRED((short)4);

		private final short dbValue;

		ReservationStatus(short dbValue) {
			this.dbValue = dbValue;
		}

		public static ReservationStatus fromDbValue(int dbValue) {
			return switch (dbValue) {
				case 1 -> ReservationStatus.PENDING;
				case 2 -> ReservationStatus.CONFIRMED;
				case 3 -> ReservationStatus.CANCELLED;
				case 4 -> ReservationStatus.EXPIRED;
				default -> throw new IllegalStateException("Unknown reservation status dbValue: " + dbValue);
			};
		}
	}

	@Converter
	public static class ReservationStatusConverter implements AttributeConverter<ReservationStatus, Short> {

		@Override
		public Short convertToDatabaseColumn(ReservationStatus attribute) {
			if (attribute == null) {
				return null;
			}
			return attribute.getDbValue();
		}

		@Override
		public ReservationStatus convertToEntityAttribute(Short dbData) {
			if (dbData == null) {
				return null;
			}
			return ReservationStatus.fromDbValue(dbData);
		}
	}

	/**
	 * Reservation Entity 영속화시 상태 초기화
	 * status가 없으면 PENDING으로 설정
	 */
	@Override
	protected void prePersistHook() {
		if (this.status == null) {
			this.status = ReservationStatus.PENDING;
		}
	}
}
