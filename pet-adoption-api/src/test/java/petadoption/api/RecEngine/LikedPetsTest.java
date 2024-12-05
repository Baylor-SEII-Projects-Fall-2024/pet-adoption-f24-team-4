package petadoption.api.RecEngine;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import petadoption.api.likedPets.LikedPet;
import petadoption.api.likedPets.LikedPetRepository;
import petadoption.api.likedPets.LikedPetService;
import petadoption.api.pet.Pet;
import petadoption.api.pet.PetRepository;
import petadoption.api.user.User;
import petadoption.api.user.UserRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("testdb") // Use H2 in-memory database for testing
class LikedPetsTest {

    @Autowired
    private LikedPetService likedPetService;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private LikedPetRepository likedPetRepository;

    @Autowired
    private UserRepository userRepository;

    private User testUser;
    private Pet testPet;

    @BeforeEach
    void setUp() {
        likedPetRepository.deleteAll();
        petRepository.deleteAll();
        userRepository.deleteAll();

        // Create and save a test user
        testUser = new User();
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setEmailAddress("john.doe@example.com");
        userRepository.save(testUser);

        // Create and save a test pet
        testPet = new Pet();
        testPet.setName("Buddy");
        petRepository.save(testPet);
    }

    @Test
    void testSaveLikedPet() {
        LikedPet likedPet = new LikedPet(testUser, testPet);
        LikedPet savedLikedPet = likedPetService.saveLikedPet(likedPet);

        assertNotNull(savedLikedPet.getLikedPetId(), "LikedPet ID should be generated");
        assertEquals(testUser, savedLikedPet.getUser(), "User should match");
        assertEquals(testPet, savedLikedPet.getPet(), "Pet should match");
    }

    @Test
    void testExistsByUserAndPet() {
        LikedPet likedPet = new LikedPet(testUser, testPet);
        likedPetService.saveLikedPet(likedPet);

        boolean exists = likedPetService.existsByUserAndPet(testUser, testPet);
        assertTrue(exists, "LikedPet should exist for given user and pet");
    }

    @Test
    void testGetLikedPets() {
        LikedPet likedPet = new LikedPet(testUser, testPet);
        likedPetService.saveLikedPet(likedPet);

        List<Pet> likedPets = likedPetService.getLikedPets(testUser.getId());
        assertNotNull(likedPets, "LikedPets list should not be null");
        assertEquals(1, likedPets.size(), "LikedPets list should have one entry");
        assertEquals(testPet.getName(), likedPets.get(0).getName(), "Liked pet should match the saved pet");
    }
}