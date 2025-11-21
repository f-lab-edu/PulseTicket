package personnel.jupitorsendsme.pulseticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;

/**
 * 에약/예약 취소 후 응답 객체
 */
@Getter
@Builder
@AllArgsConstructor
public class ReservationBookingResponse {

	/**
	 * 예약 또는 예약 취소를 성공 했는지에 대한 flag
	 */
	private boolean isSuccess;

	/**
	 * 예약 성공한 경우 예약 번호
	 */
	private Long reservationId;

	/**
	 * 성공시 Reservation 객체로부터 ReservationBookingResponse 를 Builder 로 리턴
	 * @param created 생성된 Reservation 객체
	 * @return 성공시 반환하는 ReservationBookingResponse
	 */
	public static ReservationBookingResponse success(Reservation created) {
		return ReservationBookingResponse
			.builder()
			.isSuccess(true)
			.reservationId(created.getId())
			.build();
	}
}
