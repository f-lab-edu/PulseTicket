package personnel.jupitorsendsme.pulseticket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 예약/예약 취소시 Controller 의 request 객체
 */
@Getter
@Setter
@NoArgsConstructor
public class ReservationBookingRequest {
	/**
	 * 예약신청자 id
	 */
	private String userId;

	/**
	 * 예약신청자 password
	 */
	private String rawPassword;

	/**
	 * 예약하고자 하는 event id
	 */
	private Long eventId;

	/**
	 * 예약하고자 하는 시트 번호
	 */
	private Integer seatNumber;
}
