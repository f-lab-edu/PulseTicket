package personnel.jupitorsendsme.pulseticket.service.reservationBooking;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import personnel.jupitorsendsme.pulseticket.constants.ReservationConstants;
import personnel.jupitorsendsme.pulseticket.dto.ReservationBookingResponse;
import personnel.jupitorsendsme.pulseticket.entity.Reservation;
import personnel.jupitorsendsme.pulseticket.entity.Seat;
import personnel.jupitorsendsme.pulseticket.entity.User;
import personnel.jupitorsendsme.pulseticket.interfaces.ReservationBookingService;
import personnel.jupitorsendsme.pulseticket.interfaces.ReservationQueryService;
import personnel.jupitorsendsme.pulseticket.interfaces.UserManagementService;
import personnel.jupitorsendsme.pulseticket.repository.ReservationRepository;
import personnel.jupitorsendsme.pulseticket.repository.SeatRepository;
import personnel.jupitorsendsme.pulseticket.repository.UserRepository;

import java.time.LocalDateTime;

/**
 * 얘의 역할은 뭐지? </br>
 * booking, 즉 예약 담당이다. </br>
 * 예약에는 여러가지 단계가 필요하다. </br>
 * </br>
 * 그걸 총괄하는 역할? </br>
 */
@Service
public class ReservationBookingServiceDefault implements ReservationBookingService {

    @Autowired
    @Qualifier("default")
    UserManagementService userManagementService;

    @Autowired
    @Qualifier("default")
    ReservationQueryService reservationQueryService;

    @Autowired
    ReservationRepository reservationRepo;

    @Autowired
    UserRepository userRepo;

    @Autowired
    SeatRepository seatRepo;

    @Override
    @Transactional
    public ReservationBookingResponse book(String username, String password, Long eventId, Integer seatNumber) throws RuntimeException {

        // 사용자 유효성 여부 (User Entity 조회할때와 합칠 순 없나 ?)
        if (!userManagementService.isUserValid(username, password)) throw new RuntimeException();

        // 시트 좌석 예약 가능 여부 (이것도 Seat Entity 조회시와 합칠 순 없나?)
        if (!reservationQueryService.isSpecificSeatAvailable(eventId, seatNumber)) throw new RuntimeException();

        // User Entity 조회하여 키 습득
        User user = userRepo.findUserByUsername(username).orElseThrow(RuntimeException::new);

        // 마찬가지 이유로 Seat Entity 조회
        Seat seat = seatRepo.findSeatByEventIdAndSeatNumber(eventId, seatNumber).orElseThrow(RuntimeException::new);

        Reservation reservation = Reservation.builder()
                .user(user)
                .seat(seat)
                .status(ReservationConstants.ReservationStatus.PENDING)
                .expiresAt(LocalDateTime.now().plusHours(24))
                .build();

        reservationRepo.save(reservation);

        return null;
    }
}
