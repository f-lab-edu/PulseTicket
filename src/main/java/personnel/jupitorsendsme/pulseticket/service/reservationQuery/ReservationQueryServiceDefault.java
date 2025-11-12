package personnel.jupitorsendsme.pulseticket.service.reservationQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import personnel.jupitorsendsme.pulseticket.dto.ReservationQueryResponse;
import personnel.jupitorsendsme.pulseticket.interfaces.ReservationQueryService;

@Service
@Qualifier("default")
public class ReservationQueryServiceDefault implements ReservationQueryService {

    @Override
    public boolean isBookingEventAvailable(Long eventId) {
        return false;
    }

    @Override
    public String availableSeatsOfTheEvent(Long eventId) {
        return "";
    }

    @Override
    public boolean isSpecificSeatAvailable(Long eventId, Integer seatNumber) {
        return false;
    }

    @Override
    public ReservationQueryResponse inquiryUserReservations(String username, String password) {
        return null;
    }
}
