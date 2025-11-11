package personnel.jupitorsendsme.pulseticket.dto;

/**
 * Reservation 관련 응답 객체
 */
public class ReservationResponse {

    /**
     * 예약을 한 사용자의 id
     */
    private String username;

    /**
     * 예약관련 동작을 한 이벤트 대상
     */
    private String eventId;

}
