package personnel.jupitorsendsme.pulseticket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import personnel.jupitorsendsme.pulseticket.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	List<Reservation> findByUser_LoginId(String loginId);
}
