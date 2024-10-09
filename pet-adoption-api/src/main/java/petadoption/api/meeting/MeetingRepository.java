package petadoption.api.meeting;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MeetingRepository extends JpaRepository<Meeting,Long> {


    List<Meeting> findByUser_Id(Long userId);


    List<Meeting> findByCenter_adoptionID(Long adoptionId);
}
