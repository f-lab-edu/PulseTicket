package personnel.jupitorsendsme.pulseticket.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingRequest;
import personnel.jupitorsendsme.pulseticket.dto.ReservationQueryResponse;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;
import personnel.jupitorsendsme.pulseticket.entity.Seat;
import personnel.jupitorsendsme.pulseticket.entity.SeatStatusResponse;
import personnel.jupitorsendsme.pulseticket.repository.ReservationRepository;
import personnel.jupitorsendsme.pulseticket.repository.SeatRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationQueryService {

	private final SeatRepository seatRepository;
	private final ReservationRepository reservationRepository;

	/**
	 * 특정 이벤트에 대한 예약 가능 여부. <br>
	 * 좌석이 일단 비어있기만 하면 (하나라도 예약 가능한 상태라면) true <br>
	 * @param request 확인하려는 이벤트 id 가 담긴 request <br>
	 * @return 예약 가능 여부 <br>
	 */
	public boolean isBookingEventAvailable(ReservationBookingRequest request) {
		return seatRepository.existsByEvent_IdAndStatus(request.getEventId(), Seat.SeatStatus.AVAILABLE);
	}

	/**
	 * 특정 이벤트의 예약 가능한 좌석 조회 - 시각적 표현 <br>
	 * 전체 좌석을 String 으로 나타내기 <br>
	 * @param request 확인하고자 하는 이벤트 id 가 담긴 객체 <br>
	 * @return Textual Diagram <br>
	 */
	public List<SeatStatusResponse> statusOfSeatsOfTheEvent(ReservationBookingRequest request) {

		List<Seat> seats = seatRepository.findByEvent_Id(request.getEventId());

		return SeatStatusResponse.from(seats);
	}

	/**
	 * 특정 이벤트의 특정 좌석이 예약 가능한지 판단 <br>
	 * @param request 알아보고자 하는 이벤트의 id 와 좌석번호가 담긴 request 객체 <br>
	 * @return 에약 가능 여부. (나중에는 유효하지 않은 예약 좌석일 경우 특정 메시지를 반환하도록 수정하는게 좋겠다) <br>
	 */
	public boolean isSpecificSeatAvailable(ReservationBookingRequest request) {
		return seatRepository.existsSeatByEvent_IdAndSeatNumberAndStatus(
			request.getEventId(),
			request.getSeatNumber(),
			Seat.SeatStatus.AVAILABLE
		);
	}

	/**
	 * 특정 이벤트의 예약 가능한 좌석 리턴 <br>
	 * @param request 알아보고자 하는 이벤트의 id 와 좌석번호가 담긴 request 객체 <br>
	 * @return 예약이 가능하면 Seat , 불가능하면 null <br>
	 */
	public Optional<Seat> findAvailableSeat(ReservationBookingRequest request) {
		return seatRepository.findByEvent_IdAndSeatNumberAndStatus(
			request.getEventId(),
			request.getSeatNumber(),
			Seat.SeatStatus.AVAILABLE
		);
	}

	/**
	 * 특정 사용자에 대한 예약 목록 조회 <br>
	 * @param request 확인하고자 하는 사용자의 id 와 password 가 담긴 객체 <br>
	 * @return 예약 목록이 담긴 DTO <br>
	 */
	public List<ReservationQueryResponse> inquiryUserReservations(ReservationBookingRequest request) {
		List<Reservation> reservations = reservationRepository.findByUser_LoginId(request.getLoginId());

		return ReservationQueryResponse.from(reservations);
	}
}
