package personnel.jupitorsendsme.pulseticket.service.reservationBooking;

import org.springframework.stereotype.Service;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingRequest;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingResponse;
import personnel.jupitorsendsme.pulseticket.interfaces.ReservationBookingService;

@Service
public class ReservationBookingServiceDefault implements ReservationBookingService {

    @Override
    public ReservationBookingResponse book(ReservationBookingRequest request) {
        return null;
    }
}
