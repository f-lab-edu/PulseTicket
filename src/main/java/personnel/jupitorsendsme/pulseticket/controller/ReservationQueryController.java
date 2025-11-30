package personnel.jupitorsendsme.pulseticket.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.dto.ReservationQueryResponse;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;
import personnel.jupitorsendsme.pulseticket.entity.SeatStatusResponse;
import personnel.jupitorsendsme.pulseticket.service.ReservationQueryService;
import personnel.jupitorsendsme.pulseticket.service.SeatManagementService;

/**
 * 좌석 조회 컨트롤러
 * HTTP 요청/응답 처리
 * 여러 조회 방식을 포함함
 */
@RestController
@RequestMapping("api/reservation/query")
@RequiredArgsConstructor
public class ReservationQueryController {

	private final ReservationQueryService reservationQueryService;
	private final SeatManagementService seatManagementService;

	/**
	 * 특정 이벤트에 대한 예약 가능 여부. <br>
	 * 좌석이 일단 비어있기만 하면 (하나라도 예약 가능한 상태라면) true
	 * @param request 예약 가능한지 확인하려는 이벤트 id 가 담긴 객체
	 * @return 예약 가능 여부
	 */
	@GetMapping("isBookingEventAvailable")
	public Boolean isBookingEventAvailable(@ModelAttribute ReservationRequest request) {
		return reservationQueryService.isBookingEventAvailable(request);
	}

	/**
	 * 특정 이벤트의 좌석 현황 리스트
	 * @param request 확인하고자 하는 이벤트 id 가 담긴 객체 <br>
	 * @return 특정 이벤트의 좌석 정보 리스트
	 */
	@GetMapping("statusOfSeatsOfTheEvent")
	public List<SeatStatusResponse> statusOfSeatsOfTheEvent(@ModelAttribute ReservationRequest request) {
		return seatManagementService.statusOfSeatsOfTheEvent(request);
	}

	/**
	 * 특정 이벤트의 특정 좌석이 예약 가능한지 판단
	 * @param request 알아보고자 하는 이벤트의 id, 좌석번호가 담긴 객체
	 * @return 에약 가능 여부. (나중에는 유효하지 않은 예약 좌석일 경우 특정 메시지를 반환하도록 수정하는게 좋겠다)
	 */

	@GetMapping("isSpecificSeatAvailable")
	public Boolean isSpecificSeatAvailable(@ModelAttribute ReservationRequest request) {
		return seatManagementService.isSpecificSeatAvailable(request);
	}

	/**
	 * 특정 사용자에 대한 예약 목록 조회
	 * @param request 확인하고자 하는 사용자의 id, password 가담긴 객체
	 * @return 예약 목록이 담긴 DTO
	 */
	@GetMapping("inquiryUserReservations")
	public List<ReservationQueryResponse> inquiryUserReservations(@ModelAttribute ReservationRequest request) {
		return reservationQueryService.inquiryUserReservations(request);
	}
}
