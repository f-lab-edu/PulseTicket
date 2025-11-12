package personnel.jupitorsendsme.pulseticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

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
    boolean isSuccess;
}
