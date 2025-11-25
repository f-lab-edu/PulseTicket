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
	@JoinColumn(name = "event_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Event event;
	/**
	 * 소속 이벤트의 id
	 */
	@Column(name = "event_id", insertable = false, updatable = false)
	private Long eventId;
	/**
	 * 좌석 번호
	 */
	@Column(name = "seat_number", nullable = false)
	private Integer seatNumber;
	/**
	 * 좌석 상태 (AVAILABLE, RESERVED, CONFIRMED)
	 */
	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 20)
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
	 * 해당 좌석 예약 처리
	 */
	public void reserve() {
		switch (this.status) {
			case RESERVED -> throw new IllegalStateException("이미 예약된 좌석");
			case SOLD -> throw new IllegalStateException("이미 결제완료된 좌석");
		}
		this.status = SeatStatus.RESERVED;
	}

	public void sold() {
		switch (this.status) {
			case AVAILABLE -> throw new IllegalStateException("예약 가능한 좌석 상태. 예약이 되고 나서 결제해야 함");
			case SOLD -> throw new IllegalStateException("이미 판매됨");
		}
		this.status = SeatStatus.SOLD;
	}

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
	 * Seats Entity 영속화시 상태 초기화
	 * status가 없으면 PENDING으로 설정
	 */
	@Override
	protected void prePersistHook() {
		if (this.status == null) {
			this.status = SeatStatus.AVAILABLE;
		}
	}
}
