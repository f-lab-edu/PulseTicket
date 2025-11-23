package personnel.jupitorsendsme.pulseticket.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import personnel.jupitorsendsme.pulseticket.entity.User;

/**
 * User 엔티티에 대한 데이터 접근 계층
 * 사용자 데이터 저장, 조회, 삭제 등의 기본 CRUD 작업을 제공
 */
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findUserByLoginId(String loginId);
	
	boolean existsByLoginId(String loginId);
}
