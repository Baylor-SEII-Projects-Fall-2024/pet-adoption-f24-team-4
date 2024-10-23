package petadoption.api.meeting;

import jakarta.persistence.*;
import lombok.Data;
import petadoption.api.adoptionCenter.AdoptionCenter;
import petadoption.api.pet.Pet;
import petadoption.api.user.User;

import java.util.Date;
import java.util.Optional;

@Data
@Entity
@Table(name = Meeting.TABLE_NAME)
public class Meeting {
    public static final String TABLE_NAME = "Meetings";
    @Id
    @GeneratedValue(generator = TABLE_NAME + "_GENERATOR")
    @SequenceGenerator(
            name = TABLE_NAME + "_GENERATOR",
            sequenceName = TABLE_NAME + "_SEQUENCE"
    )
    @Column(name = "meetingID")
    Long id;

    @Column(name = "meetingDate")
    Date date;

    @ManyToOne
    @JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID", nullable = true)
    private User user;

    // Pet contains adoption center info
    @ManyToOne
    @JoinColumn(name = "petID", referencedColumnName = "petID", nullable = true)
    private Pet pet;

    public Meeting(Pet pet, User user, Date date) {
        setPet(pet);
        setDate(date);
        setUser(user);
    }

    public Meeting() { }

    public Long getId() { return id; }
    public void setId(Long id){ this.id = id; }


    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Pet getPet() { return pet; }

    public void setPet(Pet pet) { this.pet = pet; }
}
