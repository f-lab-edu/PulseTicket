package personnel.jupitorsendsme.pulseticket.service.reservationQuery;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.constants.ReservationConstants;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingRequest;
import personnel.jupitorsendsme.pulseticket.dto.ReservationQueryResponse;
import personnel.jupitorsendsme.pulseticket.entity.Seat;
import personnel.jupitorsendsme.pulseticket.interfaces.ReservationQueryService;
import personnel.jupitorsendsme.pulseticket.repository.SeatRepository;

@Service
@Qualifier("default")
@RequiredArgsConstructor
public class ReservationQueryServiceDefault implements ReservationQueryService {

	private final SeatRepository seatRepo;

	@Override
	public boolean isBookingEventAvailable(ReservationBookingRequest request) {
		return seatRepo.existsSeatByEventIdAndStatus(request.getEventId(), ReservationConstants.SeatStatus.AVAILABLE);
	}

	@Override
	public String availableSeatsOfTheEvent(ReservationBookingRequest request) {
		return "";
	}

	@Override
	public boolean isSpecificSeatAvailable(ReservationBookingRequest request) {
		return seatRepo.existsSeatByEventIdAndSeatNumberAndStatus(
			request.getEventId(),
			request.getSeatNumber(),
			ReservationConstants.SeatStatus.AVAILABLE
		);
	}

	@Override
	public Optional<Seat> findAvailableSeat(ReservationBookingRequest request) {
		return seatRepo.findSeatByEventIdAndSeatNumberAndStatus(
			request.getEventId(),
			request.getSeatNumber(),
			ReservationConstants.SeatStatus.AVAILABLE
		);
	}

	@Override
	public ReservationQueryResponse inquiryUserReservations(ReservationBookingRequest request) {

		return null;
	}
}
