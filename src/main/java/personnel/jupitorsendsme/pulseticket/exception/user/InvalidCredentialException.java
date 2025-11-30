package personnel.jupitorsendsme.pulseticket.exception.user;

import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;

@Getter
public class InvalidCredentialException extends RuntimeException {
	private final String loginId;
	private final String rawPassword;

	public InvalidCredentialException(ReservationRequest request) {
		super("유효하지 않은 인증 정보 : " + request.getLoginId() + ", " + request.getPassword());
		this.loginId = request.getLoginId();
		this.rawPassword = request.getPassword();
	}
}
