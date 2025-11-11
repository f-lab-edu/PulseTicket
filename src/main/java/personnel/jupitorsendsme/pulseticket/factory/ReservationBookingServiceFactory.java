package personnel.jupitorsendsme.pulseticket.factory;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingRequest;
import personnel.jupitorsendsme.pulseticket.interfaces.ReservationBookingService;
import personnel.jupitorsendsme.pulseticket.service.reservationBooking.ReservationBookingServiceDefault;

/**
 * ReservationBooking 구현체 factory method 컴포넌트
 * Request 에 따라 어떤 서비스를 쓸건지를 정해서 Controller 에게 넘겨주는 책임.
 */
@Component
public class ReservationBookingServiceFactory {
    private final ObjectProvider<ReservationBookingServiceDefault> bookingServiceDefaultProvider;

    public ReservationBookingServiceFactory (
            ObjectProvider<ReservationBookingServiceDefault> bookingServiceDefaultProvider
    ) {
        this.bookingServiceDefaultProvider = bookingServiceDefaultProvider;
    }

    /**
     * @param request Controller 로 부터 넘겨받은 ReservationBookingRequest
     * @return request 에 해당하는 BookingService 선택해서 반환
     */
    public ReservationBookingService getReservationBookingService (ReservationBookingRequest request) {
        if (request.getUsername() != null) {
            return bookingServiceDefaultProvider.getObject();
        }
        // 찾을 수 없다면 Default
        return bookingServiceDefaultProvider.getObject();
    }
}
