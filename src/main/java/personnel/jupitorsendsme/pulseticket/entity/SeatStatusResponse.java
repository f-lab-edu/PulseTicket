package personnel.jupitorsendsme.pulseticket.entity;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SeatStatusResponse {

	/**
	 * 좌석 번호
	 */
	private Integer seatNumber;
	/**
	 * 좌석 상태 (AVAILABLE, RESERVED, CONFIRMED)
	 */
	private Seat.SeatStatus seatStatus;

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
