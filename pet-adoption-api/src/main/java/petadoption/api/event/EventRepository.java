package petadoption.api.event;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    public List<Event> findAllByCenter_AdoptionID(Long adoptionID);
}