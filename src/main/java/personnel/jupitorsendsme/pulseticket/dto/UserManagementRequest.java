package personnel.jupitorsendsme.pulseticket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserManagementRequest {

	/**
	 * 사용자 id
	 */
	private String userId;

	/**
	 * 사용자 password
	 */
	private String password;
}
