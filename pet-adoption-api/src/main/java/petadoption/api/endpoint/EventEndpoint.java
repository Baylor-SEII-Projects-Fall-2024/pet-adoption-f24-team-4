package petadoption.api.endpoint;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import petadoption.api.adoptionCenter.AdoptionCenter;
import petadoption.api.adoptionCenter.AdoptionCenterService;
import petadoption.api.event.Event;
import petadoption.api.event.EventRequest;
import petadoption.api.event.EventService;

import java.util.List;
import java.util.Optional;

@Log4j2
@RestController
@RequestMapping("/events")
public class EventEndpoint {
    private final EventService eventService;

    private final AdoptionCenterService adoptionCenterService;

    public EventEndpoint(EventService eventService,
                         AdoptionCenterService adoptionCenterService) {
        this.eventService = eventService;
        this.adoptionCenterService = adoptionCenterService;
    }

    @GetMapping("/all")
    public List<Event> getEvents() { return eventService.getAllEvents(); }

    @PostMapping("/addEvent")
    public ResponseEntity<Event> addEvent(@RequestBody EventRequest eventRequest) {
        try {
            Event e = new Event();

            Optional<AdoptionCenter> ac =
                    adoptionCenterService.getCenter(eventRequest.getAdoptionID());
            if (ac.isPresent()) {
                e.setCenter(ac.get());
                // event picture logic
                e.setTitle(eventRequest.getTitle());
                e.setDescription(eventRequest.getDescription());
                e.setLocation(eventRequest.getLocation());
                e.setStartDateTime(eventRequest.getStartDateTime());
                e.setEndDateTime(eventRequest.getEndDateTime());

                Event result = eventService.saveEvent(e);
                log.info("Successfully saved event");
                return new ResponseEntity<>(result, HttpStatus.CREATED);
            } else {
                throw new EntityNotFoundException("AdoptionCenter not found");
            }
        } catch (Exception e) {
            log.info("Failed to save event", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    /*
    public ResponseEntity<Event> addEvent(@RequestBody Event request) {
        try {
            Event e = new Event();
            copyRequestToEvent(request, e);

            Event result = eventService.saveEvent(e);
            log.info("Successfully saved event");
            return new ResponseEntity<>(result, HttpStatus.CREATED);
        } catch (Exception e) {
            log.info("Failed to save event", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
     */

    @GetMapping("/{eventID}")
    public Optional<Event> getEvent(@PathVariable long eventID) {
        return eventService.getEventById(eventID);
    }

    @GetMapping("/adoption-center/{adoptionID}")
    public List<Event> getEventsFromCenter(@PathVariable long adoptionID) {
        return eventService.getEventsFromCenter(adoptionID);
    }

    @PutMapping("/updateEvent")
    public ResponseEntity<Event> updateEvent(@RequestBody Event request) {
        try {
            Optional<Event> result = eventService.getEventById(request.getEventID());
            if (result.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Event e = result.get();

            copyRequestToEvent(request, e);
            Event saveResult = eventService.saveEvent(e);
            log.info("Event successfully updated: " + saveResult.getEventID());
            return ResponseEntity.ok(saveResult);
        } catch (Exception e) {
            log.error("Failed to update Event", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/deleteEvent/{eventID}")
    public ResponseEntity<Event> deleteEvent(@PathVariable long eventID) {
        try {
            eventService.deleteEvent(eventID);
            log.info("Event successfully deleted: {}", eventID);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Failed to delete Event", e);
            return ResponseEntity.badRequest().build();
        }
    }
    /*
    @DeleteMapping("/deleteEvent")
    public ResponseEntity<String> deleteEvent(@RequestBody Event request) {
        try {
            eventService.deleteEvent(request.getEventID());
            log.info("Event successfully deleted: {}", request.getEventID());
            return ResponseEntity.ok(
                    "Event successfully deleted: "
                            + request.getTitle()
            );
        } catch (EntityNotFoundException e) {
            log.error("Event not found", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Event not found.");
        } catch (Exception e) {
            log.error("Failed to delete Event", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while deleting the pet.");
        }
    }
     */

    private void copyRequestToEvent(@RequestBody Event request, Event e) {
        e.setCenter(request.getCenter());
        e.setTitle(request.getTitle());
        if (request.getEventPicture() != null) {
            e.setEventPicture(request.getEventPicture());
        }
        e.setDescription(request.getDescription());
        e.setLocation(request.getLocation());
        e.setEndDateTime(request.getEndDateTime());
        e.setStartDateTime(request.getStartDateTime());

    }
}
