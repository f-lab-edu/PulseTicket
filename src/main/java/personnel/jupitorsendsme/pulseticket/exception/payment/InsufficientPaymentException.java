package personnel.jupitorsendsme.pulseticket.exception.payment;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;
import personnel.jupitorsendsme.pulseticket.entity.Event;

/**
 * 불충분한 금액
 */
@Getter
public class InsufficientPaymentException extends RuntimeException {
	private final String loginId;
	private final Long eventId;
	private final BigDecimal ticketPrice;
	private final BigDecimal paymentAmount;
	private final HttpStatus httpStatus;

	public InsufficientPaymentException(Event event, ReservationRequest request) {
		super(String.format("결재 금액 부족 - userLoginId: %s, eventId : %d, 필요 금액: %s, 전송 금액: %s",
			request.getLoginId(), event.getId(), event.getTicketPrice(), request.getPaymentAmount()));
		this.loginId = request.getLoginId();
		this.eventId = event.getId();
		this.ticketPrice = event.getTicketPrice();
		this.paymentAmount = request.getPaymentAmount();
		this.httpStatus = HttpStatus.PAYMENT_REQUIRED;
	}
}
