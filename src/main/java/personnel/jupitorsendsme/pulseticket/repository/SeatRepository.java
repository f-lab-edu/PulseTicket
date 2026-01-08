package personnel.jupitorsendsme.pulseticket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import personnel.jupitorsendsme.pulseticket.entity.Seat;

public interface SeatRepository extends JpaRepository<Seat, Long> {
	boolean existsByEvent_IdAndStatus(Long event_id, Seat.SeatStatus status);

	boolean existsSeatByEvent_IdAndSeatNumberAndStatus(Long event_id, Integer seatNumber,
		Seat.SeatStatus status);

	boolean existsByEvent_IdAndSeatNumber(Long event_id, Integer seatNumber);

	Optional<Seat> findByEvent_IdAndSeatNumber(Long event_id, Integer seatNumber);

	List<Seat> findByEvent_Id(Long event_id);
}
