package personnel.jupitorsendsme.pulseticket.interfaces;

import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingResponse;

public interface ReservationBookingService {

    /**
     * 좌석 예약
     * @param username 예약 신청자의 id
     * @param password 예약 신청자의 password
     * @param eventId 예약하고자 하는 이벤트의 id
     * @param seatNumber 예약하고자 하는 이벤트의 좌석 번호
     * @return 예약 성공여부 (isSuccess), 예약 번호 (reservationId)
     */
    ReservationBookingResponse book (String username, String password, Long eventId, Integer seatNumber);
}
