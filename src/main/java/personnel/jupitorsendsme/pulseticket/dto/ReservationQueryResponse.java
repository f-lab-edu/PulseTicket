package personnel.jupitorsendsme.pulseticket.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * 예약 조회 서비스에 대한 응답 객체
 */
@Getter
@Builder
@AllArgsConstructor
public class ReservationQueryResponse {

    /**
     * 예약이 가능한 상태인지 표시
     * true 면 예약 가능, false 면 예약 불가능
     */
    boolean isBookingAvailable;

    /**
     * 예약이 완료됬는지를 알려줌
     * true 면 예약 완료, false 면 예약 되지 않음
     */
    boolean hasBookingDone;

    /**
     * 특정 이벤트에 대한 예약가능한 자리를 matrix 형태로 보여주는 문자열
     * 행구분은 '\n' 으로 한다.
     */
    String seatsMap;
}
