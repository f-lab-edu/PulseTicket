package personnel.jupitorsendsme.pulseticket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingRequest;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingResponse;
import personnel.jupitorsendsme.pulseticket.factory.ReservationBookingServiceFactory;

/**
 * 좌석 예약 컨트롤러</br>
 * HTTP 요청/응답 처리</br>
 * 요청에 따른 예약방식을 설정하는 역할까지 포함함.</br>
 */
@RestController
@RequestMapping("api/reservation/booking")
public class ReservationBookingController {

    private final ReservationBookingServiceFactory reservationBookingServiceFactory;

    @Autowired
    public ReservationBookingController (
            ReservationBookingServiceFactory reservationBookingServiceFactory) {
        this.reservationBookingServiceFactory = reservationBookingServiceFactory;
    }

    /**
     * 예약 청을 하는 엔드 포인트
     * 예약에 성공하면 Seats, Reservation 테이블에 데이터 저장
     *
     * @param request 예약 데이터 요청
     * @return 예약 성공 여부를 포함한 예약 관련 정보
     */
    @PostMapping
    ReservationBookingResponse booking (@RequestBody ReservationBookingRequest request) {

        return reservationBookingServiceFactory
                .getReservationBookingService()
                .book(request);
    }
}
