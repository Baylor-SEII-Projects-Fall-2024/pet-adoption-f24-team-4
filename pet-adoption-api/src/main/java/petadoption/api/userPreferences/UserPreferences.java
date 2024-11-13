package petadoption.api.userPreferences;

import jakarta.persistence.*;
import lombok.Getter;
import petadoption.api.pet.criteria.*;
import petadoption.api.pet.criteria.breed.CatBreed;
import petadoption.api.pet.criteria.breed.DogBreed;
import petadoption.api.user.User;

import java.util.HashMap;
import java.util.Map;
import java.util.EnumMap;

/**
 * @see User
 * @see Species
 * @see CatBreed
 * @see DogBreed
 * @see Size
 * @see FurColor
 * @see FurType
 * @see CoatLength
 * @see Temperament
 * @see Health
 * @see SpayedNeutered
 * @see Sex
 *
 * @author Rafe Loya
 * @author Will Clore
 */

@Entity
@Table(name = "user_preferences")
public class UserPreferences {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_preferences_id;

    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_cat_breed_preferences", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "cat_breed")
    @Column(name = "preference")
    private Map<CatBreed, Double> catBreedPreferences;

    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_dog_breed_preferences", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "dog_breed")
    @Column(name = "preference")
    private Map<DogBreed, Double> dogBreedPreferences;

    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_coat_length_preferences", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "coat_length")
    @Column(name = "preference")
    private Map<CoatLength, Double> coatLengthPreferences;

    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_fur_color_preferences", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "fur_color")
    @Column(name = "preference")
    private Map<FurColor, Double> furColorPreferences;

    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_fur_type_preferences", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "fur_type")
    @Column(name = "preference")
    private Map<FurType, Double> furTypePreferences;

    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_health_preferences", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "health")
    @Column(name = "preference")
    private Map<Health, Double> healthPreferences;

    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_sex_preferences", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "sex")
    @Column(name = "preference")
    private Map<Sex, Double> sexPreferences;

    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_size_preferences", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "size")
    @Column(name = "preference")
    private Map<Size, Double> sizePreferences;

    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_spayed_neutered_preferences", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "spayed_neutered")
    @Column(name = "preference")
    private Map<SpayedNeutered, Double> spayedNeuteredPreferences;

    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_species_preferences", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "species")
    @Column(name = "preference")
    private Map<Species, Double> speciesPreferences;

    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_temperament_preferences", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "temperament")
    @Column(name = "preference")
    private Map<Temperament, Double> temperamentPreferences;

    @Getter
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_age_preferences", joinColumns = @JoinColumn(name = "user_id"))
    @MapKeyColumn(name = "age")
    @Column(name = "preference")
    private Map<Integer, Double> agePreferences;

    public UserPreferences() {
        speciesPreferences = new EnumMap<>(Species.class);
        catBreedPreferences = new EnumMap<>(CatBreed.class);
        dogBreedPreferences = new EnumMap<>(DogBreed.class);
        sizePreferences = new EnumMap<>(Size.class);
        furColorPreferences = new EnumMap<>(FurColor.class);
        furTypePreferences = new EnumMap<>(FurType.class);
        coatLengthPreferences = new EnumMap<>(CoatLength.class);
        agePreferences = new HashMap<>();
        temperamentPreferences = new EnumMap<>(Temperament.class);
        healthPreferences = new EnumMap<>(Health.class);
        spayedNeuteredPreferences = new EnumMap<>(SpayedNeutered.class);
        sexPreferences = new EnumMap<>(Sex.class);
    }

    public UserPreferences(
            Map<Species, Double> spe,
            Map<CatBreed, Double> cb,
            Map<DogBreed, Double> db,
            Map<Size, Double> si,
            Map<FurColor, Double> fc,
            Map<FurType, Double> ft,
            Map<CoatLength, Double> cl,
            Map<Integer, Double> a,
            Map<Temperament, Double> t,
            Map<Health, Double> h,
            Map<SpayedNeutered, Double> spa,
            Map<Sex, Double> se) {
        this.speciesPreferences = spe;
        this.catBreedPreferences = cb;
        this.dogBreedPreferences = db;
        this.sizePreferences = si;
        this.furColorPreferences = fc;
        this.furTypePreferences = ft;
        this.coatLengthPreferences = cl;
        this.agePreferences = a;
        this.temperamentPreferences = t;
        this.healthPreferences = h;
        this.spayedNeuteredPreferences = spa;
        this.sexPreferences = se;
    }


    public Long getId() {
        return user_preferences_id;
    }

    public void resetRatings() {
        speciesPreferences.clear();
        catBreedPreferences.clear();
        dogBreedPreferences.clear();
        sizePreferences.clear();
        furColorPreferences.clear();
        furTypePreferences.clear();
        coatLengthPreferences.clear();
        agePreferences.clear();
        temperamentPreferences.clear();
        healthPreferences.clear();
        spayedNeuteredPreferences.clear();
        sexPreferences.clear();
    }

    public Double getSpeciesRating(Species s) {
        if (speciesPreferences.containsKey(s)) {
            return speciesPreferences.get(s);
        }
        return 0.0;
    }

    public void setSpeciesRating(Species s, Double rating) {
        speciesPreferences.put(s, rating);
    }

    public void updateSpeciesRating(Species s, Double value) {
        if (speciesPreferences.containsKey(s)) {
            speciesPreferences.put(s, (speciesPreferences.get(s) + value));
        } else {
            speciesPreferences.put(s, value);
        }
    }

    public void clearSpeciesRating() { speciesPreferences.clear(); }

    public Double getCatBreedRating(CatBreed c) {
        if (catBreedPreferences.containsKey(c)) {
            return catBreedPreferences.get(c);
        }
        return 0.0;
    }

    public void setCatBreedRating(CatBreed c, Double rating) {
        catBreedPreferences.put(c, rating);
    }

    public void updateCatBreedRating(CatBreed c, Double value) {
        if (catBreedPreferences.containsKey(c)) {
            catBreedPreferences.put(c, (catBreedPreferences.get(c) + value));
        } else {
            catBreedPreferences.put(c, value);
        }
    }

    public void clearCatBreedRating() { catBreedPreferences.clear(); }

    public Double getDogBreedRating(DogBreed d) {
        if (dogBreedPreferences.containsKey(d)) {
            return dogBreedPreferences.get(d);
        }
        return 0.0;
    }

    public void setDogBreedRating(DogBreed d, Double rating) {
        dogBreedPreferences.put(d, rating);
    }

    public void updateDogBreedRating(DogBreed d, Double value) {
        if (dogBreedPreferences.containsKey(d)) {
            dogBreedPreferences.put(d, (dogBreedPreferences.get(d) + value));
        } else {
            dogBreedPreferences.put(d, value);
        }
    }

    public void clearDogBreedRating() { dogBreedPreferences.clear(); }

    public Double getSizeRating(Size s) {
        if (sizePreferences.containsKey(s)) {
            return sizePreferences.get(s);
        }
        return 0.0;
    }

    public void setSizeRating(Size s, Double rating) {
        sizePreferences.put(s, rating);
    }

    public void updateSizeRating(Size s, Double value) {
        if (sizePreferences.containsKey(s)) {
            sizePreferences.put(s, (sizePreferences.get(s) + value));
        } else {
            sizePreferences.put(s, value);
        }
    }

    public void clearSizeRating() { sizePreferences.clear(); }

    public Double getFurColorRating(FurColor fc) {
        if (furColorPreferences.containsKey(fc)) {
            return furColorPreferences.get(fc);
        }
        return 0.0;
    }

    public void setFurColorRating(FurColor fc, Double rating) {
        furColorPreferences.put(fc, rating);
    }

    public void updateFurColorRating(FurColor fc, Double value) {
        if (furColorPreferences.containsKey(fc)) {
            furColorPreferences.put(fc, (furColorPreferences.get(fc) + value));
        } else {
            furColorPreferences.put(fc, value);
        }
    }

    public void clearFurColorRating() { furColorPreferences.clear(); }

    public Double getFurTypeRating(FurType ft) {
        if (furTypePreferences.containsKey(ft)) {
            return furTypePreferences.get(ft);
        }
        return 0.0;
    }

    public void setFurTypeRating(FurType ft, Double rating) {
        furTypePreferences.put(ft, rating);
    }

    public void updateFurTypeRating(FurType ft, Double value) {
        if (furTypePreferences.containsKey(ft)) {
            furTypePreferences.put(ft, (furTypePreferences.get(ft) + value));
        } else {
            furTypePreferences.put(ft, value);
        }
    }

    public void clearFurTypeRating() { furTypePreferences.clear(); }

    public Double getCoatLengthRating(CoatLength cl) {
        if (coatLengthPreferences.containsKey(cl)) {
            return coatLengthPreferences.get(cl);
        }
        return 0.0;
    }

    public void setCoatLengthRating(CoatLength cl, Double rating) {
        coatLengthPreferences.put(cl, rating);
    }

    public void updateCoatLengthRating(CoatLength cl, Double value) {
        if (coatLengthPreferences.containsKey(cl)) {
            coatLengthPreferences.put(cl, (coatLengthPreferences.get(cl) + value));
        } else {
            coatLengthPreferences.put(cl, value);
        }
    }

    public void clearCoatLengthRating() { coatLengthPreferences.clear(); }

    public Double getAgeRating(int a) {
        if (agePreferences.containsKey(a)) {
            return agePreferences.get(a);
        }
        return 0.0;
    }

    public void setAgeRating(int a, Double rating) {
        agePreferences.put(a, rating);
    }

    public void updateAgeRating(int a, Double value) {
        if (agePreferences.containsKey(a)) {
            agePreferences.put(a, (agePreferences.get(a) + value));
        } else {
            agePreferences.put(a, value);
        }
    }

    public void clearAgeRating() { agePreferences.clear(); }

    public Double getTemperamentRating(Temperament t) {
        if (temperamentPreferences.containsKey(t)) {
            return temperamentPreferences.get(t);
        }
        return 0.0;
    }

    public void setTemperamentRating(Temperament t, Double rating) {
        temperamentPreferences.put(t, rating);
    }

    public void updateTemperamentRating(Temperament t, Double value) {
        if (temperamentPreferences.containsKey(t)) {
            temperamentPreferences.put(t, (temperamentPreferences.get(t) + value));
        } else {
            temperamentPreferences.put(t, value);
        }
    }

    public void clearTemperamentRating() { temperamentPreferences.clear(); }

    public Double getHealthRating(Health h) {
        if (healthPreferences.containsKey(h)) {
            return healthPreferences.get(h);
        }
        return 0.0;
    }

    public void setHealthRating(Health h, Double rating) {
        healthPreferences.put(h, rating);
    }

    public void updateHealthRating(Health h, Double value) {
        if (healthPreferences.containsKey(h)) {
            healthPreferences.put(h, (healthPreferences.get(h) + value));
        } else {
            healthPreferences.put(h, value);
        }
    }

    public void clearHealthRating() { healthPreferences.clear(); }

    public Double getSpayedNeuteredRating(SpayedNeutered sn) {
        if (spayedNeuteredPreferences.containsKey(sn)) {
            return spayedNeuteredPreferences.get(sn);
        }
        return 0.0;
    }

    public void setSpayedNeuteredRating(SpayedNeutered sn, Double rating) {
        spayedNeuteredPreferences.put(sn, rating);
    }

    public void updateSpayedNeuteredRating(SpayedNeutered sn, Double value) {
        if (spayedNeuteredPreferences.containsKey(sn)) {
            spayedNeuteredPreferences.put(sn, (spayedNeuteredPreferences.get(sn) + value));
        } else {
            spayedNeuteredPreferences.put(sn, value);
        }
    }

    public void clearSpayedNeuteredRating() { spayedNeuteredPreferences.clear(); }

    public Double getSexRating(Sex s) {
        if (sexPreferences.containsKey(s)) {
            return sexPreferences.get(s);
        }
        return 0.0;
    }

    public void setSexRating(Sex s, Double rating) {
        sexPreferences.put(s, rating);
    }

    public void updateSexRating(Sex s, Double value) {
        if (sexPreferences.containsKey(s)) {
            sexPreferences.put(s, (sexPreferences.get(s) + value));
        } else {
            sexPreferences.put(s, value);
        }
    }

    public void clearSexRating() { sexPreferences.clear(); }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) { return true; }
        if (obj == null || obj.getClass() != this.getClass()) { return false; }

        UserPreferences up = (UserPreferences)obj;
        return (
                (this.speciesPreferences != null
                        && up.speciesPreferences != null
                        && up.speciesPreferences.equals(this.speciesPreferences))
                && (this.catBreedPreferences != null
                        && up.catBreedPreferences != null
                        && up.catBreedPreferences.equals(this.catBreedPreferences))
                && (this.dogBreedPreferences != null
                        && up.dogBreedPreferences != null
                        && up.dogBreedPreferences.equals(this.dogBreedPreferences))
                && (this.sizePreferences != null
                        && up.sizePreferences != null
                        && up.sizePreferences.equals(this.sizePreferences))
                && (this.agePreferences != null
                        && up.agePreferences != null
                        && up.agePreferences.equals(this.agePreferences))
                && (this.furColorPreferences != null
                        && up.furColorPreferences != null
                        && up.furColorPreferences.equals(this.furColorPreferences))
                && (this.furTypePreferences != null
                        && up.furTypePreferences != null
                        && up.furTypePreferences.equals(this.furTypePreferences))
                && (this.coatLengthPreferences != null
                        && up.coatLengthPreferences != null
                        && up.coatLengthPreferences.equals(this.coatLengthPreferences))
                && (this.temperamentPreferences != null
                        && up.temperamentPreferences != null
                        && up.temperamentPreferences.equals(this.temperamentPreferences))
                && (this.healthPreferences != null
                        && up.healthPreferences != null
                        && up.healthPreferences.equals(this.healthPreferences))
                && (this.spayedNeuteredPreferences != null
                        && up.spayedNeuteredPreferences != null
                        && up.spayedNeuteredPreferences.equals(this.spayedNeuteredPreferences))
                && (this.sexPreferences != null
                        && up.sexPreferences != null
                        && up.sexPreferences.equals(this.sexPreferences))
        );
    }
}