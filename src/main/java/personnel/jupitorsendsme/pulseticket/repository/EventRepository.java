package personnel.jupitorsendsme.pulseticket.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import personnel.jupitorsendsme.pulseticket.entity.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
}
