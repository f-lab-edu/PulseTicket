package personnel.jupitorsendsme.pulseticket.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import personnel.jupitorsendsme.pulseticket.exception.seat.IllegalSeatPhaseException;

/**
 * 좌석 정보를 관리하는 엔티티
 */
@Entity
@Table(
	name = "seats",
	uniqueConstraints = @UniqueConstraint(
		name = "uk_seats_event_seat_number",
		columnNames = {"event_id", "seat_number"}
	)
)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Seat extends BaseEntity {

	/**
	 * 좌석 고유 식별자
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	/**
	 * 소속 이벤트
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(
		name = "event_id",
		nullable = false,
		foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)
	)
	private Event event;
	/**
	 * 좌석 번호
	 */
	@Column(name = "seat_number", nullable = false)
	private Integer seatNumber;
	/**
	 * 좌석 상태 (AVAILABLE, RESERVED, SOLD)
	 */
	@Enumerated(EnumType.STRING)
	@Column(length = 20, nullable = false)
	private SeatStatus status;
	/**
	 * 예약 만료 일시
	 */
	@Column(name = "reserved_until")
	private LocalDateTime reservedUntil;
	/**
	 * 해당 좌석의 예약 이력
	 */
	@OneToMany(mappedBy = "seat")
	private List<Reservation> reservations;

	/**
	 * Seats Entity 영속화시 상태 초기화
	 * status가 없으면 AVAILABLE 로 설정
	 */
	@Override
	protected void prePersistHook() {
		if (this.status == null) {
			this.status = SeatStatus.AVAILABLE;
		}
	}

	public void proceed() {
		SeatStatus next = this.getStatus().nextPhase();

		if (next == null) {
			throw new IllegalSeatPhaseException(this);
		}

		this.status = next;
	}

	public boolean isReservationAvailable() {
		return this.status == SeatStatus.AVAILABLE;
	}

	public boolean isOutOfRange() {
		return this.seatNumber == null || seatNumber < 1 || seatNumber > event.getTotalSeats();
	}

	/**
	 * Seats 테이블의 status 컬럼에 해당하는 상태 <br>
	 * Available : 예약 가능 <br>
	 * Reserved : 예약된 상태 <br>
	 * Sold : 예약이 구매된 상태 (paid)
	 */
	@Getter
	public enum SeatStatus {
		SOLD(null),
		RESERVED(SOLD),
		AVAILABLE(RESERVED);

		private final SeatStatus next;

		SeatStatus(SeatStatus next) {
			this.next = next;
		}

		public SeatStatus nextPhase() {
			return this.next;
		}
	}
}
