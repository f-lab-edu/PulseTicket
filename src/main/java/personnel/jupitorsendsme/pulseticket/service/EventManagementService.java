package personnel.jupitorsendsme.pulseticket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;
import personnel.jupitorsendsme.pulseticket.entity.Event;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventManagementService {

	// TODO : 테스트, 예외 처리 필요 -> event, request 유효한지
	boolean isPaymentAmountEnough(Event event, ReservationRequest request) {
		return request.getPaymentAmount().compareTo(event.getTicketPrice()) >= 0;
	}
}
