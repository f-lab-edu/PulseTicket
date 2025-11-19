package personnel.jupitorsendsme.pulseticket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import personnel.jupitorsendsme.pulseticket.constants.ReservationConstants;
import personnel.jupitorsendsme.pulseticket.entity.Seat;

/**
 * Seat 엔티티에 대한 데이터 접근 계층
 * 좌석 데이터 저장, 조회, 삭제 등의 기본 CRUD 작업을 제공
 */
public interface SeatRepository extends JpaRepository<Seat, Long> {

	boolean existsByEvent_IdAndStatus(Long event_id, ReservationConstants.SeatStatus status);

	boolean existsSeatByEvent_IdAndSeatNumberAndStatus(Long event_id, Integer seatNumber,
		ReservationConstants.SeatStatus status);

	Optional<Seat> findByEvent_IdAndSeatNumberAndStatus(Long event_id, Integer seatNumber,
		ReservationConstants.SeatStatus status);

	List<Seat> findByEvent_Id(Long event_id);
}
