package personnel.jupitorsendsme.pulseticket.interfaces;

import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingRequest;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingResponse;

public interface ReservationBookingService {

    /**
     * 예약 처리 메서드
     * @param request 예약 관련 정보
     * @return 예약이 잘 됬는지 안됬는지에 대한 응답
     */
    ReservationBookingResponse book (ReservationBookingRequest request);
}
