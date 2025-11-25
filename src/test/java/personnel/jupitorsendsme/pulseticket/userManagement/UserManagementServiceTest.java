package personnel.jupitorsendsme.pulseticket.userManagement;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingRequest;
import personnel.jupitorsendsme.pulseticket.service.UserManagementService;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Transactional
public class UserManagementServiceTest {
	private final UserManagementService userManagementService;

	@Test
	public void createUserTest() {
		String testUserLoginId = "testUser1";
		String testUserRawPassword = "testUserPassword1";
		ReservationBookingRequest request =
			ReservationBookingRequest
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
