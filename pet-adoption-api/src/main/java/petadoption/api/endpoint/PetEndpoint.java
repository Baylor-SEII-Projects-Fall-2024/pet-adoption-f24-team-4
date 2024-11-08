package petadoption.api.endpoint;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import petadoption.api.adoptionCenter.AdoptionCenter;
import petadoption.api.adoptionCenter.AdoptionCenterService;
import petadoption.api.pet.Pet;
import petadoption.api.pet.PetRequest;
import petadoption.api.pet.PetService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
            Optional<AdoptionCenter> adoptionCenter = adoptionCenterService.getCenter(petRequest.getAdoptionID());

            if (adoptionCenter.isEmpty()) {
                return ResponseEntity.badRequest().body("Adoption center not found");
            }

            Pet pet = new Pet();
            pet.setName(petRequest.getFirstName());
            //pet.setPetType(petRequest.getPetType());
            pet.setWeight(petRequest.getWeight());
            //pet.setFurType(petRequest.getFurType());
            //pet.setBreed(petRequest.getBreed());
            pet.setAge(petRequest.getAge()); // Set the age
            pet.setTemperament(petRequest.getTemperament()); // Set the temperament enum
            pet.setPetSize(petRequest.getPetSize()); // Set the size enum
            //pet.setHealthStatus(petRequest.getHealthStatus()); // Set health status
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

            existingPet.setName(petRequest.getFirstName());
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




}
