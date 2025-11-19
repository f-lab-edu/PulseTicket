package personnel.jupitorsendsme.pulseticket.entity;

import java.util.ArrayList;
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
 * 사용자 정보를 관리하는 엔티티
 */
@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class User extends BaseEntity {

	/**
	 * 사용자 고유 식별자
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * 로그인 ID (중복 불가)
	 */
	@Column(name = "login_id", nullable = false, unique = true, length = 50)
	private String loginId;

	/**
	 * 해시된 비밀번호
	 */
	@SuppressWarnings("DefaultAnnotationParam")
	@Column(name = "password_hash", nullable = false, length = 255)
	private String passwordHash;

	/**
	 * 사용자의 예약 목록
	 */
	@Builder.Default
	@OneToMany(mappedBy = "user")
	private List<Reservation> reservations = new ArrayList<>();
}
