package personnel.jupitorsendsme.pulseticket.service.reservationQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import personnel.jupitorsendsme.pulseticket.constants.ReservationConstants;
import personnel.jupitorsendsme.pulseticket.dto.ReservationQueryResponse;
import personnel.jupitorsendsme.pulseticket.interfaces.ReservationQueryService;
import personnel.jupitorsendsme.pulseticket.repository.SeatRepository;

@Service
@Qualifier("default")
public class ReservationQueryServiceDefault implements ReservationQueryService {

    private final SeatRepository seatRepo;

    public ReservationQueryServiceDefault(SeatRepository seatRepo) {
        this.seatRepo = seatRepo;
    }

    @Override
    public boolean isBookingEventAvailable(Long eventId) {
        return seatRepo.existsSeatByEventIdAndStatus (eventId, ReservationConstants.SeatStatus.AVAILABLE);
    }

    @Override
    public String availableSeatsOfTheEvent(Long eventId) {
        return "";
    }

    @Override
    public boolean isSpecificSeatAvailable(Long eventId, Integer seatNumber) {
        return seatRepo.existsSeatByEventIdAndSeatNumberAndStatus(
                eventId,
                seatNumber,
                ReservationConstants.SeatStatus.AVAILABLE
        );
    }

    @Override
    public ReservationQueryResponse inquiryUserReservations(String username, String password) {
        return null;
    }
}
