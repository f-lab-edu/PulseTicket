package personnel.jupitorsendsme.pulseticket.entity;

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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import personnel.jupitorsendsme.pulseticket.constants.ReservationConstants;

import java.time.LocalDateTime;

/**
 * 예약 정보를 관리하는 엔티티
 */
@Entity
@Table(name = "reservations")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {

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
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id", nullable = false, unique = true)
    private Seat seat;

    /**
     * 예약 상태 (PENDING, CONFIRMED, CANCELLED)
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ReservationConstants.ReservationStatus status;

    /**
     * 예약 생성 일시
     */
    @Builder.Default
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

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
