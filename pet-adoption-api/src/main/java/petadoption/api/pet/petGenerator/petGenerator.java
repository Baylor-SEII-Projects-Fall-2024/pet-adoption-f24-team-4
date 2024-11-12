package petadoption.api.pet.petGenerator;



import petadoption.api.pet.criteria.*;
import petadoption.api.pet.criteria.breed.CatBreed;
import petadoption.api.pet.criteria.breed.DogBreed;

import java.util.Random;
import java.util.Scanner;

public class petGenerator {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of pets to generate: ");
        int numberOfPets = scanner.nextInt();
        generateRandomPets(numberOfPets);
    }

    private static void generateRandomPets(int count) {
        Random random = new Random();
        Species[] speciesValues = Species.values();
        CoatLength[] coatLengthValues = CoatLength.values();
        FurColor[] furColorValues = FurColor.values();
        FurType[] furTypeValues = FurType.values();
        Health[] healthValues = Health.values();
        Size[] sizeValues = Size.values();
        Temperament[] temperamentValues = Temperament.values();
        Sex[] sexValues = Sex.values();
        SpayedNeutered[] spayedNeuteredValues = SpayedNeutered.values();

        for (int i = 0; i < count; i++) {
            Species randomSpecies = speciesValues[random.nextInt(speciesValues.length)];
            CoatLength randomCoatLength = coatLengthValues[random.nextInt(coatLengthValues.length)];
            FurColor randomFurColor = furColorValues[random.nextInt(furColorValues.length)];
            FurType randomFurType = furTypeValues[random.nextInt(furTypeValues.length)];
            Health randomHealth = healthValues[random.nextInt(healthValues.length)];
            Size randomSize = sizeValues[random.nextInt(sizeValues.length)];
            Temperament randomTemperament = temperamentValues[random.nextInt(temperamentValues.length)];
            Sex randomSex = sexValues[random.nextInt(sexValues.length)];
            SpayedNeutered randomSpayed = spayedNeuteredValues[random.nextInt(spayedNeuteredValues.length)];


            String randomBreed;
            if (randomSpecies == Species.CAT) {
                CatBreed[] catBreeds = CatBreed.values();
                randomBreed = catBreeds[random.nextInt(catBreeds.length)].getDisplayName();
            } else if (randomSpecies == Species.DOG) {
                DogBreed[] dogBreeds = DogBreed.values();
                randomBreed = dogBreeds[random.nextInt(dogBreeds.length)].getDisplayName();
            } else {
                randomBreed = "Unknown Breed";
            }
            System.out.println(randomSpecies.getDisplayName() + "," + randomBreed + "," +
                    randomCoatLength.getDisplayName() + "," + randomFurColor.getDisplayName() + "," +
                    randomFurType.getDisplayName() + "," + randomHealth.getDisplayName() + "," +
                    randomSize.getDisplayName() + "," + randomTemperament.getDisplayName() + "," + randomSex.getDisplayName()
                    + "," + randomSpayed.getDisplayName()
            );
        }
    }
}
