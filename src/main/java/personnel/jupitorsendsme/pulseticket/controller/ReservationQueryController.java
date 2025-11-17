package personnel.jupitorsendsme.pulseticket.controller;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import personnel.jupitorsendsme.pulseticket.dto.ReservationQueryResponse;
import personnel.jupitorsendsme.pulseticket.interfaces.ReservationQueryService;

/**
 * 좌석 조회 컨트롤러
 * HTTP 요청/응답 처리
 * 여러 조회 방식을 포함함
 */
@RestController
@RequestMapping("api/reservation/query")
public class ReservationQueryController {

    private final ReservationQueryService reservationQueryService;

    public ReservationQueryController(
            @Qualifier("default")
            ReservationQueryService reservationQueryService) {

        this.reservationQueryService = reservationQueryService;
    }

    /**
     * 특정 이벤트에 대한 예약 가능 여부. <br>
     * 좌석이 일단 비어있기만 하면 (하나라도 예약 가능한 상태라면) true
     * @param eventId 예약 가능한지 확인하려는 이벤트 id
     * @return 예약 가능 여부
     */
    @GetMapping("isBookingEventAvailable")
    public Boolean isBookingEventAvailable (@RequestParam Long eventId) {

        return reservationQueryService.isBookingEventAvailable(eventId);
    }

    /**
     * 특정 이벤트의 예약 가능한 좌석 조회 - 시각적 표현 <br>
     * 일단 행바꿈을 '\n' 캐릭터를 써서 전체 좌석을 String 으로 나타내기
     * @param eventId 확인하고자 하는 이벤트 id
     * @return Textual Diagram
     */
    @GetMapping("availableSeatsOfTheEvent")
    public String availableSeatsOfTheEvent (@RequestParam Long eventId) {
        return reservationQueryService.availableSeatsOfTheEvent(eventId);
    }

    /**
     * 특정 이벤트의 특정 좌석이 예약 가능한지 판단
     * @param eventId 알아보고자 하는 이벤트의 id
     * @param seatNumber 알아보고자 하는 이벤트의 좌석 번호
     * @return 에약 가능 여부. (나중에는 유효하지 않은 예약 좌석일 경우 특정 메시지를 반환하도록 수정하는게 좋겠다)
     */

    @GetMapping("isSpecificSeatAvailable")
    public Boolean isSpecificSeatAvailable (@RequestParam Long eventId, @RequestParam Integer seatNumber) {
        return reservationQueryService.isSpecificSeatAvailable(eventId, seatNumber);
    }


    /**
     * 특정 사용자에 대한 예약 목록 조회
     * @param userId 확인하고자 하는 사용자의 id
     * @param password 확인하고자 하는 사용자의 password
     * @return 예약 목록이 담긴 DTO
     */
    @GetMapping("inquiryUserReservations")
    public ReservationQueryResponse inquiryUserReservations (@RequestParam String userId, @RequestParam String password) {
        return reservationQueryService.inquiryUserReservations(userId, password);
    }
}
