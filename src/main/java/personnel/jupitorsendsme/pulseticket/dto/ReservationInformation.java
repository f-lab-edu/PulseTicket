package personnel.jupitorsendsme.pulseticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import personnel.jupitorsendsme.pulseticket.constants.ReservationConstants;

@Getter
@Builder
@AllArgsConstructor
public class ReservationInformation {

	/**
	 * 예약 고유 번호
	 */
	private Long reservationId;

	/**
	 * 예약 상태
	 */
	private ReservationConstants.ReservationStatus status;
}
