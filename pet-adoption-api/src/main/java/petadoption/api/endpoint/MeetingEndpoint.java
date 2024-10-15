package petadoption.api.endpoint;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import petadoption.api.meeting.Meeting;
import petadoption.api.meeting.MeetingService;

@RestController
@RequestMapping("/MeetingHome")
public class MeetingEndpoint {

    @Autowired
    private MeetingService meetingService;

    // Get all meetings for the user
    @GetMapping
    public List<Meeting> getAllMeetings() {
        return meetingService.getAllMeetings();  // Fetch meetings from the service layer
    }

    // Delete a meeting by its ID
    @DeleteMapping("/{id}")
    public void deleteMeeting(@PathVariable Long id) {
        meetingService.deleteMeeting(id);  // Delete the meeting with the given ID
    }
}