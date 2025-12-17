package personnel.jupitorsendsme.pulseticket.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import lombok.NonNull;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;

public class UserNotFoundException extends RuntimeException implements ErrorResponse {
	private final String loginId;
	private final HttpStatus httpStatus;

	public UserNotFoundException(ReservationRequest request) {
		super("사용자 조회 안됨 - login Id : " + request.getLoginId());
		this.loginId = request.getLoginId();
		this.httpStatus = HttpStatus.NOT_FOUND;
	}

	@Override
	@NonNull
	public HttpStatusCode getStatusCode() {
		return this.httpStatus;
	}

	@Override
	@NonNull
	public ProblemDetail getBody() {
		ProblemDetail detail = ProblemDetail.forStatusAndDetail(this.httpStatus,
			String.format("%s id 를 가진 사용자가 없음", this.loginId));
		detail.setTitle("존재하지 않는 사용자");

		return detail;
	}
}
