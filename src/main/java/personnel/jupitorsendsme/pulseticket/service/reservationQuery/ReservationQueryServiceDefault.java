package personnel.jupitorsendsme.pulseticket.service.reservationQuery;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional(readOnly = true)
public class ReservationQueryServiceDefault implements ReservationQueryService {

	private final SeatRepository seatRepository;

	@Override
	public boolean isBookingEventAvailable(ReservationBookingRequest request) {
		return seatRepository.existsByEvent_IdAndStatus(request.getEventId(),
			ReservationConstants.SeatStatus.AVAILABLE);
	}

	@Override
	public String textualDiagramOfSeatsOfTheEvent(ReservationBookingRequest request) {
		List<Seat> seats = seatRepository.findByEvent_Id(request.getEventId());
		StringBuilder diagram = new StringBuilder();

		for (Seat seat : seats) {
			diagram.append("[");

			switch (seat.getStatus()) {
				case ReservationConstants.SeatStatus.RESERVED -> diagram.append("X");
				case ReservationConstants.SeatStatus.SOLD -> diagram.append("~");
				default -> diagram.append("O");
			}

			diagram.append("]");
		}

		return diagram.toString();
	}

	@Override
	public boolean isSpecificSeatAvailable(ReservationBookingRequest request) {
		return seatRepository.existsSeatByEvent_IdAndSeatNumberAndStatus(
			request.getEventId(),
			request.getSeatNumber(),
			ReservationConstants.SeatStatus.AVAILABLE
		);
	}

	@Override
	public Optional<Seat> findAvailableSeat(ReservationBookingRequest request) {
		return seatRepository.findByEvent_IdAndSeatNumberAndStatus(
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
