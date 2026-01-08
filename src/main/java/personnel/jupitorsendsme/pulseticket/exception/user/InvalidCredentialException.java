package personnel.jupitorsendsme.pulseticket.exception.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import lombok.NonNull;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;

public class InvalidCredentialException extends RuntimeException implements ErrorResponse {
	private final String loginId;
	private final HttpStatus httpStatus;

	public InvalidCredentialException(ReservationRequest request) {
		super(String.format("유효하지 않은 인증 정보 - loginId: %s, password: %s",
			request.getLoginId(), request.getPassword()));
		this.loginId = request.getLoginId();
		this.httpStatus = HttpStatus.UNAUTHORIZED;
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
			String.format("%s 사용자의 인증 정보가 유효하지 않음", this.loginId));
		detail.setTitle("유효하지 않은 인증정보");

		return detail;
	}
}
