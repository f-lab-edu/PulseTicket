package personnel.jupitorsendsme.pulseticket.exception.payment;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import lombok.NonNull;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;
import personnel.jupitorsendsme.pulseticket.entity.Event;

/**
 * 불충분한 금액
 */
public class InsufficientPaymentException extends RuntimeException implements ErrorResponse {
	private final BigDecimal ticketPrice;
	private final BigDecimal paymentAmount;
	private final HttpStatus httpStatus;

	public InsufficientPaymentException(Event event, ReservationRequest request) {
		super(String.format("결재 금액 부족 - userLoginId: %s, eventId : %d, 필요 금액: %s, 전송 금액: %s",
			request.getLoginId(), event.getId(), event.getTicketPrice(), request.getPaymentAmount()));
		this.ticketPrice = event.getTicketPrice();
		this.paymentAmount = request.getPaymentAmount();
		this.httpStatus = HttpStatus.PAYMENT_REQUIRED;
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
			String.format("필요 금액: %s, 전송 금액: %s", this.ticketPrice, this.paymentAmount));
		detail.setTitle("결제 금액 부족");

		return detail;
	}
}
