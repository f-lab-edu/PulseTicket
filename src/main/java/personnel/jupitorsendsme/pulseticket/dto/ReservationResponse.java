package personnel.jupitorsendsme.pulseticket.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;
import personnel.jupitorsendsme.pulseticket.entity.Seat;

@Getter
@AllArgsConstructor
public class ReservationResponse {

	/**
	 * 예약 고유 번호
	 */
	private final Long reservationId;

	/**
	 * 이벤트 고유 번호
	 */
	private final Long eventId;

	/**
	 * 좌석 번호
	 */
	private final Integer seatNumber;

	/**
	 * 좌석의 상태
	 * {@link Seat.SeatStatus}
	 */
	private final Seat.SeatStatus seatStatus;

	/**
	 * Reservation 으로부터 ReservationResponse 를 변환
	 * @param reservation 변환하고자 하는 Reservation 객체
	 * @return 변환된 ReservationResponse 객체
	 */
	public static ReservationResponse from(Reservation reservation) {

		return new ReservationResponse(
			reservation.getId(),
			reservation.getEvent().getId(),
			reservation.getSeat().getSeatNumber(),
			reservation.getSeat().getStatus());
	}
}
