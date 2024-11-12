//package petadoption.api.event;
//
//import jakarta.transaction.Transactional;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//import petadoption.api.adoptionCenter.AdoptionCenter;
//import petadoption.api.adoptionCenter.AdoptionCenterRepository;
//import petadoption.api.adoptionCenter.AdoptionCenterService;
//
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest
//@ActiveProfiles("testdb")  // make these tests use the H2 in-memory DB instead of your actual DB
//@Transactional
//public class EventTests {
//    @Autowired
//    private EventRepository eventRepository;
//
//    @Autowired
//    private EventService eventService;
//
//    @Autowired
//    private AdoptionCenterRepository adoptionCenterRepository;
//
//    @Autowired
//    private AdoptionCenterService adoptionCenterService;
//
//    @BeforeEach
//    void setUp() {
//        eventRepository.deleteAll();
//        adoptionCenterRepository.deleteAll();
//    }
//
//    @Test
//    void testEventIsValidDate() {
//        String[] validDateTimes = {
//                "2011-12-03T10:15:30",
//                "2024-10-21T00:07:59",
//                "1999-12-31T23:59:59"
//        };
//        String[] invalidDateTimes = {
//                "9999-99-99T99:99:99", // Out of bounds date / time
//                "bruh",                // ...not a date / time
//                "2024-10-21 00:00:00", // Missing 'T' between date and time
//        };
//
//        for (String vdt : validDateTimes) {
//            assertTrue(Event.isValidDateTime(vdt), vdt + " should be a valid date");
//        }
//        for (String idt : invalidDateTimes) {
//            assertFalse(Event.isValidDateTime(idt), idt + " should not be a valid date");
//        }
//    }
//
//    @Test
//    void testSetDateTime() {
//        String[] validStartDateTimes = {
//                "2011-12-03T10:15:30",
//                "2024-10-21T00:07:59",
//                "1999-12-31T23:59:59"
//        };
//        String[] validEndDateTimes = {
//                "2002-10-02T10:15:30",
//                "2024-10-21T09:13:59",
//                "2000-01-01T00:00:00"
//        };
//        String[] invalidDateTimes = {
//                "9999-99-99T99:99:99", // Out of bounds date / time
//                "bruh",                // ...not a date / time
//                "2024-10-21 00:00:00", // Missing 'T' between date and time
//        };
//        Event e = new Event();
//
//        // Successful startDateTime assignments (W/O endDateTime)
//        for (String vdt : validStartDateTimes) {
//            e.setStartDateTime(vdt);
//        }
//
//        // Successful endDateTime assignments
//        // startDateTime is validStartDateTimes[2]
//        for (String idt : validEndDateTimes) {
//            e.setEndDateTime(idt);
//        }
//
//        e.setEndDateTime(validEndDateTimes[2]);
//
//        // Improper startDateTime assignment (time later than edt)
//        try {
//            e.setStartDateTime(validStartDateTimes[0]);
//            System.out.println(
//                    "ERR: failed to throw exception for SDT: "
//                            + validStartDateTimes[0]
//                            + " and EDT: "
//                            + validEndDateTimes[2]
//            );
//            assert(false);
//        } catch (IllegalArgumentException ex) {
//            System.out.println("Success: " + ex.getMessage());
//        }
//        try {
//            e.setStartDateTime(validStartDateTimes[1]);
//            System.out.println(
//                    "ERR: failed to throw exception for SDT: "
//                            + validStartDateTimes[1]
//                            + " and EDT: "
//                            + validEndDateTimes[2]
//            );
//            assert(false);
//        } catch (IllegalArgumentException ex) {
//            System.out.println("Success: " + ex.getMessage());
//        }
//
//        // Setting to same time
//        e.setStartDateTime(validStartDateTimes[2]);
//        e.setEndDateTime(validStartDateTimes[2]);
//
//        // Improper endDateTime assignment
//        e.setEndDateTime("2100-12-31T23:59:59"); // temp value
//        e.setStartDateTime(validStartDateTimes[1]);
//        try {
//            e.setEndDateTime(validEndDateTimes[0]);
//            System.out.println(
//                    "ERR: failed to throw exception for EDT: "
//                            + validEndDateTimes[0]
//                            + " and SDT: "
//                            + validStartDateTimes[1]
//            );
//        } catch (IllegalArgumentException ex) {
//            System.out.println("Success: " + ex.getMessage());
//        }
//    }
//
//    @Test
//    void testEventServiceSaveEvent() {
//        AdoptionCenter ac = new AdoptionCenter();
//        ac.setCenterName("Tester Center");
//        ac.setBuildingAddress("12345 Test St., Testville, TX");
//        ac.setDescription("A loving adoption center");
//
//        AdoptionCenter acSaveResult = adoptionCenterService.saveCenter(ac);
//        Optional<AdoptionCenter> acGetResult = adoptionCenterService.getCenter(
//                acSaveResult.getAdoptionID()
//        );
//        assertTrue(acGetResult.isPresent(), "Test center was not saved");
//
//        Event e = new Event();
//        e.setCenter(ac);
//        e.setTitle("Testing Event Title");
//        e.setDescription("Testing Event Description");
//
//        Event eSaveResult = eventService.saveEvent(e);
//        assertNotNull(eSaveResult, "Failed to save event");
//    }
//
//    @Test
//    void testEventServiceGetEventById() {
//        AdoptionCenter ac = new AdoptionCenter();
//        ac.setCenterName("Tester Center");
//        ac.setBuildingAddress("12345 Test St., Testville, TX");
//        ac.setDescription("A loving adoption center");
//
//        AdoptionCenter acSaveResult = adoptionCenterService.saveCenter(ac);
//        Optional<AdoptionCenter> acGetResult = adoptionCenterService.getCenter(
//                acSaveResult.getAdoptionID()
//        );
//        assertTrue(acGetResult.isPresent(), "Test center was not saved");
//
//        Event e = new Event();
//        e.setCenter(ac);
//        e.setTitle("Testing Event Title");
//        e.setDescription("Testing Event Description");
//
//        Event eSaveResult = eventService.saveEvent(e);
//        assertNotNull(eSaveResult, "Failed to save event");
//        Optional<Event> eGetResult = eventService.getEventById(
//                eSaveResult.getEventID()
//        );
//        assertTrue(eGetResult.isPresent(), "Failed to get event");
//        assertNotNull(
//                eGetResult.get().getEventID(),
//                "Event was not saved successfully / ID was not generated"
//        );
//        assertNotNull(
//                eGetResult.get().getCenter().getAdoptionID(),
//                "Event not associated with AdoptionCenter"
//        );
//    }
//
//    @Test
//    void testEventServiceDeleteEvent() {
//        AdoptionCenter ac = new AdoptionCenter();
//        ac.setCenterName("Tester Center");
//        ac.setBuildingAddress("12345 Test St., Testville, TX");
//        ac.setDescription("A loving adoption center");
//
//        AdoptionCenter acSaveResult = adoptionCenterService.saveCenter(ac);
//        Optional<AdoptionCenter> acGetResult = adoptionCenterService.getCenter(
//                acSaveResult.getAdoptionID()
//        );
//
//        Event e = new Event();
//        e.setCenter(ac);
//        e.setTitle("Testing Event Title");
//        e.setDescription("Testing Event Description");
//
//        Event eSaveResult = eventService.saveEvent(e);
//        assertNotNull(eSaveResult, "Failed to save event");
//        Optional<Event> eGetResult = eventService.getEventById(
//                eSaveResult.getEventID()
//        );
//        assertTrue(eGetResult.isPresent(), "Failed to get event");
//        assertNotNull(
//                eGetResult.get().getEventID(),
//                "Event was not saved successfully / ID was not generated"
//        );
//        assertNotNull(
//                eGetResult.get().getCenter().getAdoptionID(),
//                "Event not associated with AdoptionCenter"
//        );
//
//        eventService.deleteEvent(eGetResult.get().getEventID());
//        assertTrue(
//                eventService.getEventById(eGetResult.get().getEventID()).isEmpty(),
//                "Event was not deleted"
//        );
//    }
//
//    @Test
//    void testEventServiceGetEventsFromCenter() {
//        AdoptionCenter ac = new AdoptionCenter();
//        ac.setCenterName("Tester Center");
//        ac.setBuildingAddress("12345 Test St., Testville, TX");
//        ac.setDescription("A loving adoption center");
//
//        AdoptionCenter acSaveResult = adoptionCenterService.saveCenter(ac);
//        Optional<AdoptionCenter> acGetResult = adoptionCenterService.getCenter(
//                acSaveResult.getAdoptionID()
//        );
//
//        for (int i = 0; i < 3; i++) {
//            Event e = new Event();
//            e.setCenter(ac);
//            e.setTitle("Testing Event Title" + i);
//            e.setDescription("Testing Event Description");
//
//            Event eSaveResult = eventService.saveEvent(e);
//            assertNotNull(eSaveResult, "Failed to save event");
//        }
//
//        List<Event> events = eventService.getEventsFromCenter(acSaveResult.getAdoptionID());
//        assertNotNull(events, "Failed to get events");
//        int i = 0;
//        for (Event e : events) {
//            assertEquals("Testing Event Title" + i, e.getTitle());
//            i++;
//        }
//    }
//}
