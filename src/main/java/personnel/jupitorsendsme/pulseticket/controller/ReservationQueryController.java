package personnel.jupitorsendsme.pulseticket.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personnel.jupitorsendsme.pulseticket.service.reservationQuery.ReservationQueryA;

/**
 * 좌석 조회 컨트롤러
 * HTTP 요청/응답 처리
 * 여러 조회 방식을 포함함
 */
@RestController
@RequestMapping("api/reservation/query")
public class ReservationQueryController {

    private ReservationQueryA reservationQueryA;

    public ReservationQueryController(ReservationQueryA reservationQueryA) {
        this.reservationQueryA = reservationQueryA;
    }


}
