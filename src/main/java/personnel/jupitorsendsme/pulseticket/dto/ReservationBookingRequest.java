package personnel.jupitorsendsme.pulseticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 예약/예약 취소시 Controller 의 request 객체
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservationBookingRequest {
	/**
	 * 예약신청자 로그인 ID
	 */
	private String loginId;

	/**
	 * 예약신청자 password
	 */
	private String password;

	/**
	 * 예약하고자 하는 event id
	 */
	private Long eventId;

	/**
	 * 예약하고자 하는 시트 번호
	 */
	private Integer seatNumber;
}
