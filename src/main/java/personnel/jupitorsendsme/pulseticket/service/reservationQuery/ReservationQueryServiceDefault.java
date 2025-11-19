package personnel.jupitorsendsme.pulseticket.service.reservationQuery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.constants.ReservationConstants;
import personnel.jupitorsendsme.pulseticket.dto.ReservationQueryResponse;
import personnel.jupitorsendsme.pulseticket.interfaces.ReservationQueryService;
import personnel.jupitorsendsme.pulseticket.repository.SeatRepository;

@Service
@Qualifier("default")
@RequiredArgsConstructor
public class ReservationQueryServiceDefault implements ReservationQueryService {

	private final SeatRepository seatRepo;

	@Override
	public boolean isBookingEventAvailable(Long eventId) {
		return seatRepo.existsSeatByEventIdAndStatus(eventId, ReservationConstants.SeatStatus.AVAILABLE);
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
	public ReservationQueryResponse inquiryUserReservations(String userId, String password) {
		return null;
	}
}
