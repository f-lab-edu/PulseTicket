package personnel.jupitorsendsme.pulseticket.dto;

/**
 * 에약/예약 취소 후 응답 객체
 */
public class ReservationBookingResponse extends ReservationResponse {

    /**
     * 예약 또는 예약 취소를 성공 했는지에 대한 flag
     */
    boolean isSuccess;
}
