package personnel.jupitorsendsme.pulseticket.entity;

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
import personnel.jupitorsendsme.pulseticket.constants.ReservationConstants;

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
	private ReservationConstants.ReservationStatus status;
	
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
