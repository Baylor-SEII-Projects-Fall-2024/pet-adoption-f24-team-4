package petadoption.api.meeting;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import petadoption.api.pet.Pet;

import java.util.List;

@Repository
public interface MeetingRepository extends JpaRepository<Meeting, Long> {
    Meeting findByPet_id(long petID);
    List<Meeting> findByUser_id(long userID);
}
