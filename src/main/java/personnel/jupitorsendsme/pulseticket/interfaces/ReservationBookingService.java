package personnel.jupitorsendsme.pulseticket.interfaces;

import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingRequest;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingResponse;

public interface ReservationBookingService {

	/**
	 * 좌석 예약
	 * @param request 예약에 필요한 정보
	 * @return 예약 성공여부 (isSuccess), 예약 번호 (reservationId)
	 */
	ReservationBookingResponse book(ReservationBookingRequest request);
}
