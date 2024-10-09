package petadoption.api.meeting;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting,Long> {

    List<Meeting> findByUserId(Long userId);
    List<Meeting> findByAdoptionID(Long adoptionID);
}
