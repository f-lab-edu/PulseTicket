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
 * 기본 좌석 예약 서비스
 */
@Service
public class ReservationBookingServiceDefault implements ReservationBookingService {

    private final UserManagementService userManagementService;
    private final ReservationQueryService reservationQueryService;
    private final ReservationRepository reservationRepo;
    private final UserRepository userRepo;
    private final SeatRepository seatRepo;

    @Autowired
    public ReservationBookingServiceDefault(
            @Qualifier("default") UserManagementService userManagementService,
            @Qualifier("default") ReservationQueryService reservationQueryService,
            ReservationRepository reservationRepo,
            UserRepository userRepo,
            SeatRepository seatRepo) {
        this.userManagementService = userManagementService;
        this.reservationQueryService = reservationQueryService;
        this.reservationRepo = reservationRepo;
        this.userRepo = userRepo;
        this.seatRepo = seatRepo;
    }

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
