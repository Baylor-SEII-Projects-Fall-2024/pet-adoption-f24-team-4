package petadoption.api.endpoint;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import petadoption.api.meeting.Meeting;
import petadoption.api.meeting.MeetingService;
import petadoption.api.pet.Pet;

import java.util.List;

public class MeetingEndpoint {

    MeetingService meetingService;
    @GetMapping("/meeting/{userID}")
    public ResponseEntity<?> getUserMeetings(@PathVariable Long userID) {
        try {
            List<Meeting> meetings = meetingService.findMeetingsByUserID(userID);
            return ResponseEntity.ok(meetings);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching pets: " + e.getMessage());
        }
    }
    @GetMapping("/meeting/{adoptionID}")
    public ResponseEntity<?> getCenterMeetings(@PathVariable Long centerID) {
        try {
            List<Meeting> meetings = meetingService.findMeetingsByCenterID(centerID);
            return ResponseEntity.ok(meetings);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching pets: " + e.getMessage());
        }
    }
}
