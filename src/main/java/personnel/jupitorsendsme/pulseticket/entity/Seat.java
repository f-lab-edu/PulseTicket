package personnel.jupitorsendsme.pulseticket.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Converter;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
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
import personnel.jupitorsendsme.pulseticket.exception.seat.IllegalSeatStatusDbValueException;

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
	@JoinColumn(name = "event_id", insertable = false, updatable = false)
	private Event event;
	/**
	 * 소속 이벤트의 id
	 */
	@Column(name = "event_id", nullable = false)
	private Long eventId;
	/**
	 * 좌석 번호
	 */
	@Column(name = "seat_number", nullable = false)
	private Integer seatNumber;
	/**
	 * 좌석 상태 (AVAILABLE, RESERVED, SOLD)
	 */
	@Convert(converter = SeatStatusConverter.class)
	@Column(columnDefinition = "smallint", nullable = false)
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

	/**
	 * Seats 테이블의 status 컬럼에 해당하는 상태 <br>
	 * Available : 예약 가능 <br>
	 * Reserved : 예약된 상태 <br>
	 * Sold : 예약이 구매된 상태 (paid)
	 */
	@Getter
	public enum SeatStatus {
		SOLD((short)3, null),
		RESERVED((short)2, SOLD),
		AVAILABLE((short)1, RESERVED);

		private final short dbValue;
		private final SeatStatus next;

		SeatStatus(short dbValue, SeatStatus next) {
			this.dbValue = dbValue;
			this.next = next;
		}

		public static SeatStatus toEnum(int dbValue) {
			return switch (dbValue) {
				case 1 -> SeatStatus.AVAILABLE;
				case 2 -> SeatStatus.RESERVED;
				case 3 -> SeatStatus.SOLD;
				default -> throw new IllegalSeatStatusDbValueException(dbValue);
			};
		}

		public SeatStatus nextPhase() {
			return this.next;
		}
	}

	@Converter
	private static class SeatStatusConverter implements AttributeConverter<SeatStatus, Short> {
		@Override
		public Short convertToDatabaseColumn(SeatStatus attribute) {
			if (attribute == null) {
				return null;
			}
			return attribute.getDbValue();
		}

		@Override
		public SeatStatus convertToEntityAttribute(Short dbData) {
			if (dbData == null) {
				return null;
			}
			return SeatStatus.toEnum(dbData);
		}
	}
}
