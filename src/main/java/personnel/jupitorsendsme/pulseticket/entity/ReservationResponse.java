package personnel.jupitorsendsme.pulseticket.entity;

import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.constants.ReservationConstants;

@Getter
public class ReservationResponse {
	private final Long reservationId;
	private final Long eventId;
	private final Integer seatNumber;
	private final ReservationConstants.SeatStatus seatStatus;

	public static ReservationResponse from(Reservation reservation) {
		return new ReservationResponse(reservation);
	}

	ReservationResponse(Reservation reservation) {
		this.reservationId = reservation.getId();
		this.eventId = reservation.getEvent().getId();
		this.seatNumber = reservation.getSeat().getSeatNumber();
		this.seatStatus = reservation.getSeat().getStatus();
	}
}
