package petadoption.api.meeting;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.apache.bcel.generic.TABLESWITCH;
import org.springframework.format.annotation.DateTimeFormat;
import petadoption.api.adoptionCenter.AdoptionCenter;
import petadoption.api.user.User;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = Meeting.TABLE_NAME)
public class Meeting {
    public static final String TABLE_NAME = "MEETING";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MEETING_ID")
    private Long id;

    @Column(name = "MEET_DATE")
    @DateTimeFormat(pattern = "dd/MM/YYYY")
    LocalDateTime date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", referencedColumnName = "USER_ID")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "adoption_id", referencedColumnName = "ADOPTION_ID")
    private AdoptionCenter center;


    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public AdoptionCenter getCenter() {
        return center;
    }

    public void setCenter(AdoptionCenter center) {
        this.center = center;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
