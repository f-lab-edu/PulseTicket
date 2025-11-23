package personnel.jupitorsendsme.pulseticket.exception.user;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {
	private final String loginId;

	public UserNotFoundException(String loginId) {
		super("사용자 조회 안됨 : " + loginId);
		this.loginId = loginId;
	}
}
