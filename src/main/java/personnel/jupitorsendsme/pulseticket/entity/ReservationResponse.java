package personnel.jupitorsendsme.pulseticket.entity;

import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.constants.ReservationConstants;

@Getter
public class ReservationResponse {
	private Long reservationId;
	private Long eventId;
	private Integer seatNumber;
	private ReservationConstants.SeatStatus seatStatus;

	public static ReservationResponse from(Reservation reservation) {
		ReservationResponse response = new ReservationResponse();
		response.reservationId = reservation.getId();
		response.eventId = reservation.getEvent().getId();
		response.seatNumber = reservation.getSeat().getSeatNumber();
		response.seatStatus = reservation.getSeat().getStatus();

		return response;
	}
}
