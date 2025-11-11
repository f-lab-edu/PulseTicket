package personnel.jupitorsendsme.pulseticket.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationRequest {

    /**
     * 예약신청자 id
     */
    private String username;

    /**
     * 예약신청자 password
     */
    private String password;

    /**
     * 예약하고자 하는 event id
     */
    private String eventId;
}
