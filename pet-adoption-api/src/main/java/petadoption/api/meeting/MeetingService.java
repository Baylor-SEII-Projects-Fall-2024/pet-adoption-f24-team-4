package petadoption.api.meeting;

import java.util.List;
import java.util.Optional;

public class MeetingService {

    MeetingRepository meetingRepository;

    public Meeting saveMeeting(Meeting meeting){
        return meetingRepository.save(meeting);
    }
    public Optional<Meeting> findMeetingByID(Long meetingId){
        return meetingRepository.findById(meetingId);
    }

    public List<Meeting> findMeetingsByUserID(Long userId){
        return meetingRepository.findByUserId(userId);
    }
    public List<Meeting> findMeetingsByCenterID(Long adoptionID){
        return meetingRepository.findByAdoptionID(adoptionID);
    }
}
