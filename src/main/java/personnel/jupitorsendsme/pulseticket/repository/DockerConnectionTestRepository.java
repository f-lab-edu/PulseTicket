package personnel.jupitorsendsme.pulseticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import personnel.jupitorsendsme.pulseticket.entity.DockerConnectionTestEntity;

/**
 * DockerConnectionTestEntity에 대한 데이터 접근 계층
 * JPA를 통한 PostgreSQL 데이터베이스와의 상호작용만 담당
 * 데이터 저장, 조회, 삭제 등의 기본 CRUD 작업을 제공
 */
public interface DockerConnectionTestRepository extends JpaRepository<DockerConnectionTestEntity, Long> {
}

