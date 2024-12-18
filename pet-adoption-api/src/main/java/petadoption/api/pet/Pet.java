package petadoption.api.pet;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import petadoption.api.Utility.Image;
import petadoption.api.adoptionCenter.AdoptionCenter;

import java.util.HashSet;
import java.util.Set;

import static petadoption.api.pet.criteria.Attribute.*;

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

    @Getter
    @Setter
    @Column(name = "name")
    private String name;

    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "pet_attributes", joinColumns = @JoinColumn(name = "petID"))
    @Column(name = "attributes")
    private Set<String> attributes;

    @Getter
    @Setter
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PICTURE_ID", referencedColumnName = "id")
    private Image profilePicture;

    @Getter
    @Setter
    @ManyToOne
    @JoinColumn(name = "adoptionID", referencedColumnName = "adoptionID", nullable = true)
    private AdoptionCenter center;

    public Pet(String name, HashSet<String> attributes) {
        this.name = name;
        this.attributes = attributes;
    }

    public Pet() {}

    /**
     * Assigns a set of attributes to a pet. This function has
     * multiple checks for the following:
     * <ul>
     *     <li>Single attribute only has one record of it in the set (ex. age).</li>
     *     <li>Multivalued attribute has at least one record (ex. temperament).</li>
     *     <li>Species of the pet only has breeds relevant to it.</li>
     * </ul>
     * <p>If one of these checks fail, an <code>IllegalArgumentException</code>
     * will be thrown by the program and the attributes will not be assigned
     * to the pet.</p>
     *
     * @param attributes Set of attributes relevant to pet
     * @throws IllegalArgumentException If any of the following above is violated.
     */
    public void setAttributes(Set<String> attributes) {
        boolean species = false,
                catBreed = false,
                dogBreed = false,
                furType = false,
                furColor = false,
                furLength = false,
                size = false,
                health = false,
                gender = false,
                spayedNeutered = false,
                temperament = false,
                age = false,
                weight = false;
        String speciesType = "";

        /* Although a set would prohibit multiple elements,
         * this attribute checking is intended to ensure the following:
         *      - There is at least one occurrence of necessary attributes
         *      - Attributes that can only appear a single time do not
         *          have multiple entries (ex. age, gender, species, etc.)
         *      - Species only have breeds that are relevant to them
         *          (i.e. a cat does not contain dog breeds, v.v.)
         */
        for (String a : attributes) {
            String[] attribute = splitAttribute(a);

            if (!verifyAttributeFormat(attribute)) {
                throw new IllegalArgumentException(
                        "Attribute does not follow proper format\n"
                        + "[type]: " + attribute[TYPE] + "\n"
                        + "[attribute] " + attribute[ATTR]
                );
            }
            switch (attribute[TYPE]) {
                case "Species":
                    if (species) {
                        throw new IllegalArgumentException("Multiple species records found");
                    }
                    speciesType = attribute[ATTR];
                    species = true;
                    break;
                case "Cat Breed":
                    if (dogBreed) {
                        throw new IllegalArgumentException("Dog Breed attr. found for cat");
                    }
                    catBreed = true;
                    break;
                case "Dog Breed":
                    if (catBreed) {
                        throw new IllegalArgumentException("Cat Breed attr. found for dog");
                    }
                    dogBreed = true;
                    break;
                case "Fur Type":
                    if (furType) {
                        throw new IllegalArgumentException("Multiple fur type records found");
                    }
                    furType = true;
                    break;
                case "Fur Color":
                    furColor = true;
                    break;
                case "Fur Length":
                    if (furLength) {
                        throw new IllegalArgumentException("Multiple fur length records found");
                    }
                    furLength = true;
                    break;
                case "Size":
                    if (size) {
                        throw new IllegalArgumentException("Multiple size records found");
                    }
                    size = true;
                    break;
                case "Health":
                    if (health) {
                        throw new IllegalArgumentException("Multiple health records found");
                    }
                    health = true;
                    break;
                case "Gender":
                    if (gender) {
                        throw new IllegalArgumentException("Multiple gender records found");
                    }
                    gender = true;
                    break;
                case "Spayed / Neutered":
                    if (spayedNeutered) {
                        throw new IllegalArgumentException("Multiple spayed neutered records found");
                    }
                    spayedNeutered = true;
                    break;
                case "Temperament":
                    temperament = true;
                    break;
                case "Age":
                    if (age) {
                        throw new IllegalArgumentException("Multiple age records found");
                    }
                    age = true;
                    break;
                case "Weight":
                    if (weight) {
                        throw new IllegalArgumentException("Multiple weight records found");
                    }
                    weight = true;
                    break;
                default:
                    throw new IllegalArgumentException("Invalid attr. type found: " + attribute[TYPE]);
            }
        }

        if (!species) {
            throw new IllegalArgumentException("No species record found");
        }
        if (speciesType.equals("Cat") && !catBreed) {
            throw new IllegalArgumentException("No cat breed record found for cat");
        }
        if (speciesType.equals("Dog") && !dogBreed) {
            throw new IllegalArgumentException("No dog breed record found for dog");
        }
        if (!furType) {
            throw new IllegalArgumentException("No fur type record found");
        }
        if (!furColor) {
            throw new IllegalArgumentException("No fur color record found");
        }
        if (!size) {
            throw new IllegalArgumentException("No size record found");
        }
        if (!health) {
            throw new IllegalArgumentException("No health record found");
        }
        if (!gender) {
            throw new IllegalArgumentException("No gender record found");
        }
        if (!spayedNeutered) {
            throw new IllegalArgumentException("No spayed neutered record found");
        }
        if (!temperament) {
            throw new IllegalArgumentException("No temperament record found");
        }
        if (!age) {
            throw new IllegalArgumentException("No age record found");
        }
        if (!weight) {
            throw new IllegalArgumentException("No weight record found");
        }

        this.attributes = new HashSet<>(attributes);
    }

    public String toString() {
        StringBuilder str = new StringBuilder();

        str.append("//==============================================//\n");
        str.append("[id]   ").append(this.id).append("\n");
        str.append("[name] ").append(this.name).append("\n");
        str.append("[attributes]\n");
        for (String attr : this.attributes) {
            String[] a = splitAttribute(attr);
            str.append("\t [").append(a[TYPE]).append("] ")
                    .append(a[ATTR]).append("\n");
        }
        str.append("[Adoption Center] ").append(this.center).append("\n");

        return str.toString();
    }
}
