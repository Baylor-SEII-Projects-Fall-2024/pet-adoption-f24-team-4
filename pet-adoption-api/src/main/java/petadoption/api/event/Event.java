package petadoption.api.event;

import jakarta.persistence.*;
import lombok.Data;
import petadoption.api.Utility.Image;
import petadoption.api.adoptionCenter.AdoptionCenter;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = Event.TABLE_NAME)
public class Event {
    public static final String TABLE_NAME = "events";

    @Id
    @GeneratedValue(generator = TABLE_NAME + "_GENERATOR")
    @SequenceGenerator(
            name = TABLE_NAME + "_GENERATOR",
            sequenceName = TABLE_NAME + "_SEQUENCE"
    )
    @Column(name = "EVENT_ID")
    private Long eventID;

    @ManyToOne
    @JoinColumn(name = "adoptionID", referencedColumnName = "adoptionID", nullable = false)
    private AdoptionCenter center;

    @Column(name = "TITLE")
    private String title;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PICTURE_ID", referencedColumnName = "id")
    private Image eventPicture;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "START_DATE_TIME")
    private LocalDateTime startDateTime;

    @Column(name = "END_DATE_TIME")
    private LocalDateTime endDateTime;

    public Event() {}

    public void setStartDateTime(LocalDateTime startDateTime) {
        if (endDateTime != null && startDateTime.isAfter(endDateTime)) {
            throw new IllegalArgumentException("Start date-time cannot be after end date-time.");
        }
        this.startDateTime = startDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        if (startDateTime != null && endDateTime.isBefore(startDateTime)) {
            throw new IllegalArgumentException("End date-time cannot be before start date-time.");
        }
        this.endDateTime = endDateTime;
    }
}