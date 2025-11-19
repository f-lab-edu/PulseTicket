package personnel.jupitorsendsme.pulseticket.entity;

import java.time.LocalDateTime;
import java.util.List;

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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import personnel.jupitorsendsme.pulseticket.constants.ReservationConstants;

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
	@JoinColumn(name = "event_id", nullable = false)
	private Event event;

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
	private ReservationConstants.SeatStatus status;

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
}
