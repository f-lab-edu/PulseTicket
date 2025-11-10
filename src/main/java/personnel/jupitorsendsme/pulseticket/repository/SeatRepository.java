package personnel.jupitorsendsme.pulseticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import personnel.jupitorsendsme.pulseticket.entity.Seat;


/**
 * Seat 엔티티에 대한 데이터 접근 계층
 * 좌석 데이터 저장, 조회, 삭제 등의 기본 CRUD 작업을 제공
 */
public interface SeatRepository extends JpaRepository<Seat, Long> {
}
