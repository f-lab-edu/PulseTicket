package personnel.jupitorsendsme.pulseticket.exception.user;

import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;

@Getter
public class UserNotFoundException extends RuntimeException {
	private final String loginId;

	public UserNotFoundException(ReservationRequest request) {
		super("사용자 조회 안됨 - login Id : " + request.getLoginId());
		this.loginId = request.getLoginId();
	}
}
