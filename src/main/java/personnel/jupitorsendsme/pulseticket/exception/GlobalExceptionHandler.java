package personnel.jupitorsendsme.pulseticket.exception;

import java.net.URI;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Object> customExceptionHandler(Exception ex, @Nullable Object body, HttpHeaders headers,
		HttpStatusCode statusCode, WebRequest request) {

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

		return super.handleExceptionInternal(ex, body, headers, statusCode, request);
	}
}
