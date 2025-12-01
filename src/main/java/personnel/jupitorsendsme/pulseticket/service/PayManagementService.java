package personnel.jupitorsendsme.pulseticket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;
import personnel.jupitorsendsme.pulseticket.entity.Event;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;
import personnel.jupitorsendsme.pulseticket.entity.Seat;
import personnel.jupitorsendsme.pulseticket.exception.payment.InsufficientPaymentException;

@Service
@RequiredArgsConstructor
@Transactional
public class PayManagementService {
	private final ReservationQueryService reservationQueryService;
	private final UserManagementService userManagementService;
	private final EventManagementService eventManagementService;

	public void payReservation(ReservationRequest request) {

		// 사용자 검증
		userManagementService.isUserValid(request);

		// 결재 가능한 예약
		Reservation reservation = reservationQueryService.getPayableReservation(request);
		Seat seat = reservation.getSeat();
		Event event = reservation.getEvent();

		// 가격 검증
		if (!eventManagementService.isPaymentAmountEnough(event, request))
			throw new InsufficientPaymentException(event, request);

		// Seat update
		seat.proceed();

		// Reservation Update
		reservation.confirm();
	}
}
