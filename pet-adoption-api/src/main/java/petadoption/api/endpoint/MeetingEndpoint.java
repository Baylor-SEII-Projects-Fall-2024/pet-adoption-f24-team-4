package petadoption.api.endpoint;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import petadoption.api.adoptionCenter.AdoptionCenter;
import petadoption.api.meeting.Meeting;
import petadoption.api.meeting.MeetingRequest;
import petadoption.api.meeting.MeetingService;
import petadoption.api.pet.Pet;
import petadoption.api.pet.PetRequest;
import petadoption.api.user.User;

@Log4j2
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


    @PostMapping("/add")
    public ResponseEntity<Meeting> addMeeting(@RequestBody MeetingRequest request) {
        try {

            Meeting m = new Meeting();
            Meeting result = meetingService.createAndSaveMeeting(request.getPetID(), request.getUserID(), request.getDate());
            log.info("Successfully saved meeting");
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            log.info("Failed to save Meeting", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // get a meeting by its ID
    @GetMapping("/{id}")
    public void getMeeting(@PathVariable Long id) {
        meetingService.getMeetingById(id);
    }

    @DeleteMapping("/deleteMeeting")
    public ResponseEntity<String> deleteMeeting(@RequestBody Long meetingId) {
        try {
            meetingService.deleteMeeting(meetingId);
            return ResponseEntity.ok("Meeting deleted successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Meeting not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the Meeting.");
        }
    }

    // Get meetings of adoption center
    @GetMapping("/adoption-center/{adoptionID}")
    public List<Meeting> getMeetingsFromCenter(@PathVariable long adoptionID) {
        return meetingService.getAdoptionCenterMeetings(adoptionID);
    }

    // Get meetings of user
    @GetMapping("/user/{userID}")
    public List<Meeting> getMeetingsFromUser(@PathVariable long userID) {
        return meetingService.getUserMeetings(userID);
    }

    @PutMapping("/updateMeeting")
    public ResponseEntity<Meeting> updateMeeting(@RequestBody Meeting request) {
        try {
            Optional<Meeting> result = meetingService.getMeetingById(request.getId());
            if (result.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Meeting m = result.get();

            copyRequestToMeeting(request, m);
            Meeting saveResult = meetingService.saveMeeting(m);
            log.info("Meeting successfully updated: " + saveResult.getId());
            return ResponseEntity.ok(saveResult);
        } catch (Exception e) {
            log.error("Failed to update Meeting", e);
            return ResponseEntity.badRequest().build();
        }
    }
    private void copyRequestToMeeting(@RequestBody Meeting request, Meeting e) {
        e.setDate(request.getDate());
        e.setPet(request.getPet());
        e.setId(request.getId());
        e.setUser(request.getUser());
    }


}