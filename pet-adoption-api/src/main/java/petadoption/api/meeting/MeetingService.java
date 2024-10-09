package petadoption.api.meeting;

import java.util.Optional;

public class MeetingService {

    MeetingRepository meetingRepository;

    public Meeting saveMeeting(Meeting meeting){
        return meetingRepository.save(meeting);
    }
    public Optional<Meeting> findMeetingByID(Long meetingId){
        return meetingRepository.findById(meetingId);
    }
}
