package personnel.jupitorsendsme.pulseticket;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;
import personnel.jupitorsendsme.pulseticket.service.UserManagementService;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional
public class UserManagementServiceTest {
	private final UserManagementService userManagementService;

	/**
	 * 유저 생성 기본
	 */
	@Test
	public void createUser_success() {
		String testUserLoginId = "testUser1";
		String testUserRawPassword = "testUserPassword1";
		ReservationRequest request =
			ReservationRequest
				.builder()
				.loginId(testUserLoginId)
				.password(testUserRawPassword)
				.build();

		assertThatNoException().isThrownBy(() ->
			userManagementService.createUser(request)
		);

		assertThatNoException().isThrownBy(() ->
			userManagementService.getValidUser(request)
		);
	}
}
