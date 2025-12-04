package personnel.jupitorsendsme.pulseticket.unit;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;
import personnel.jupitorsendsme.pulseticket.entity.User;
import personnel.jupitorsendsme.pulseticket.repository.UserRepository;
import personnel.jupitorsendsme.pulseticket.service.HashingServiceArgon2id;
import personnel.jupitorsendsme.pulseticket.service.UserManagementService;

@DataJpaTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({UserManagementService.class, HashingServiceArgon2id.class})
@Transactional
public class UserManagementServiceTest {
	private final UserManagementService userManagementService;
	private final UserRepository userRepository;

	/**
	 * 유저 생성 기본
	 */
	@Test
	@DisplayName("유저 생성시 생성된 데이터가 1개")
	public void createUser_success() {
		String testUserLoginId = "testUser1";
		String testUserRawPassword = "testUserPassword1";
		ReservationRequest request =
			ReservationRequest
				.builder()
				.loginId(testUserLoginId)
				.password(testUserRawPassword)
				.build();

		userManagementService.createUser(request);

		Iterable<User> allUsers = userRepository.findAll();
		assertThat(allUsers).hasSize(1);
	}
}
