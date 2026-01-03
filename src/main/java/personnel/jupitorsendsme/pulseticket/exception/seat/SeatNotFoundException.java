package personnel.jupitorsendsme.pulseticket.exception.seat;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.web.ErrorResponse;

import lombok.NonNull;
import personnel.jupitorsendsme.pulseticket.dto.ReservationRequest;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;

public class SeatNotFoundException extends RuntimeException implements ErrorResponse {
	private final Long eventId;
	private final Integer seatNumber;
	private final HttpStatus httpStatus;

	public SeatNotFoundException(ReservationRequest request) {
		super(String.format("좌석 정보 검색 안됨 - eventId: %d, seatNumber: %d",
			request.getEventId(), request.getSeatNumber()));
		this.eventId = request.getEventId();
		this.seatNumber = request.getSeatNumber();
		this.httpStatus = HttpStatus.NOT_FOUND;
	}

	public SeatNotFoundException(Reservation reservation) {
		super(String.format("좌석 정보 검색 안됨 - eventId: %d, seatNumber: %d",
			reservation.getEvent().getId(), reservation.getSeat().getSeatNumber()));
		this.eventId = reservation.getEvent().getId();
		this.seatNumber = reservation.getSeat().getSeatNumber();
		this.httpStatus = HttpStatus.NOT_FOUND;
	}

	@Override
	@NonNull
	public HttpStatusCode getStatusCode() {
		return this.httpStatus;
	}

	@Override
	@NonNull
	public ProblemDetail getBody() {
		ProblemDetail detail = ProblemDetail.forStatusAndDetail(this.httpStatus,
			String.format("이벤트 ID %d에서 좌석 번호 %d를 찾을 수 없음", this.eventId, this.seatNumber));
		detail.setTitle("좌석을 찾을 수 없음");

		return detail;
	}
}
