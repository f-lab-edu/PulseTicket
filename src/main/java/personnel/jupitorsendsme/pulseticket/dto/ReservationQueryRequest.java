package personnel.jupitorsendsme.pulseticket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 예약 확인 request 객체
 */
@Getter
@NoArgsConstructor
public class ReservationQueryRequest {
    /**
     * 예약 관련 확인을 하고자 하는 사용자 id
     */
    private String userId;

    /**
     * 예약 관련 확인을 하고자 하는 사용자 password
     */
    private String password;

    /**
     * 예약 관련 조회를 하고자 하는 event id
     */
    private Long eventId;

    /**
     * 예약 관련 조회를 하고자 하는 이벤트의 시트 번호
     */
    private Integer seatNumber;
}
