package personnel.jupitorsendsme.pulseticket.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 이벤트 정보를 관리하는 엔티티
 */
@Entity
@Table(name = "events")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event extends BaseEntity {
	/**
	 * 이벤트 고유 식별자
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 이벤트명
	 */
	@Column(nullable = false)
	private String name;

	/**
	 * 총 좌석 수
	 */
	@Column(name = "total_seats", nullable = false)
	private Integer totalSeats;

	/**
	 * 이벤트의 좌석 목록
	 */
	@OneToMany(mappedBy = "event")
	private List<Seat> seats;
}
