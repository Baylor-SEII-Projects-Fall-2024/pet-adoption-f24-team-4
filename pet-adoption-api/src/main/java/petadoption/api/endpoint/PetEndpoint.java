package petadoption.api.endpoint;

import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Temporal;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import petadoption.api.adoptionCenter.AdoptionCenter;
import petadoption.api.adoptionCenter.AdoptionCenterService;
import petadoption.api.pet.Pet;
import petadoption.api.pet.PetRequest;
import petadoption.api.pet.PetService;
import petadoption.api.pet.criteria.*;
import petadoption.api.pet.criteria.breed.CatBreed;
import petadoption.api.pet.criteria.breed.DogBreed;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@RestController
public class PetEndpoint {
    private PetService petService;
    private  AdoptionCenterService adoptionCenterService;
    public PetEndpoint(PetService petService, AdoptionCenterService adoptionCenterService) {
        this.petService = petService;
        this.adoptionCenterService = adoptionCenterService;
    }
    @PostMapping("/addPet")
    public ResponseEntity<?> addPet(@RequestBody PetRequest petRequest) {
        try {
            Optional<AdoptionCenter> adoptionCenter = adoptionCenterService.getCenter(petRequest.getAdoptionId());

            if (adoptionCenter.isEmpty()) {
                return ResponseEntity.badRequest().body("Adoption center not found");
            }

            Pet pet = new Pet();
            pet.setName(petRequest.getName());
            pet.setSpecies(petRequest.getSpecies());
            pet.setPetSize(petRequest.getPetSize());
            pet.setWeight(petRequest.getWeight());
            pet.setAge(petRequest.getAge()); // Set the age
            pet.setTemperament(petRequest.getTemperament());
            pet.setCoatLength(petRequest.getCoatLength());
            pet.setFurType(petRequest.getFurType());
            pet.setFurColor(petRequest.getFurColor());
            if(petRequest.getDogBreed() != null){
                pet.setDogBreed(petRequest.getDogBreed());
            }
            if(petRequest.getCatBreed() != null){
                pet.setCatBreed(petRequest.getCatBreed());
            }

            pet.setCenter(adoptionCenter.get());

            petService.savePet(pet, adoptionCenter.get().getAdoptionID());
            log.info("Pet registered to adoption center " + pet.getCenter().getCenterName());
            return ResponseEntity.ok(pet);
        } catch (Exception e) {
            log.error("Error adding pet", e);
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/pets")
    public ResponseEntity<?> getAllPets() {
        System.out.println("hello");
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

        petService.addSamplePets(adoptionCenterService);
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
    public ResponseEntity<Pet> updatePet(@RequestBody PetRequest petRequest){
        try {
            Optional<Pet> existingPetOpt = petService.getPetById(petRequest.getId());
            if (!existingPetOpt.isPresent()) {
                return ResponseEntity.notFound().build();
            }
            Pet existingPet = existingPetOpt.get();


            existingPet.setName(petRequest.getName());
            //existingPet.setPetType(petRequest.getPetType());
            existingPet.setWeight(petRequest.getWeight());
            //existingPet.setFurType(petRequest.getFurType());
            existingPet.setPetSize(petRequest.getPetSize());
            //existingPet.setBreed(petRequest.getBreed());
            existingPet.setAge(petRequest.getAge());
            existingPet.setTemperament(petRequest.getTemperament());
            //existingPet.setHealthStatus(petRequest.getHealthStatus());


            petService.savePet(existingPet, existingPet.getCenter().getAdoptionID());

            log.info("Pet updated successfully: " + existingPet.getName());
            return ResponseEntity.ok(existingPet);
        }
        catch (Exception e) {
            log.error("Error updating pet: ", e);
            return ResponseEntity.badRequest().build();
        }

    }

    @GetMapping("/getOptions")
    public Map<String, List<String>> getPetEnumOptions() {
        return Map.of(
                "species", Arrays.stream(Species.values()).map(Species::getDisplayName).collect(Collectors.toList()),
                "coatLength", Arrays.stream(CoatLength.values()).map(CoatLength::getDisplayName).collect(Collectors.toList()),
                "furType", Arrays.stream(FurType.values()).map(FurType::getDisplayName).collect(Collectors.toList()),
                "furColor", Arrays.stream(FurColor.values()).map(FurColor::getDisplayName).collect(Collectors.toList()),
                "dogBreed", Arrays.stream(DogBreed.values()).map(DogBreed::getDisplayName).collect(Collectors.toList()),
                "catBreed", Arrays.stream(CatBreed.values()).map(CatBreed::getDisplayName).collect(Collectors.toList()),
                "size", Arrays.stream(Size.values()).map(Size::getDisplayName).collect(Collectors.toList()),
                "temperament", Arrays.stream(Temperament.values()).map(Temperament::getDisplayName).collect(Collectors.toList()),
                "healthStatus", Arrays.stream(Health.values()).map(Health::getDisplayName).collect(Collectors.toList())
        );
    }



}
