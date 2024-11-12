package petadoption.api.Utility;

import petadoption.api.pet.criteria.*;
import petadoption.api.pet.criteria.breed.CatBreed;
import petadoption.api.pet.criteria.breed.DogBreed;

public class EnumUtil {

    public static Species mapStringToSpecies(String speciesStr) {
        return Species.valueOf(convertToEnumFormat(speciesStr));
    }

    public static Size mapStringToSize(String sizeStr) {
        return Size.valueOf(convertToEnumFormat(sizeStr));
    }

    public static CoatLength mapStringToCoatLength(String coatLengthStr) {
        return CoatLength.valueOf(convertToEnumFormat(coatLengthStr));
    }

    public static FurType mapStringToFurType(String furTypeStr) {
        return FurType.valueOf(convertToEnumFormat(furTypeStr));
    }

    public static FurColor mapStringToFurColor(String furColorStr) {
        return FurColor.valueOf(convertToEnumFormat(furColorStr));
    }

    public static DogBreed mapStringToDogBreed(String dogBreedStr) {
        return DogBreed.valueOf(convertToEnumFormat(dogBreedStr));
    }

    public static CatBreed mapStringToCatBreed(String catBreedStr) {
        return CatBreed.valueOf(convertToEnumFormat(catBreedStr));
    }

    public static Temperament mapStringToTemperament(String temperamentStr) {
        return Temperament.valueOf(convertToEnumFormat(temperamentStr));
    }

    public static Health mapStringToHealth(String healthStr) {
        return Health.valueOf(convertToEnumFormat(healthStr));
    }

    public static Sex mapStringToSex(String sexStr) {
        return Sex.valueOf(convertToEnumFormat(sexStr));
    }

    public static SpayedNeutered mapStringToSpayedNeutered(String spayedStr) {
        return SpayedNeutered.valueOf(convertToEnumFormat(spayedStr));
    }
    // Helper method to format strings to match enum values
    private static String convertToEnumFormat(String str) {
        return str.trim().toUpperCase().replace(" ", "_");
    }

}