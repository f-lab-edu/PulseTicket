package personnel.jupitorsendsme.pulseticket.dto;


/**
 * 예약/예약 취소시 Controller 의 request 객체
 */
public class ReservationBookingRequest extends ReservationRequest {
    /**
     * 예약하려고 하는건지에 대한 플래그
     * true 면 예약, false 면 예약 취소
     */
    private boolean isBooking;
}
