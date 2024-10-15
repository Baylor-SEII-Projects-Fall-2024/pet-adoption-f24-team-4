package petadoption.api.meeting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import petadoption.api.pet.Pet;

import java.util.List;
import java.util.Optional;

@Service
public class MeetingService {

    @Autowired
    MeetingRepository repository;

    public Optional<Meeting> getMeetingById(long meetingID){
        return repository.findById(meetingID);
    }

    public List<Meeting> getAllMeetings() { return repository.findAll(); }

    public void deleteMeeting(Long id) { repository.deleteById(id); }
}
