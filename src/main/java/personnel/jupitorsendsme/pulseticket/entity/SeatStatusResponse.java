package personnel.jupitorsendsme.pulseticket.entity;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Builder;

/**
 * @param seatNumber
좌석 번호
 * @param seatStatus
좌석 상태 (AVAILABLE, RESERVED, CONFIRMED) */
@Builder
public record SeatStatusResponse(Integer seatNumber, Seat.SeatStatus seatStatus) {

	public static SeatStatusResponse from(Seat seat) {
		return SeatStatusResponse
			.builder()
			.seatNumber(seat.getSeatNumber())
			.seatStatus(seat.getStatus())
			.build();
	}

	public static List<SeatStatusResponse> from(List<Seat> seatList) {
		return seatList.stream().map(SeatStatusResponse::from).collect(Collectors.toList());
	}
}
