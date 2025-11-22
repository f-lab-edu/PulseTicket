package personnel.jupitorsendsme.pulseticket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import personnel.jupitorsendsme.pulseticket.entity.Reservation;

/**
 * Reservation 엔티티에 대한 데이터 접근 계층
 * 예약 데이터 저장, 조회, 삭제 등의 기본 CRUD 작업을 제공
 */
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
	
	List<Reservation> findByUser_LoginId(String loginId);
}
