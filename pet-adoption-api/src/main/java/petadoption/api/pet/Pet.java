package petadoption.api.pet;

import jakarta.persistence.*;
import lombok.Data;
import petadoption.api.Utility.Image;
import petadoption.api.adoptionCenter.AdoptionCenter;
import petadoption.api.user.User;

@Data
@Entity
@Table(name = Pet.TABLE_NAME)
public class Pet {
    public static final String TABLE_NAME = "Pets";
    @Id
    @GeneratedValue(generator = TABLE_NAME + "_GENERATOR")
    @SequenceGenerator(
            name = TABLE_NAME + "_GENERATOR",
            sequenceName = TABLE_NAME + "_SEQUENCE"
    )
    @Column(name = "petID")
    Long id;

    @Column(name = "FIRST_NAME")
    String firstName;

    @Column(name = "LAST_NAME")
    String lastName;

    @Column(name = "PET_TYPE")
    String petType;

    @Column(name = "WEIGHT")
    int weight;

    @Column(name = "FUR_TYPE")
    String furType;

    @ManyToOne
    @JoinColumn(name = "adoptionID", referencedColumnName = "adoptionID", nullable = true)
    private AdoptionCenter center;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PICTURE_ID", referencedColumnName = "id")
    Image profilePicture;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getFurType() {
        return furType;
    }

    public void setFurType(String furType) {
        this.furType = furType;
    }
}
