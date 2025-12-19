package personnel.jupitorsendsme.pulseticket.exception;

import java.net.URI;

import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> customExceptionHandler(Exception ex, WebRequest request) throws Exception {

		if (ex instanceof ErrorResponse errorResponse) {
			ProblemDetail detail = errorResponse.getBody();

			if (request instanceof ServletWebRequest servletWebRequest) {
				detail.setInstance(URI.create(servletWebRequest.getRequest().getRequestURI()));
			}

			return handleExceptionInternal(
				ex,
				detail,
				errorResponse.getHeaders(),
				errorResponse.getStatusCode(),
				request
			);
		}

		return super.handleException(ex, request);
	}
}
