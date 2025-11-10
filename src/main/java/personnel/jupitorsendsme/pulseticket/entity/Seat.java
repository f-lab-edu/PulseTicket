package personnel.jupitorsendsme.pulseticket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Seat {

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
    @Column(nullable = false, length = 20)
    private String status;

    /**
     * 예약 만료 일시
     */
    @Column(name = "reserved_until")
    private LocalDateTime reservedUntil;

    /**
     * 생성 일시
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 수정 일시
     */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 해당 좌석의 예약
     */
    @OneToOne(mappedBy = "seat")
    private Reservation reservation;

    /**
     * Seat 생성자
     *
     * @param event 소속 이벤트
     * @param seatNumber 좌석 번호
     */
    public Seat(Event event, Integer seatNumber) {
        this.event = event;
        this.seatNumber = seatNumber;
        this.status = "AVAILABLE";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
