package personnel.jupitorsendsme.pulseticket.dto;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;
import personnel.jupitorsendsme.pulseticket.entity.Seat;

/**
 * @param reservationId
예약 고유 번호
 * @param eventId
이벤트 고유 번호
 * @param seatNumber
좌석 번호
 * @param seatStatus
좌석의 상태
{@link Seat.SeatStatus} */
@Builder
public record ReservationQueryResponse(Long reservationId, Long eventId, Integer seatNumber,
									   Seat.SeatStatus seatStatus) {

	/**
	 * Reservation 으로부터 ReservationQueryResponse 를 변환
	 * @param reservation 변환하고자 하는 Reservation 객체
	 * @return 변환된 ReservationQueryResponse 객체
	 */
	public static ReservationQueryResponse from(Reservation reservation) {
		return ReservationQueryResponse.builder()
			.reservationId(reservation.getId())
			.eventId(reservation.getEvent().getId())
			.seatNumber(reservation.getSeat().getSeatNumber())
			.seatStatus(reservation.getSeat().getStatus())
			.build();
	}

	public static List<ReservationQueryResponse> from(List<Reservation> reservations) {
		return reservations.stream().map(ReservationQueryResponse::from).collect(Collectors.toList());
	}
}
