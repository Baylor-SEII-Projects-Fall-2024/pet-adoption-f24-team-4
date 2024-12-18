package petadoption.api.Utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import petadoption.api.adoptionCenter.AdoptionCenter;
import petadoption.api.adoptionCenter.AdoptionCenterService;
import petadoption.api.pet.Pet;
import petadoption.api.pet.PetService;

import java.util.*;

import static petadoption.api.pet.criteria.Attribute.*;

@Component
public class petGenerator {

    private static final int DEFAULT_NUM_PETS = 50;
    private static final String[] NAMES = {
            "Angel", "Popeye", "Squishy", "Abigail", "Red", "Fern Lily", "Akiro", "Boots", "Swiper",
            "Dora", "McFluffin", "Iggy", "Waggs", "Samson", "Lucky", "Diamond", "Zeus", "Bella",
            "Casanova", "Sketch", "Allen", "Toot", "Gale", "Sammy", "Marvin", "Waffles", "Robbie",
            "Houdini", "Wayne", "Dumplin", "Knox", "Potato", "Bert", "Porkchop", "Chase", "Barbie",
            "Silver", "Brody", "Raven", "Stephen", "Sprite", "Kate", "Mandarin"
    };

    @Autowired
    private PetService petService;

    @Autowired
    private AdoptionCenterService adoptionCenterService;

    public void generatePetsOnStartup() {
        List<AdoptionCenter> adoptionCenters = createAndSaveAdoptionCenters();
        generateAndSavePets(DEFAULT_NUM_PETS, adoptionCenters);

    }



    private List<AdoptionCenter> createAndSaveAdoptionCenters() {
        List<AdoptionCenter> adoptionCenters = Arrays.asList(
                new AdoptionCenter("Happy Paws Adoption Center", "123 Main St, Houston, TX", "Providing loving homes to pets in need. Open 7 days a week!"),
                new AdoptionCenter("Forever Friends Shelter", "456 Elm St, Austin, TX", "A no-kill shelter with a variety of pets ready for adoption."),
                new AdoptionCenter("Houston Humane Society", "789 Oak St, Houston, TX", "Rescue, care, and adoption of homeless pets. Serving the community for over 50 years."),
                new AdoptionCenter("Pet Haven", "1010 Birch St, Dallas, TX", "Specializing in pet rescues and providing forever homes to abandoned pets."),
                new AdoptionCenter("Furry Friends Foundation", "111 Pine St, San Antonio, TX", "Dedicated to helping pets find loving families and supporting pet owners in need.")
        );

        adoptionCenters.forEach(adoptionCenterService::saveCenter); // Save each center to the database
        return adoptionCenters;
    }

    public void generateAndSavePets(int numPets, List<AdoptionCenter> adoptionCenters) {
        List<Pet> pets = generateRandomPets(numPets, adoptionCenters);
        pets.forEach(petService::savePet);
    }



    private List<Pet> generateRandomPets(int numPets, List<AdoptionCenter> adoptionCenters) {
        Random random = new Random();
        List<Pet> pets = new ArrayList<>();

        for (int i = 0; i < numPets; i++) {
            Pet pet = new Pet();
            HashSet<String> attributes = new HashSet<>();

            // name
            pet.setName(NAMES[random.nextInt(NAMES.length)]);

            // species
            attributes.add(buildAttribute(
                            typeList[0],
                            speciesList[random.nextInt(speciesList.length)]
            ));

            // breed
            // variable below will be reused for other multi-row records
            int numAttr = random.nextInt(1, 6);
            if (attributes.contains("Species:Cat")) {
                for (int j = 0; j < numAttr; j++) {
                    attributes.add(buildAttribute(
                            typeList[1],
                            catBreedList[random.nextInt(catBreedList.length)]
                    ));
                }
                // add "Domestic Shorthair" tag
                if (numAttr != 1) {
                    attributes.add(buildAttribute(typeList[1], catBreedList[0]));
                }
            } else {
                for (int j = 0; j < numAttr; j++) {
                    attributes.add(buildAttribute(
                            typeList[2],
                            dogBreedList[random.nextInt(dogBreedList.length)]
                    ));
                }
                // add "Mix-Breed" tag
                if (numAttr != 1) {
                    attributes.add(buildAttribute(typeList[2], dogBreedList[0]));
                }
            }

            // fur type
            attributes.add(buildAttribute(
                            typeList[3],
                            furTypeList[random.nextInt(furTypeList.length)]
            ));

            // fur color
            numAttr = random.nextInt(1, 6);
            for (int j = 0; j < numAttr; j++) {
                attributes.add(buildAttribute(
                        typeList[4],
                        furColorList[random.nextInt(furColorList.length)]
                ));
            }

            // fur length
            attributes.add(buildAttribute(
                    typeList[5],
                    furLengthList[random.nextInt(furLengthList.length)]
            ));

            // size
            attributes.add(buildAttribute(
                    typeList[6],
                    sizeList[random.nextInt(sizeList.length)]
            ));

            // health
            attributes.add(buildAttribute(
                    typeList[7],
                    healthList[random.nextInt(healthList.length)]
            ));

            // gender
            attributes.add(buildAttribute(
                    typeList[8],
                    genderList[random.nextInt(genderList.length)]

            ));

            // spayed / neutered
            attributes.add(buildAttribute(
                    typeList[9],
                    spayedNeuteredList[random.nextInt(spayedNeuteredList.length)]
            ));

            // temperament
            numAttr = random.nextInt(1, 6);
            for (int j = 0; j < numAttr; j++) {
                attributes.add(buildAttribute(
                        typeList[10],
                        temperamentList[random.nextInt(temperamentList.length)]
                ));
            }

            // age
            attributes.add(buildAttribute(
                    typeList[11],
                    Integer.toString(random.nextInt(1, 51))
            ));

            // weight
            attributes.add(buildAttribute(
                    typeList[12],
                    Integer.toString(random.nextInt(1, 401))
            ));

            // Randomly assign an adoption center to the pet
            if (adoptionCenters != null && !adoptionCenters.isEmpty()) {
                pet.setCenter(adoptionCenters.get(random.nextInt(adoptionCenters.size())));
            }

            pet.setAttributes(attributes);
            pets.add(pet);
        }
        return pets;
    }
}