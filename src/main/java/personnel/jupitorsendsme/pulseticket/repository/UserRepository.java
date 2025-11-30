package personnel.jupitorsendsme.pulseticket.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import personnel.jupitorsendsme.pulseticket.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findUserByLoginId(String loginId);

	boolean existsByLoginId(String loginId);
}
