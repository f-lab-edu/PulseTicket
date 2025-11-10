package personnel.jupitorsendsme.pulseticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personnel.jupitorsendsme.pulseticket.service.reservationBooking.ReservationBookingA;
import personnel.jupitorsendsme.pulseticket.service.reservationQuery.ReservationQueryA;

/**
 * 좌석 예약 컨트롤러
 * HTTP 요청/응답 처리
 * 여러 예약 방식을 포함함
 * TODO 여러 결과방식에 대한 고민 필요 -> 어떻게 구현하는가 ? Return DTO 도 Interface ?
 */
@RestController
@RequestMapping("api/reservation/booking")
public class ReservationBookingController {

    private final ReservationBookingA reservationBookingA;

    @Autowired
    public ReservationBookingController (
            @Qualifier("defaultReservationBookingA") ReservationBookingA reservationBookingA ) {
        this.reservationBookingA = reservationBookingA;
    }


}
