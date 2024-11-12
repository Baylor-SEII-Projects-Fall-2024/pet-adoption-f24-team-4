package petadoption.api.pet;

import jakarta.persistence.*;
import lombok.Data;
import petadoption.api.Utility.Image;
import petadoption.api.adoptionCenter.AdoptionCenter;
import petadoption.api.pet.criteria.*;
import petadoption.api.pet.criteria.breed.AnimalBreed;
import petadoption.api.pet.criteria.breed.CatBreed;
import petadoption.api.pet.criteria.breed.DogBreed;

import java.util.Set;

import static petadoption.api.pet.criteria.Species.CAT;

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
    private Long id;

    @Column(name = "name")
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "SPECIES")
    private Species species;

    @Column(name = "WEIGHT")
    private int weight;

    @Enumerated(EnumType.STRING)
    @Column(name = "COAT_LENGTH")
    private CoatLength coatLength;

    @Enumerated(EnumType.STRING)
    @Column(name = "FUR_TYPE")
    private FurType furType;

    @Enumerated(EnumType.STRING)
    @Column(name = "FUR_COLOR")
    private Set<FurColor> furColor;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PICTURE_ID", referencedColumnName = "id")
    private Image profilePicture;

    @ManyToOne
    @JoinColumn(name = "adoptionID", referencedColumnName = "adoptionID", nullable = true)
    private AdoptionCenter center;

    @Enumerated(EnumType.STRING)
    @Column(name = "DOG_BREED") // Added breed column
    private Set<DogBreed> dogBreed;

    @Enumerated(EnumType.STRING)
    @Column(name = "CAT_BREED") // Added breed column
    private Set<CatBreed> catBreed;

    @Enumerated(EnumType.STRING) // Use this annotation if petSize is an enum
    @Column(name = "PET_SIZE") // Added petSize column
    private Size petSize;

    @Column(name = "AGE") // Added age column
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "TEMPERAMENT") // Added temperament column
    private Set<Temperament> temperament;

    @Enumerated(EnumType.STRING)
    @Column(name = "HEALTH_STATUS") // Added healthStatus column
    private Health healthStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "SPAYED_NEUTERED")
    private SpayedNeutered spayedNeutered;

    @Enumerated(EnumType.STRING)
    @Column(name = "SEX")
    private Sex sex;

    public Pet(
            String firstName,
            Species species,
            int weight,
            CoatLength coatLength,
            FurType furType,
            Set<FurColor> furColor,
            AdoptionCenter center ,
            Set<DogBreed> dogBreeds,
            Set<CatBreed> catBreeds,
            Size petSize, int age,
            Set<Temperament> temperament,
            Health healthStatus,
            SpayedNeutered spayedNeutered,
            Sex sex) {
        this.name = firstName;
        this.species = species;
        this.weight = weight;
        this.coatLength = coatLength;
        this.furType = furType;
        this.furColor = furColor;
        this.center = center;
        this.dogBreed = dogBreeds;
        this.catBreed = catBreeds;
        this.petSize = petSize;
        this.age = age;
        this.temperament = temperament;
        this.healthStatus = healthStatus;
        this.spayedNeutered = spayedNeutered;
        this.sex = sex;
    }

    public Pet() {}

    public String toString() {
        StringBuilder str = new StringBuilder();

        // [id] : name
        str.append("[" + id + "] : " + name + "\n");
        str.append("adoption center: " + center.getCenterName() + "\n");
        str.append("species: " + species.getDisplayName() + "\n");
        str.append("sex: " + sex.getDisplayName() + "\n");
        if (species.ordinal() == 0) {
            str.append("dog breed(s):\n");
            for (DogBreed db : dogBreed) {
                str.append("\t" + db.getDisplayName() + "\n");
            }
        } else {
            str.append("cat breed(s):\n");
            for (CatBreed cb : catBreed) {
                str.append("\t" + cb.getDisplayName() + "\n");
            }
        }
        str.append("weight: " + weight + "\n");
        str.append("size: " + petSize + "\n");
        str.append("fur type: " + furType + "\n");
        str.append("coat length: " + coatLength + "\n");
        str.append("fur color(s):\n");
        for (FurColor fc : furColor) {
            str.append("\t" + fc + "\n");
        }
        str.append("temperament(s):\n");
        for (Temperament t : temperament) {
            str.append("\t" + t.getDisplayName() + "\n");
        }
        str.append("health status: " + healthStatus + "\n");
        str.append("spayed / neutered?: " + (spayedNeutered.ordinal() == 0 ? "Y" : "N") + "\n");

        return str.toString();
    }

}
