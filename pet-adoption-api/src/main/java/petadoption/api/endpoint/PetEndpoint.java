package petadoption.api.endpoint;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import petadoption.api.Utility.petGenerator;
import petadoption.api.adoptionCenter.AdoptionCenter;
import petadoption.api.adoptionCenter.AdoptionCenterService;
import petadoption.api.pet.Pet;
import petadoption.api.pet.PetRequest;
import petadoption.api.pet.PetService;
import petadoption.api.pet.criteria.*;
import petadoption.api.pet.criteria.breed.CatBreed;
import petadoption.api.pet.criteria.breed.DogBreed;

import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

import static petadoption.api.pet.criteria.Attribute.*;

@Log4j2
@RestController
public class PetEndpoint {
    private PetService petService;
    private AdoptionCenterService adoptionCenterService;

    public PetEndpoint(PetService petService, AdoptionCenterService adoptionCenterService) {
        this.petService = petService;
        this.adoptionCenterService = adoptionCenterService;
    }

    /**
     * Organizes all per-defined attributes into a 2-dimensional array,
     * with the first index representing the organized types and the
     * second index being the attributes under that type
     *
     * @return types and their associated attributes
     */
    @GetMapping("/loadAttributes")
    public List<List<String>> loadAttributes() {
        List<List<String>> attributes = new ArrayList<>();

        // length reduced by 2 due to "Age" and "Weight" attribute
        // not having an attribute list
        for (int i = 0; i < (typeList.length - 2); ++i) {
            String[] attributeList;
            if ((attributeList = mapAttributeList(typeList[i])) != null) {
                attributes.add(Arrays.stream(attributeList).toList());
            }
        }

        return attributes;
    }

    @PostMapping("/addPet")
    public ResponseEntity<?> addPet(@RequestBody PetRequest petRequest) {
        try {
            Optional<AdoptionCenter> adoptionCenter
                    = adoptionCenterService.getCenter(petRequest.adoptionID);

            if (adoptionCenter.isEmpty()) {
                return ResponseEntity.badRequest().body("Adoption center not found");
            }

            Pet pet = new Pet();
            pet.setName(petRequest.name);
            pet.setAttributes(petRequest.attributes);
            /*
            pet.setName(petRequest.getName());
            pet.setSpecies(petRequest.getSpecies());
            pet.setPetSize(petRequest.getPetSize());
            pet.setWeight(petRequest.getWeight());
            pet.setAge(petRequest.getAge());
            pet.setTemperament(petRequest.getTemperament());
            pet.setCoatLength(petRequest.getCoatLength());
            pet.setFurType(petRequest.getFurType());
            pet.setHealthStatus(petRequest.getHealthStatus());
            pet.setFurColor(petRequest.getFurColor());
            pet.setSpayedNeutered(petRequest.getSpayedNeutered());
            log.info(petRequest.getSex());
            pet.setSex(petRequest.getSex());
            if (petRequest.getDogBreed() != null) {
                pet.setDogBreed(petRequest.getDogBreed());
            }
            if (petRequest.getCatBreed() != null) {
                pet.setCatBreed(petRequest.getCatBreed());
            }
            */

            pet.setCenter(adoptionCenter.get());

            petService.savePet(pet, adoptionCenter.get().getAdoptionID());
            log.info(
                    "Pet registered to adoption center {}",
                    pet.getCenter().getCenterName()
            );
            return ResponseEntity.ok(pet);
        } catch (Exception e) {
            log.error("Error adding pet", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/pets")
    public ResponseEntity<?> getAllPets() {
        try {
            List<Pet> pets = petService.getAllPets();
            return ResponseEntity.ok(pets);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching pets: " + e.getMessage());
        }
    }

    @GetMapping("/samplePets")
    public String addSampleCenters() throws IOException {
        System.out.println("1");

        //petService.addSamplePets(adoptionCenterService);
        return "Sample pets added successfully.";
    }


    @GetMapping("/pets/{adoptionID}")
    public ResponseEntity<?> getAdoptionCenterPets(@PathVariable Long adoptionID) {
        try {
            List<Pet> pets = petService.getAdoptionCenterPets(adoptionID);
            return ResponseEntity.ok(pets);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching pets: " + e.getMessage());
        }
    }


    @GetMapping("/pets-sample")
    public ResponseEntity<?> samplePets() {
        try {
            System.out.println("START");
            adoptionCenterService.addSampleAdoptionCenters();
            List<AdoptionCenter> adoptionCenters = adoptionCenterService.getAllAdoptionCenters();
            List<Pet> pets =   petService.addSamplePets(adoptionCenters);
            pets.forEach(petService::savePet);
            return ResponseEntity.ok("SUCCESS");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error fetching pets: " + e.getMessage());
        }
    }

    @DeleteMapping("/deletePet")
    public ResponseEntity<String> deletePet(@RequestBody Pet pet) {
        try {
            petService.deletePet(pet.getId());
            return ResponseEntity.ok("Pet deleted successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Pet not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while deleting the pet.");
        }
    }

    @PutMapping("/updatePet")
    public ResponseEntity<Pet> updatePet(@RequestBody PetRequest petRequest) {
        try {
            Optional<Pet> existingPetOpt = petService.getPetById(petRequest.id);
            if (existingPetOpt.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            Pet pet = existingPetOpt.get();


            pet.setName(petRequest.getName());
            pet.setAttributes(petRequest.attributes);
            /*
            pet.setSpecies(petRequest.getSpecies());
            pet.setPetSize(petRequest.getPetSize());
            pet.setWeight(petRequest.getWeight());
            pet.setAge(petRequest.getAge());
            pet.setTemperament(petRequest.getTemperament());
            pet.setCoatLength(petRequest.getCoatLength());
            pet.setFurType(petRequest.getFurType());
            pet.setFurColor(petRequest.getFurColor());
             */

            petService.savePet(pet, pet.getCenter().getAdoptionID());

            log.info("Pet updated successfully: {}", pet.getName());
            return ResponseEntity.ok(pet);
        } catch (Exception e) {
            log.error("Error updating pet: ", e);
            return ResponseEntity.badRequest().build();
        }

    }
    @GetMapping("/getOptions")
    public Map<String, List<String>> getPetEnumOptions() {
        return Map.ofEntries(
                Map.entry("species", Arrays.stream(Species.values()).map(Species::getDisplayName).collect(Collectors.toList())),
                Map.entry("coatLength", Arrays.stream(CoatLength.values()).map(CoatLength::getDisplayName).collect(Collectors.toList())),
                Map.entry("furType", Arrays.stream(FurType.values()).map(FurType::getDisplayName).collect(Collectors.toList())),
                Map.entry("furColor", Arrays.stream(FurColor.values()).map(FurColor::getDisplayName).collect(Collectors.toList())),
                Map.entry("dogBreed", Arrays.stream(DogBreed.values()).map(DogBreed::getDisplayName).collect(Collectors.toList())),
                Map.entry("catBreed", Arrays.stream(CatBreed.values()).map(CatBreed::getDisplayName).collect(Collectors.toList())),
                Map.entry("size", Arrays.stream(Size.values()).map(Size::getDisplayName).collect(Collectors.toList())),
                Map.entry("temperament", Arrays.stream(Temperament.values()).map(Temperament::getDisplayName).collect(Collectors.toList())),
                Map.entry("healthStatus", Arrays.stream(Health.values()).map(Health::getDisplayName).collect(Collectors.toList())),
                Map.entry("sex", Arrays.stream(Sex.values()).map(Sex::getDisplayName).collect(Collectors.toList())),
                Map.entry("spayedNeutered", Arrays.stream(SpayedNeutered.values()).map(SpayedNeutered::getDisplayName).collect(Collectors.toList()))
        );
    }
}
