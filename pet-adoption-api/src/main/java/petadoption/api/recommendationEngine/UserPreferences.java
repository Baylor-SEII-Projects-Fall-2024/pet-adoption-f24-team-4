package petadoption.api.recommendationEngine;

import jakarta.persistence.*;
import lombok.Getter;
import petadoption.api.pet.criteria.*;
import petadoption.api.pet.criteria.breed.CatBreed;
import petadoption.api.pet.criteria.breed.DogBreed;
import petadoption.api.user.User;

import java.util.HashMap;
import java.util.Map;

@Getter
@Entity
@Table(name = UserPreferences.TABLE_NAME)
public class UserPreferences {
    public static final String TABLE_NAME = "user_preferences";

    @Getter
    @Id
    @GeneratedValue(generator = TABLE_NAME + "_GENERATOR")
    @SequenceGenerator(
            name = TABLE_NAME + "_GENERATOR",
            sequenceName = TABLE_NAME + "_SEQUENCE"
    )
    @Column(name = "user_preferences_id")
    private Long userPreferencesId;

    @OneToOne(optional = false, mappedBy = "userPreferences")
    private User user;

    @ElementCollection
    @MapKeyColumn(name = "species")
    @Column(name = "species_rating")
    @CollectionTable(name = "user_preferences_species",
            joinColumns=@JoinColumn(name = "user_preferences_id"))
    Map<Species, Double> species;

    /*
    @ElementCollection
    @MapKeyColumn(name = "breed")
    @Column(name = "breed_rating")
    @CollectionTable(name = "user_preferences_breed",
            joinColumns=@JoinColumn(name = "user_preferences_id"))
    Map<Breed, Double> breed;
     */

    @ElementCollection
    @MapKeyColumn(name = "cat_breed")
    @Column(name = "cat_breed_rating")
    @CollectionTable(name = "user_preferences_cat_breed",
            joinColumns=@JoinColumn(name = "user_preferences_id"))
    Map<CatBreed, Double> catBreed;

    @ElementCollection
    @MapKeyColumn(name = "dog_breed")
    @Column(name = "dog_breed_rating")
    @CollectionTable(name = "user_preferences_dog_breed",
            joinColumns=@JoinColumn(name = "user_preferences_id"))
    Map<DogBreed, Double> dogBreed;

    @ElementCollection
    @MapKeyColumn(name = "size")
    @Column(name = "size_rating")
    @CollectionTable(name = "user_preferences_size",
            joinColumns=@JoinColumn(name = "user_preferences_id"))
    Map<Size, Double> size;

    @ElementCollection
    @MapKeyColumn(name = "fur_color")
    @Column(name = "fur_color_rating")
    @CollectionTable(name = "user_preferences_fur_color",
            joinColumns=@JoinColumn(name = "user_preferences_id"))
    Map<FurColor, Double> furColor;

    @ElementCollection
    @MapKeyColumn(name = "coat_length")
    @Column(name = "coat_length_rating")
    @CollectionTable(name = "user_preferences_coat_length",
            joinColumns=@JoinColumn(name = "user_preferences_id"))
    Map<CoatLength, Double> coatLength;

    @ElementCollection
    @MapKeyColumn(name = "age")
    @Column(name = "age_rating")
    @CollectionTable(name = "user_preferences_age",
            joinColumns=@JoinColumn(name = "user_preferences_id"))
    Map<Integer, Double> age;

    @ElementCollection
    @MapKeyColumn(name = "temperament")
    @Column(name = "temperament_rating")
    @CollectionTable(name = "user_preferences_temperament",
            joinColumns=@JoinColumn(name = "user_preferences_id"))
    Map<Temperament, Double> temperament;

    @ElementCollection
    @MapKeyColumn(name = "health")
    @Column(name = "health_rating")
    @CollectionTable(name = "user_preferences_health",
            joinColumns=@JoinColumn(name = "user_preferences_id"))
    Map<Health, Double> health;

    public UserPreferences() {
        species = new HashMap<>();
        //breed = new HashMap<>();
        catBreed = new HashMap<>();
        dogBreed = new HashMap<>();
        size = new HashMap<>();
        furColor = new HashMap<>();
        coatLength = new HashMap<>();
        age = new HashMap<>();
        temperament = new HashMap<>();
        health = new HashMap<>();
    }

    public Double getSpeciesRating(Species s) {
        if (species.containsKey(s)) {
            return species.get(s);
        }
        return 0.0;
    }

    public void setSpeciesRating(Species s, Double rating) {
        species.put(s, rating);
    }

    public void updateSpeciesRating(Species s, Double value) {
        if (species.containsKey(s)) {
            species.put(s, (species.get(s) + value));
        } else {
            species.put(s, value);
        }
    }

    public Double getCatBreedRating(CatBreed c) {
        if (catBreed.containsKey(c)) {
            return catBreed.get(c);
        }
        return 0.0;
    }

    public void setCatBreedRating(CatBreed c, Double rating) {
        catBreed.put(c, rating);
    }

    public void updateCatBreedRating(CatBreed c, Double value) {
        if (catBreed.containsKey(c)) {
            catBreed.put(c, (catBreed.get(c) + value));
        } else {
            catBreed.put(c, value);
        }
    }

    public Double getDogBreedRating(DogBreed d) {
        if (dogBreed.containsKey(d)) {
            return dogBreed.get(d);
        }
        return 0.0;
    }

    public void setDogBreedRating(DogBreed d, Double rating) {
        dogBreed.put(d, rating);
    }

    public void updateDogBreedRating(DogBreed d, Double value) {
        if (dogBreed.containsKey(d)) {
            dogBreed.put(d, (dogBreed.get(d) + value));
        } else {
            dogBreed.put(d, value);
        }
    }

    /*
    public Double getBreedRating(Breed b) {
        if (breed.containsKey(b)) {
            return breed.get(b);
        }
        return 0.0;
    }

    public void setBreedRating(Breed b, Double rating) {
        breed.put(b, rating);
    }

    public void updateBreedRating(Breed b, Double value) {
        if (breed.containsKey(b)) {
            breed.put(b, (breed.get(b) + value));
        } else {
            breed.put(b, value);
        }
    }
     */

    public Double getSizeRating(Size s) {
        if (size.containsKey(s)) {
            return size.get(s);
        }
        return 0.0;
    }

    public void setSizeRating(Size s, Double rating) {
        size.put(s, rating);
    }

    public void updateSizeRating(Size s, Double value) {
        if (size.containsKey(s)) {
            size.put(s, (size.get(s) + value));
        } else {
            size.put(s, value);
        }
    }

    public Double getFurColorRating(FurColor fc) {
        if (furColor.containsKey(fc)) {
            return furColor.get(fc);
        }
        return 0.0;
    }

    public void setFurColorRating(FurColor fc, Double rating) {
        furColor.put(fc, rating);
    }

    public void updateFurColorRating(FurColor fc, Double value) {
        if (furColor.containsKey(fc)) {
            furColor.put(fc, (furColor.get(fc) + value));
        } else {
            furColor.put(fc, value);
        }
    }

    public Double getCoatLengthRating(CoatLength cl) {
        if (coatLength.containsKey(cl)) {
            return coatLength.get(cl);
        }
        return 0.0;
    }

    public void setCoatLengthRating(CoatLength cl, Double rating) {
        coatLength.put(cl, rating);
    }

    public void updateCoatLengthRating(CoatLength cl, Double value) {
        if (coatLength.containsKey(cl)) {
            coatLength.put(cl, (coatLength.get(cl) + value));
        } else {
            coatLength.put(cl, value);
        }
    }

    public Double getAgeRating(int a) {
        if (age.containsKey(a)) {
            return age.get(a);
        }
        return 0.0;
    }

    public void setAgeRating(int a, Double rating) {
        age.put(a, rating);
    }

    public void updateAgeRating(int a, Double value) {
        if (age.containsKey(a)) {
            age.put(a, (age.get(a) + value));
        } else {
            age.put(a, value);
        }
    }

    public Double getTemperamentRating(Temperament t) {
        if (temperament.containsKey(t)) {
            return temperament.get(t);
        }
        return 0.0;
    }

    public void setTemperamentRating(Temperament t, Double rating) {
        temperament.put(t, rating);
    }

    public void updateTemperamentRating(Temperament t, Double value) {
        if (temperament.containsKey(t)) {
            temperament.put(t, (temperament.get(t) + value));
        } else {
            temperament.put(t, value);
        }
    }

    public Double getHealthRating(Health h) {
        if (health.containsKey(h)) {
            return health.get(h);
        }
        return 0.0;
    }

    public void setHealthRating(Health h, Double rating) {
        health.put(h, rating);
    }

    public void updateHealthRating(Health h, Double value) {
        if (health.containsKey(h)) {
            health.put(h, (health.get(h) + value));
        } else {
            health.put(h, value);
        }
    }
}