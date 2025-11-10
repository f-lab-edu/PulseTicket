package personnel.jupitorsendsme.pulseticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import personnel.jupitorsendsme.pulseticket.entity.Event;


/**
 * Event 엔티티에 대한 데이터 접근 계층
 * 이벤트 데이터 저장, 조회, 삭제 등의 기본 CRUD 작업을 제공
 */
public interface EventRepository extends JpaRepository<Event, Long> {
}
