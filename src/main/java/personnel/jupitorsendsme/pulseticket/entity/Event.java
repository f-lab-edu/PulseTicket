package personnel.jupitorsendsme.pulseticket.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 이벤트 정보를 관리하는 엔티티
 */
@Entity
@Table(name = "events")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Event {

    /**
     * 이벤트 고유 식별자
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 이벤트명
     */
    @Column(nullable = false, length = 255)
    private String name;

    /**
     * 총 좌석 수
     */
    @Column(name = "total_seats", nullable = false)
    private Integer totalSeats;

    /**
     * 생성 일시
     */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    /**
     * 이벤트의 좌석 목록
     */
    @OneToMany(mappedBy = "event")
    private List<Seat> seats = new ArrayList<>();

    /**
     * Event 생성자
     *
     * @param name 이벤트명
     * @param totalSeats 총 좌석 수
     */
    public Event(String name, Integer totalSeats) {
        this.name = name;
        this.totalSeats = totalSeats;
        this.createdAt = LocalDateTime.now();
    }
}
