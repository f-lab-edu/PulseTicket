package personnel.jupitorsendsme.pulseticket.userManagement;

import static org.assertj.core.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingRequest;
import personnel.jupitorsendsme.pulseticket.service.UserManagementService;

@SpringBootTest
@ActiveProfiles("test")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class UserManagementServicePersistenceTest {
	private final UserManagementService userManagementService;

	@Test
	public void createUserTest() {
		String testUserLoginId = "testUser8";
		String testUserRawPassword = "testUserPassword8";
		ReservationBookingRequest request = new ReservationBookingRequest(testUserLoginId, testUserRawPassword);

		assertThatNoException().isThrownBy(() ->
			userManagementService.createUser(request)
		);

		assertThatNoException().isThrownBy(() ->
			userManagementService.getValidUser(request)
		);
	}
}
