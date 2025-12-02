package personnel.jupitorsendsme.pulseticket.exception.user;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;

@Getter
public class UserNotFoundException extends RuntimeException {
	private final String loginId;
	private final HttpStatus httpStatus;

	public UserNotFoundException(ReservationRequest request) {
		super("사용자 조회 안됨 - login Id : " + request.getLoginId());
		this.loginId = request.getLoginId();
		this.httpStatus = HttpStatus.NOT_FOUND;
	}
}
