package petadoption.api.recommendationEngine;

import lombok.extern.log4j.Log4j2;
import petadoption.api.pet.Pet;
import petadoption.api.pet.criteria.FurColor;
import petadoption.api.pet.criteria.Species;
import petadoption.api.pet.criteria.Temperament;
import petadoption.api.pet.criteria.breed.CatBreed;
import petadoption.api.pet.criteria.breed.DogBreed;

/**
 * @author Rafe Loya
 *
 * @see UserPreferences
 * @see Pet
 */
@Log4j2
public class RecommendationEngine {
    /**
     * Default value used to increment / decrement
     */
    public static final double DEFAULT_VAL = 0.001;

    /**
     * Default value for incrementing after attempting to adopt pet
     */
    public static final double DEFAULT_ADOPT_VAL = 0.050;

    /**
     * Logs the species that caused the error and
     * throws a new <code>RunTimeException</code>
     *
     * @param s <code>Species</code> that caused the error
     */
    private void unknownSpeciesError(Species s) throws RuntimeException {
        log.error("Unknown species encountered: {}", s);
        throw new RuntimeException("Unknown species encountered: " + s);
    }

    /**
     * Determines <code>Breed</code> associated with the pet,
     * then iterates through each <code>Breed</code>,
     * summing the values of each associated rating.
     *
     * @param up <code>User</code>'s specific preferences
     * @param p  <code>Pet</code> to calculate ratings from
     * @return   sum of ratings associated with pet's breeds
     */
    public double calculateBreedRating(UserPreferences up, Pet p) {
        double rating = 0.0;

        switch(p.getSpecies()) {
            case CAT:
                for (CatBreed cb : p.getCatBreed()) {
                    rating += up.getCatBreedRating(cb);
                }
                break;
            case DOG:
                for (DogBreed db : p.getDogBreed()) {
                    rating += up.getDogBreedRating(db);
                }
                break;
            default:
                unknownSpeciesError(p.getSpecies());
        }

        return rating;
    }

    /**
     * Iterates through <code>Pet</code>'s <code>FurColor</code>s,
     * summing the values of each associated rating.
     *
     * @param up <code>User</code>'s specific preferences
     * @param p  <code>Pet</code> to calculate ratings from
     * @return   sum of ratings associated with pet's fur colors
     */
    public double calculateFurColorRating(UserPreferences up, Pet p) {
        double rating = 0.0;

        for (FurColor fc : p.getFurColor()) {
            rating += up.getFurColorRating(fc);
        }

        return rating;
    }

    /**
     * Iterates through <code>Pet</code>'s <code>Temperament</code>s,
     * summing the values of each associated rating.
     *
     * @param up <code>User</code>'s specific preferences
     * @param p  <code>Pet</code> to calculate ratings from
     * @return   sum of ratings associated with pet's temperaments
     */
    public double calculateTemperamentRating(UserPreferences up, Pet p) {
        double rating = 0.0;

        for (Temperament t : p.getTemperament()) {
            rating += up.getTemperamentRating(t);
        }

        return rating;
    }

    /**
     * Calculates a <code>Pet</code>'s total rating through
     * the members in <Code>UserPreferences</Code>, which map to
     * the specific criterion's rating.
     *
     * @param up <code>User</code>'s specific preferences
     * @param p  <code>Pet</code> to calculate ratings from
     * @return   given pet's total rating
     */
    public double calculatePetRating(UserPreferences up, Pet p) {
        double totalRating = 0.0;

        totalRating += up.getSpeciesRating(p.getSpecies());               // Species
        totalRating += calculateBreedRating(up, p);                       // Breed
        totalRating += up.getSizeRating(p.getPetSize());                  // Size
        totalRating += calculateFurColorRating(up, p);                    // Fur Color
        totalRating += up.getFurTypeRating(p.getFurType());               // Fur Type
        totalRating += up.getCoatLengthRating(p.getCoatLength());         // Coat Length
        totalRating += up.getAgeRating(p.getAge());                       // Age
        totalRating += calculateTemperamentRating(up, p);                 // Temperament
        totalRating += up.getHealthRating(p.getHealthStatus());           // Health
        totalRating += up.getSpayedNeuteredRating(p.getSpayedNeutered()); // Spayed / Neutered
        totalRating += up.getSexRating(p.getSex());                       // Sex

        return totalRating;
    }

    /**
     * Changes a given <code>User</code>'s <code>UserPreferences</code>
     * according to a passed <code>double</code>.
     * <p>
     * This function is intended to be a helper function for
     * <code>ratePet()</code></code> and <code>rateAdoptedPet()</code>,
     * but can be called directly for custom increment / decrement values.
     *
     * @param up        <code>User</code>'s specific preferences
     * @param p         <code>Pet</code> to reference criteria from
     * @param updateVal value to increment / decrement critera ratings by
     */
    public void updatePreferences(UserPreferences up, Pet p, double updateVal) {
        //double updateVal = like ? DEFAULT_VAL : -(DEFAULT_VAL);

        // Species
        up.updateSpeciesRating(p.getSpecies(), updateVal);

        // Breed
        switch(p.getSpecies()) {
            case CAT:
                for (CatBreed cb : p.getCatBreed()) {
                    up.updateCatBreedRating(cb, updateVal);
                }
                break;
            case DOG:
                for (DogBreed db : p.getDogBreed()) {
                    up.updateDogBreedRating(db, updateVal);
                }
                break;
            default:
                unknownSpeciesError(p.getSpecies());
        }

        // Size
        up.updateSizeRating(p.getPetSize(), updateVal);

        // Fur Color
        for (FurColor fc : p.getFurColor()) {
            up.updateFurColorRating(fc, updateVal);
        }

        // Fur Type
        up.updateFurTypeRating(p.getFurType(), updateVal);

        // Coat Length
        up.updateCoatLengthRating(p.getCoatLength(), updateVal);

        // Age
        up.updateAgeRating(p.getAge(), updateVal);

        // Temperament
        for (Temperament t : p.getTemperament()) {
            up.updateTemperamentRating(t, updateVal);
        }

        // Health
        up.updateHealthRating(p.getHealthStatus(), updateVal);

        // Spayed / Neutered
        up.updateSpayedNeuteredRating(p.getSpayedNeutered(), updateVal);

        // Sex
        up.updateSexRating(p.getSex(), updateVal);
    }

    /**
     * Increments / decrements the ratings in the given <code>UserPreferences</code>
     * mapped by the criteria contained in a passed <code>Pet</code> instance.
     *
     * @param up   <code>User</code>'s specific preferences
     * @param p    <code>Pet</code> to reference criteria from
     * @param like if the <code>User</code> liked the pet
     */
    public void ratePet(UserPreferences up, Pet p, boolean like) {
        double updateVal = like ? DEFAULT_VAL : -(DEFAULT_VAL);

        updatePreferences(up, p, updateVal);
    }

    /**
     * Increments the ratings in the given <code>UserPreferences</code>
     * mapped by the criteria contained in a passed <code>Pet</code>
     * instance. The amount that the criteria ratings will be incremented by
     * is significantly larger than <code>ratePet()</code>.
     * <p/>
     * This function is intended to be used when a <code>User</code>
     * contacts the <code>AdoptionCenter</code> associated with the given
     * <code>Pet</code>.
     *
     * @param up <code>User</code>'s specific preferences
     * @param p  <code>Pet</code> to reference criteria from
     */
    public void rateAdoptedPet(UserPreferences up, Pet p) {
        updatePreferences(up, p, DEFAULT_ADOPT_VAL);
    }
}