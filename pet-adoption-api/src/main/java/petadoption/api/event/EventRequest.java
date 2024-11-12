package petadoption.api.event;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EventRequest {
    private Long adoptionID;
    private Long imageID;
    private String title;
    private String description;
    private String location;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
}
