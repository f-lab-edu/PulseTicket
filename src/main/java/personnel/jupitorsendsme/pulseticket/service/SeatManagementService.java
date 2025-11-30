package personnel.jupitorsendsme.pulseticket.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;
import personnel.jupitorsendsme.pulseticket.entity.Event;
import personnel.jupitorsendsme.pulseticket.entity.Seat;
import personnel.jupitorsendsme.pulseticket.entity.SeatStatusResponse;
import personnel.jupitorsendsme.pulseticket.exception.InvalidForeignKeyException;
import personnel.jupitorsendsme.pulseticket.exception.seat.SeatNumberDuplicateException;
import personnel.jupitorsendsme.pulseticket.exception.seat.SeatNumberOutOfRangeException;
import personnel.jupitorsendsme.pulseticket.exception.seat.SeatNotAvailableException;
import personnel.jupitorsendsme.pulseticket.exception.seat.SeatNotFoundException;
import personnel.jupitorsendsme.pulseticket.repository.EventRepository;
import personnel.jupitorsendsme.pulseticket.repository.SeatRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SeatManagementService {
	private final SeatRepository seatRepository;
	private final EventRepository eventRepository;

	/**
	 * 특정 이벤트의 예약 가능한 좌석 조회 - 시각적 표현 <br>
	 * 전체 좌석을 String 으로 나타내기 <br>
	 * @param request 확인하고자 하는 이벤트 id 가 담긴 객체 <br>
	 * @return Textual Diagram <br>
	 */
	public List<SeatStatusResponse> statusOfSeatsOfTheEvent(ReservationRequest request) {
		List<Seat> seats = seatRepository.findByEvent_Id(request.getEventId());

		return SeatStatusResponse.from(seats);
	}

	/**
	 * 특정 이벤트의 특정 좌석이 예약 가능한지 판단 <br>
	 * @param request 알아보고자 하는 이벤트의 id 와 좌석번호가 담긴 request 객체 <br>
	 * @return 에약 가능 여부. (나중에는 유효하지 않은 예약 좌석일 경우 특정 메시지를 반환하도록 수정하는게 좋겠다) <br>
	 */
	public boolean isSpecificSeatAvailable(ReservationRequest request) {
		return seatRepository.existsSeatByEvent_IdAndSeatNumberAndStatus(
			request.getEventId(),
			request.getSeatNumber(),
			Seat.SeatStatus.AVAILABLE
		);
	}

	/**
	 * 좌석 엔티티를 찾는 메서드 <br>
	 * @param request eventId, seatNumber <br>
	 * @return 찾은 Seat Entity <br>
	 */
	public Seat getSeat(ReservationRequest request) {
		Long eventId = request.getEventId();
		Integer seatNumber = request.getSeatNumber();

		return seatRepository.findByEvent_IdAndSeatNumber(eventId, seatNumber)
			.orElseThrow(() -> new SeatNotFoundException(eventId, seatNumber));
	}

	/**
	 * 특정 이벤트의 예약 가능한 좌석 리턴 <br>
	 * @param request 알아보고자 하는 이벤트의 id 와 좌석번호가 담긴 request 객체 <br>
	 * @return 예약이 가능하면 Seat , 불가능하면 null <br>
	 */
	public Seat getAvailableSeat(ReservationRequest request) {
		Seat seat = getSeat(request);

		if (seat.getStatus() != Seat.SeatStatus.AVAILABLE)
			throw new SeatNotAvailableException(seat);

		return seat;
	}

	/**
	 * 좌석 생성 시 FK와 좌석번호 검증 후 저장
	 */
	@Transactional
	public Seat createValidSeat(Seat seat) {
		Event event = eventRepository.findById(seat.getEventId())
			.orElseThrow(
				() -> new InvalidForeignKeyException(seat, Event.class, String.valueOf(seat.getEventId()), "event_id"));

		Integer seatNumber = seat.getSeatNumber();
		if (seatNumber == null || seatNumber < 1 || seatNumber > event.getTotalSeats()) {
			throw new SeatNumberOutOfRangeException(event.getId(), seatNumber, event.getTotalSeats());
		}

		if (seatRepository.existsByEvent_IdAndSeatNumber(event.getId(), seatNumber)) {
			throw new SeatNumberDuplicateException(event.getId(), seatNumber);
		}

		return seatRepository.save(seat);
	}
}
