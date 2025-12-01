package personnel.jupitorsendsme.pulseticket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import personnel.jupitorsendsme.pulseticket.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

	@Query("SELECT r FROM Reservation r "
		+ "LEFT JOIN FETCH r.event "
		+ "LEFT JOIN FETCH r.seat "
		+ "WHERE r.id = :id")
	Optional<Reservation> findByIdWithRelations(@Param("id") Long reservationId);

	List<Reservation> findByUser_LoginId(String loginId);
}
