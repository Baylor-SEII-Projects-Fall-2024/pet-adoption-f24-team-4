package petadoption.api.pet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import petadoption.api.adoptionCenter.AdoptionCenter;
import petadoption.api.adoptionCenter.AdoptionCenterRepository;
import petadoption.api.adoptionCenter.AdoptionCenterService;
import petadoption.api.pet.criteria.*;
import petadoption.api.pet.criteria.breed.CatBreed;
import petadoption.api.pet.criteria.breed.DogBreed;
import petadoption.api.Utility.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

@Service
public class PetService {
    @Autowired
    private PetRepository repository;

    @Autowired
    private AdoptionCenterRepository adoptionCenterRepository;

    private static final long DEFAULT_RAND_PETS = 500;

    public Optional<Pet> savePet(Long petID){
        return repository.findById(petID);
    }

    public Pet savePet(Pet pet){
        return repository.save(pet);
    }

    public Pet savePet(Pet pet, Long adoptionID) {
        if(adoptionID != null) {
            Optional<AdoptionCenter> center = adoptionCenterRepository.findById(adoptionID);
            if (center.isPresent()) {
                pet.setCenter(center.get());
            } else {
                throw new RuntimeException("Adoption Center not found.");
            }
        }

        return repository.save(pet);
    }

    public Optional<Pet> getPetById(long petId){
        return repository.findById(petId);
    }

    public List<Pet> getAllPets() {
        return repository.findAll();
    }

    public List<Pet> getRandPets(long numPets) { return repository.getRandom(numPets); }

    public List<Pet> getRandPets() { return repository.getRandom(DEFAULT_RAND_PETS); }

    public List<Pet> getAdoptionCenterPets(long adoptionID) {
        return repository.findByCenter_AdoptionID(adoptionID);
    }

    public void deletePet(long petID){
        repository.deleteById(petID);
    }

    /*
    public Pet generatePet(String name, Species species, int weight,
                           CoatLength coatLength, FurType furType, Set<FurColor> furColors,
                           AdoptionCenter adoptionCenter,Set<DogBreed> dogBreeds,
                           Set<CatBreed> catBreeds, Size size,int age,
                           Set<Temperament> temperaments, Health health,SpayedNeutered spayedNeutered,
                           Sex sex) throws IOException {

        Pet pet = new Pet(name,species,weight,coatLength,furType,furColors,adoptionCenter,dogBreeds,catBreeds,size,age,temperaments,health,spayedNeutered,sex);
        return pet;
    }

    public void addSamplePets(AdoptionCenterService adoptionCenterService) throws IOException {

        List<Pet> samplePets = new ArrayList<>();

        //DOGS
        Optional<AdoptionCenter> adoptionCenter = adoptionCenterService.getCenter((long)(1));

        Set<DogBreed> dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.GREYHOUND);
        Set<CatBreed> catBreeds = new HashSet<>();
        Set<Temperament> temperaments = new HashSet<>();
        temperaments.add(Temperament.ACTIVE);
        temperaments.add(Temperament.REACTIVE);
        temperaments.add(Temperament.ATHLETIC);
        Set<FurColor> colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.BRONZE);
        colors.add(FurColor.WHITE);


        File imageFile = new File("PetImages/GreyHound.jpg");
        Image image = new Image();
        image.setType("image/jpeg");
        image.setName("GreyHound.jpg");

        image.setImageData(Files.readAllBytes(imageFile.toPath()));


        Pet pet = generatePet("wilson",Species.DOG,50,
                                    CoatLength.MEDIUM, FurType.SMOOTH,
                                    colors, adoptionCenter.get(),
                                    dogBreeds, catBreeds, Size.LARGE,4,
                                    temperaments, Health.GOOD,SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(2));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.GERMAN_SHEPHERD_DOG);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.ACTIVE);
        temperaments.add(Temperament.SOCIABLE);
        temperaments.add(Temperament.FRIENDLY);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.BRONZE);

        imageFile = new File("PetImages/german-shepherd-dog.jpeg");
        image = new Image();
        image.setType("image/jpeg");
        image.setName("german-shepherd-dog.jpg");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Axel",Species.DOG,52,CoatLength.MEDIUM,
                FurType.SMOOTH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.LARGE,4,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(3));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.CESKY_TERRIER);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.AGGRESSIVE);
        temperaments.add(Temperament.BOSSY);
        temperaments.add(Temperament.ANXIOUS);
        colors = new HashSet<>();
        colors.add(FurColor.GREY);
        colors.add(FurColor.WHITE);

        imageFile = new File("PetImages/CESKYTERRIER.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("CESKYTERRIER.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("jake",Species.DOG,20,CoatLength.MEDIUM,
                FurType.DOUBLE, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,4,temperaments,
                Health.GOOD, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(4));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.GIANT_SCHNAUZER);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.ADAPTABLE);
        temperaments.add(Temperament.FRIENDLY);
        temperaments.add(Temperament.CALM);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);

        imageFile = new File("PetImages/GIANTSCHNAUZER.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("GIANTSCHNAUZER.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("jake",Species.DOG,80,CoatLength.LONG,
                FurType.SMOOTH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.EXTRA_LARGE,7,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(5));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.DOBERMAN_PINSCHER);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.AGGRESSIVE);
        temperaments.add(Temperament.LOYAL);
        temperaments.add(Temperament.REACTIVE);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.BROWN);

        imageFile = new File("PetImages/Doberman.jpeg");
        image = new Image();
        image.setType("image/jpeg");
        image.setName("Doberman.jpeg");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Jackson",Species.DOG,45,CoatLength.SHORT,
                FurType.SILKY, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.LARGE,6,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);



        adoptionCenter = adoptionCenterService.getCenter((long)(6));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.POODLE);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.PASSIVE);
        temperaments.add(Temperament.EASY_GOING);
        temperaments.add(Temperament.EXCITABLE);
        colors = new HashSet<>();
        colors.add(FurColor.BROWN);

        imageFile = new File("PetImages/poodle.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("poodle.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("rolex",Species.DOG,20,CoatLength.MEDIUM,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,4,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);




        adoptionCenter = adoptionCenterService.getCenter((long)(7));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.BOXER);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.AGGRESSIVE);
        temperaments.add(Temperament.REACTIVE);
        temperaments.add(Temperament.CURIOUS);
        colors = new HashSet<>();
        colors.add(FurColor.WHITE);
        colors.add(FurColor.BROWN);

        imageFile = new File("PetImages/Boxer.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("Boxer.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Waller",Species.DOG,55,CoatLength.SHORT,
                FurType.SMOOTH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.LARGE,7,temperaments,
                Health.GOOD, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);




        adoptionCenter = adoptionCenterService.getCenter((long)(8));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.CATAHOULA_LEOPARD_DOG);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.AGILE);
        temperaments.add(Temperament.CALM);
        temperaments.add(Temperament.CURIOUS);
        colors = new HashSet<>();
        colors.add(FurColor.WHITE);
        colors.add(FurColor.GREY);

        imageFile = new File("PetImages/CATAHOULALEOPARDDOG.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("CATAHOULALEOPARDDOG.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Bart",Species.DOG,20,CoatLength.SHORT,
                FurType.SILKY, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.LARGE,2,temperaments,
                Health.GOOD, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(9));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.GOLDEN_RETRIEVER);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.ACTIVE);
        temperaments.add(Temperament.PLAYFUL);
        temperaments.add(Temperament.LOYAL);
        colors = new HashSet<>();
        colors.add(FurColor.BRONZE);


        imageFile = new File("PetImages/GoldenRetreiver.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("GoldenRetreiver.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Monke",Species.DOG,70,CoatLength.LONG,
                FurType.DOUBLE,colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.LARGE,5,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(10));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.TOSA);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.AGGRESSIVE);
        temperaments.add(Temperament.ACTIVE);
        temperaments.add(Temperament.DEPENDENT);
        colors = new HashSet<>();
        colors.add(FurColor.BRONZE);
        colors.add(FurColor.WHITE);

        imageFile = new File("PetImages/tosa.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("tosa.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Cola",Species.DOG,50,CoatLength.MEDIUM,
                FurType.SMOOTH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.LARGE,6,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        //CATS
        adoptionCenter = adoptionCenterService.getCenter((long)(1));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.SIAMESE);

        temperaments = new HashSet<>();
        temperaments.add(Temperament.ACTIVE);
        temperaments.add(Temperament.SOCIABLE);
        temperaments.add(Temperament.EVEN_TEMPERED);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.WHITE);


        imageFile = new File("PetImages/Siamese.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("Siamese.webp");

        image.setImageData(Files.readAllBytes(imageFile.toPath()));


        pet = generatePet("WilburForce",Species.CAT,25,CoatLength.SHORT,
                FurType.SILKY, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,8,temperaments,
                Health.GOOD, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(2));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.DOMESTIC_SHORTHAIR);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.ACTIVE);
        temperaments.add(Temperament.EASY_GOING);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.BROWN);

        imageFile = new File("PetImages/cat1.jpg");
        image = new Image();
        image.setType("image/jpeg");
        image.setName("cat1.jpg");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Jerome",Species.CAT,52,CoatLength.SHORT,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,4,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(3));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.DOMESTIC_SHORTHAIR);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.AGGRESSIVE);
        temperaments.add(Temperament.ANXIOUS);
        temperaments.add(Temperament.EASILY_TRAINED);
        colors = new HashSet<>();
        colors.add(FurColor.GREY);


        imageFile = new File("PetImages/cat2.jpg");
        image = new Image();
        image.setType("image/jpg");
        image.setName("cat2.jpg");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("jake",Species.CAT,18,CoatLength.SHORT,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,6,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(4));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.DOMESTIC_SHORTHAIR);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.AFFECTIONATE);
        temperaments.add(Temperament.CALM);
        temperaments.add(Temperament.LOYAL);
        temperaments.add(Temperament.CURIOUS);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.WHITE);

        imageFile = new File("PetImages/cat3.jpeg");
        image = new Image();
        image.setType("image/jpeg");
        image.setName("cat3.jpeg");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("jake",Species.CAT,23,CoatLength.SHORT,
                FurType.SMOOTH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,7,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(5));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.AMERICAN_BOBTAIL);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.AGGRESSIVE);
        temperaments.add(Temperament.BOSSY);
        temperaments.add(Temperament.BOLD);
        colors = new HashSet<>();
        colors.add(FurColor.BROWN);


        imageFile = new File("PetImages/Bobtail.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("Bobtail.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("stewie",Species.CAT,34,CoatLength.SHORT,
                FurType.SMOOTH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,4,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);



        adoptionCenter = adoptionCenterService.getCenter((long)(6));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.AMERICAN_CURL);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.PASSIVE);
        temperaments.add(Temperament.CALM);
        temperaments.add(Temperament.INTELLIGENT);
        colors = new HashSet<>();
        colors.add(FurColor.WHITE);


        imageFile = new File("PetImages/curl.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("curl.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("SETH",Species.CAT,27,CoatLength.MEDIUM,
                FurType.SMOOTH,colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,4,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);




        adoptionCenter = adoptionCenterService.getCenter((long)(7));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.DEVON_REX);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.AGGRESSIVE);
        temperaments.add(Temperament.ADAPTABLE);
        temperaments.add(Temperament.ALOOF);
        colors = new HashSet<>();
        colors.add(FurColor.WHITE);
        colors.add(FurColor.GREY);

        imageFile = new File("PetImages/REX.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("REX.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Wizard",Species.CAT,15,CoatLength.SHORT,
                FurType.SMOOTH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,3,temperaments,
                Health.FAIR, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);




        adoptionCenter = adoptionCenterService.getCenter((long)(8));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.MAINE_COON);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.AGILE);
        temperaments.add(Temperament.EASILY_TRAINED);
        temperaments.add(Temperament.BOLD);
        temperaments.add(Temperament.INTELLIGENT);
        colors = new HashSet<>();
        colors.add(FurColor.BRONZE);


        imageFile = new File("PetImages/maine.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("maine.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("UltimateDestroyerOfWorlds",Species.CAT,35,CoatLength.LONG,
                FurType.DOUBLE, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.LARGE,2,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(9));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.SIAMESE);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.POSSESSIVE);
        temperaments.add(Temperament.INTELLIGENT);
        temperaments.add(Temperament.INTROVERTED);
        colors = new HashSet<>();
        colors.add(FurColor.BRONZE);


        imageFile = new File("PetImages/persian.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("persian.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("John",Species.CAT,30,CoatLength.MEDIUM,
                FurType.DOUBLE, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,4,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(10));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.TURKISH_ANGORA);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.ALOOF);
        temperaments.add(Temperament.DEPENDENT);
        temperaments.add(Temperament.LOYAL);
        temperaments.add(Temperament.CURIOUS);
        colors = new HashSet<>();
        colors.add(FurColor.WHITE);


        imageFile = new File("PetImages/Angora.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("Angora.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Servine",Species.CAT,50,CoatLength.SHORT,
                FurType.SILKY, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,3,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);




        //DOGS
        adoptionCenter = adoptionCenterService.getCenter((long)(1));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.AFGHAN_HOUND);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.PASSIVE);
        temperaments.add(Temperament.INTELLIGENT);
        temperaments.add(Temperament.CHILL);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.BROWN);
        colors.add(FurColor.GREY);


        imageFile = new File("PetImages/afghan.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("afghan.webp");

        image.setImageData(Files.readAllBytes(imageFile.toPath()));


        pet = generatePet("Robert",Species.DOG,80,CoatLength.LONG,
                FurType.SILKY, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.EXTRA_LARGE,4,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);

        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(2));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.ALASKAN_MALAMUTE);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.AFFECTIONATE);
        temperaments.add(Temperament.FRIENDLY);
        temperaments.add(Temperament.ENERGETIC);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.WHITE);

        imageFile = new File("PetImages/alaskan.jpg");
        image = new Image();
        image.setType("image/jpg");
        image.setName("alaskan.jpg");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Vic",Species.DOG,52,CoatLength.MEDIUM,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.LARGE,9,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(3));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.AKITA);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.IMPULSIVE);
        temperaments.add(Temperament.EASILY_TRAINED);
        temperaments.add(Temperament.CURIOUS);
        temperaments.add(Temperament.ANXIOUS);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.BROWN);
        colors.add(FurColor.WHITE);

        imageFile = new File("PetImages/akita.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("akita.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("RandyOrton",Species.DOG,24,CoatLength.SHORT,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,4,temperaments,
                Health.GOOD, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(4));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.AMERICAN_FOXHOUND);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.ADAPTABLE);
        temperaments.add(Temperament.EVEN_TEMPERED);
        temperaments.add(Temperament.REACTIVE);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.BROWN);
        colors.add(FurColor.WHITE);

        imageFile = new File("PetImages/foxhound.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("foxhound.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("MrStealYoGirl",Species.DOG,60,CoatLength.SHORT,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.LARGE,7,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(5));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.AMERICAN_HAIRLESS_TERRIER);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.ALOOF);
        temperaments.add(Temperament.CALM);
        temperaments.add(Temperament.SUBMISSIVE);
        colors = new HashSet<>();


        imageFile = new File("PetImages/hairless.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("hairless.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Quagmire",Species.DOG,22,CoatLength.HAIRLESS,
                FurType.HAIRLESS, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,3,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);



        adoptionCenter = adoptionCenterService.getCenter((long)(6));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.AMERICAN_BULLDOG);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.REACTIVE);
        temperaments.add(Temperament.FRIENDLY);
        temperaments.add(Temperament.AFFECTIONATE);
        colors = new HashSet<>();
        colors.add(FurColor.WHITE);

        imageFile = new File("PetImages/AmericanBulldog.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("AmericanBulldog.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("George",Species.DOG,49,CoatLength.SHORT,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.LARGE,4,temperaments,
                Health.GOOD, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);




        adoptionCenter = adoptionCenterService.getCenter((long)(7));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.AMERICAN_WATER_SPANIEL);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.CALM);
        temperaments.add(Temperament.CURIOUS);
        temperaments.add(Temperament.ANXIOUS);
        colors = new HashSet<>();
        colors.add(FurColor.BROWN);

        imageFile = new File("PetImages/water.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("water.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Lex",Species.DOG,65,CoatLength.LONG,
                FurType.DOUBLE, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.LARGE,6,temperaments,
                Health.GOOD, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);




        adoptionCenter = adoptionCenterService.getCenter((long)(8));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.AUSTRALIAN_KELPIE);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.AGILE);
        temperaments.add(Temperament.FRIENDLY);
        temperaments.add(Temperament.INTELLIGENT);
        colors = new HashSet<>();
        colors.add(FurColor.BROWN);

        imageFile = new File("PetImages/Kelpie.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("Kelpie.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Tyler",Species.DOG,70,CoatLength.MEDIUM,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.LARGE,7,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(9));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.AUSTRALIAN_CATTLE_DOG);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.POSSESSIVE);
        temperaments.add(Temperament.REACTIVE);
        temperaments.add(Temperament.DEMANDING);
        colors = new HashSet<>();
        colors.add(FurColor.GREY);
        colors.add(FurColor.BROWN);

        imageFile = new File("PetImages/Cattle.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("Cattle.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Terrence",Species.DOG,68,CoatLength.MEDIUM,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.LARGE,9,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(10));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.BASSET_HOUND);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.PASSIVE);
        temperaments.add(Temperament.EVEN_TEMPERED);
        temperaments.add(Temperament.EASILY_TRAINED);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.BROWN);

        imageFile = new File("PetImages/basset.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("basset.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Darrel",Species.DOG,29,CoatLength.SHORT,
                FurType.SMOOTH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,6,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        //CATS
        adoptionCenter = adoptionCenterService.getCenter((long)(1));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.NORWEGIAN_FOREST_CAT);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.REACTIVE);
        temperaments.add(Temperament.BOLD);
        temperaments.add(Temperament.PASSIVE);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.WHITE);


        imageFile = new File("PetImages/forest.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("forest.webp");

        image.setImageData(Files.readAllBytes(imageFile.toPath()));


        pet = generatePet("Lux",Species.CAT,35,CoatLength.MEDIUM,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,4,temperaments,
                Health.GOOD, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(2));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.SCOTTISH_FOLD);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.ANXIOUS);
        temperaments.add(Temperament.BOLD);
        temperaments.add(Temperament.DEMANDING);
        temperaments.add(Temperament.REACTIVE);
        colors = new HashSet<>();
        colors.add(FurColor.BRONZE);

        imageFile = new File("PetImages/fold.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("fold.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Lucifer",Species.CAT,20,CoatLength.SHORT,
                FurType.SMOOTH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,5,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(3));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.SPHYNX);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.ANXIOUS);
        temperaments.add(Temperament.DEMANDING);
        temperaments.add(Temperament.BOLD);
        colors = new HashSet<>();


        imageFile = new File("PetImages/sphynx.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("sphynx.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Dory",Species.CAT,16,CoatLength.HAIRLESS,
                FurType.HAIRLESS, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,6,temperaments,
                Health.GOOD, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(4));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.SINGAPURA);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.AFFECTIONATE);
        temperaments.add(Temperament.CALM);
        temperaments.add(Temperament.INTROVERTED);
        colors = new HashSet<>();
        colors.add(FurColor.WHITE);
        colors.add(FurColor.BROWN);

        imageFile = new File("PetImages/siberia.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("siberia.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Serb",Species.CAT,29,CoatLength.MEDIUM,
                FurType.SMOOTH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,7,temperaments,
                Health.GOOD, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(5));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.RUSSIAN_BLUE);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.ATHLETIC);
        temperaments.add(Temperament.CURIOUS);
        temperaments.add(Temperament.DEPENDENT);
        colors = new HashSet<>();
        colors.add(FurColor.BLUE);

        imageFile = new File("PetImages/BLue.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("BLue.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("blue",Species.CAT,18,CoatLength.SHORT,
                FurType.SMOOTH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,4,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);



        adoptionCenter = adoptionCenterService.getCenter((long)(6));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.BRITISH_SHORTHAIR);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.BOLD);
        temperaments.add(Temperament.DEMANDING);
        temperaments.add(Temperament.EVEN_TEMPERED);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.WHITE);

        imageFile = new File("PetImages/british.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("british.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("THAM",Species.CAT,27,CoatLength.MEDIUM,
                FurType.SMOOTH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,4,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);




        adoptionCenter = adoptionCenterService.getCenter((long)(7));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.BOMBAY);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.AGGRESSIVE);
        temperaments.add(Temperament.SOCIABLE);
        temperaments.add(Temperament.EASY_GOING);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);


        imageFile = new File("PetImages/bombay.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("bombay.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("thebomb",Species.CAT,24,CoatLength.SHORT,
                FurType.SMOOTH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,3,temperaments,
                Health.FAIR, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);




        adoptionCenter = adoptionCenterService.getCenter((long)(8));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.BENGAL);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.PASSIVE);
        temperaments.add(Temperament.SOCIABLE);
        temperaments.add(Temperament.POSSESSIVE);
        colors = new HashSet<>();
        colors.add(FurColor.BRONZE);
        colors.add(FurColor.BROWN);

        imageFile = new File("PetImages/bengal.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("bengal.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Alex",Species.CAT,23,CoatLength.SHORT,
                FurType.WIRY, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,2,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(9));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.CHAUSIE);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.POSSESSIVE);
        temperaments.add(Temperament.BOLD);
        temperaments.add(Temperament.CALM);
        colors = new HashSet<>();
        colors.add(FurColor.BRONZE);

        imageFile = new File("PetImages/Chausie.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("Chausie.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Antonio",Species.CAT,45,CoatLength.MEDIUM,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.LARGE,4,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(10));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.EGYPTIAN_MAU);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.SUBMISSIVE);
        temperaments.add(Temperament.ANXIOUS);
        temperaments.add(Temperament.DEPENDENT);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.WHITE);

        imageFile = new File("PetImages/egypt.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("egypt.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("SkylerWhiteYo",Species.CAT,26,CoatLength.SHORT,
                FurType.SILKY, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,3,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        //DOGS
        adoptionCenter = adoptionCenterService.getCenter((long)(1));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.BEARDED_COLLIE);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.ACTIVE);
        temperaments.add(Temperament.PLAYFUL);
        temperaments.add(Temperament.FRIENDLY);
        colors = new HashSet<>();
        colors.add(FurColor.GRAY);
        colors.add(FurColor.WHITE);

        imageFile = new File("PetImages/Collie.jpg");
        image = new Image();
        image.setType("image/jpg");
        image.setName("Collie.jpg");

        image.setImageData(Files.readAllBytes(imageFile.toPath()));


        pet = generatePet("Dexter",Species.DOG,50,CoatLength.LONG,
                                FurType.SILKY, colors, adoptionCenter.get(),
                                dogBreeds, catBreeds, Size.MEDIUM,6,temperaments,
                                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(2));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.BASENJI);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.REACTIVE);
        temperaments.add(Temperament.ATHLETIC);
        temperaments.add(Temperament.BOLD);
        colors = new HashSet<>();
        colors.add(FurColor.BRONZE);
        colors.add(FurColor.WHITE);

        imageFile = new File("PetImages/basenji.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("basenji.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Steph",Species.DOG,62,CoatLength.SHORT,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.LARGE,4,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(3));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.AUSTRALIAN_SHEPHERD);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.FRIENDLY);
        temperaments.add(Temperament.PLAYFUL);
        temperaments.add(Temperament.DEPENDENT);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.BROWN);
        colors.add(FurColor.GRAY);
        colors.add(FurColor.WHITE);

        imageFile = new File("PetImages/aush.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("aush.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Po",Species.DOG,65,CoatLength.LONG,
                FurType.DOUBLE, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.LARGE,5,temperaments,
                Health.FAIR, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(4));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.BEDLINGTON_TERRIER);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.REACTIVE);
        temperaments.add(Temperament.FRIENDLY);
        temperaments.add(Temperament.CALM);
        colors = new HashSet<>();
        colors.add(FurColor.GRAY);

        imageFile = new File("PetImages/bedlington.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("bedlington.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("ellington",Species.DOG,72,CoatLength.SHORT,
                FurType.WAVY, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.LARGE,7,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(5));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.BELGIAN_TERVUREN);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.CALM);
        temperaments.add(Temperament.PLAYFUL);
        temperaments.add(Temperament.EVEN_TEMPERED);
        colors = new HashSet<>();
        colors.add(FurColor.DARK);
        colors.add(FurColor.BROWN);

        imageFile = new File("PetImages/Beluga.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("Beluga.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("beluga",Species.DOG,85,CoatLength.LONG,
                FurType.SMOOTH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.EXTRA_LARGE,7,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);



        adoptionCenter = adoptionCenterService.getCenter((long)(6));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.BICHON_FRISE);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.PASSIVE);
        temperaments.add(Temperament.INTELLIGENT);
        temperaments.add(Temperament.INDEPENDENT);
        colors = new HashSet<>();
        colors.add(FurColor.WHITE);


        imageFile = new File("PetImages/bichon.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("bichon.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Tea",Species.DOG,23,CoatLength.MEDIUM,
                FurType.WAVY, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,4,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);




        adoptionCenter = adoptionCenterService.getCenter((long)(7));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.BLOODHOUND);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.AGGRESSIVE);
        temperaments.add(Temperament.REACTIVE);
        temperaments.add(Temperament.PLAYFUL);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.BROWN);

        imageFile = new File("PetImages/bloodhound.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("bloodhound.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("AndrewTate",Species.DOG,90,CoatLength.MEDIUM,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.EXTRA_LARGE,7,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);




        adoptionCenter = adoptionCenterService.getCenter((long)(8));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.BLUETICK_COONHOUND);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.ATHLETIC);
        temperaments.add(Temperament.REACTIVE);
        temperaments.add(Temperament.DEMANDING);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.GREY);

        imageFile = new File("PetImages/bluetick.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("bluetick.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Harry",Species.DOG,49,CoatLength.SHORT,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.LARGE,2,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(9));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.BERNESE_MOUNTAIN_DOG);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.FRIENDLY);
        temperaments.add(Temperament.REACTIVE);
        temperaments.add(Temperament.KID_FRIENDLY);
        temperaments.add(Temperament.INTELLIGENT);
        temperaments.add(Temperament.ENERGETIC);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.BRONZE);


        imageFile = new File("PetImages/Mountain.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("Mountain.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("SLimShady",Species.DOG,91,CoatLength.MEDIUM,
                FurType.DOUBLE, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.EXTRA_LARGE,5,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(10));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.BEAUCERON);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.REACTIVE);
        temperaments.add(Temperament.AGGRESSIVE);
        temperaments.add(Temperament.PLAYFUL);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.BRONZE);

        imageFile = new File("PetImages/beauceron.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("beauceron.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Usman",Species.DOG,70,CoatLength.SHORT,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.LARGE,6,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        //CATS
        adoptionCenter = adoptionCenterService.getCenter((long)(1));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.KORAT);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.ANXIOUS);
        temperaments.add(Temperament.CURIOUS);
        temperaments.add(Temperament.PLACID);
        colors = new HashSet<>();
        colors.add(FurColor.GREY);



        imageFile = new File("PetImages/Korat.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("Korat.webp");

        image.setImageData(Files.readAllBytes(imageFile.toPath()));


        pet = generatePet("kora",Species.CAT,25,CoatLength.SHORT,
                FurType.SMOOTH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,8,temperaments,
                Health.GOOD, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(2));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.HIMALAYAN);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.ALOOF);
        temperaments.add(Temperament.DEPENDENT);
        temperaments.add(Temperament.CHILL);
        colors = new HashSet<>();
        colors.add(FurColor.WHITE);
        colors.add(FurColor.BRONZE);

        imageFile = new File("PetImages/him.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("him.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Bot",Species.CAT,35,CoatLength.MEDIUM,
                FurType.DOUBLE, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,4,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(3));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.HAVANA_BROWN);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.ALOOF);
        temperaments.add(Temperament.DEPENDENT);
        temperaments.add(Temperament.TIMID);
        colors = new HashSet<>();
        colors.add(FurColor.BROWN);


        imageFile = new File("PetImages/havana.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("havana.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Wormwood",Species.CAT,23,CoatLength.SHORT,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,6,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(4));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.DOMESTIC_LONGHAIR);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.CALM);
        temperaments.add(Temperament.AGGRESSIVE);
        temperaments.add(Temperament.EXCITABLE);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.BRONZE);

        imageFile = new File("PetImages/DomesticLongHairCat.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("DomesticLongHairCat.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Dominic",Species.CAT,29,CoatLength.LONG,
                FurType.SMOOTH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,7,temperaments,
                Health.GOOD, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(5));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.MANX);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.PLAYFUL);
        temperaments.add(Temperament.POSSESSIVE);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.BRONZE);

        imageFile = new File("PetImages/manx.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("manx.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Michael",Species.CAT,30,CoatLength.MEDIUM,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,4,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);



        adoptionCenter = adoptionCenterService.getCenter((long)(6));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.OCICAT);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.REACTIVE);
        temperaments.add(Temperament.INTELLIGENT);
        temperaments.add(Temperament.KID_FRIENDLY);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.BRONZE);

        imageFile = new File("PetImages/ocicat.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("ocicat.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Max",Species.CAT,24,CoatLength.SHORT,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,3,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);




        adoptionCenter = adoptionCenterService.getCenter((long)(7));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.PIXIE_BOB);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.CURIOUS);
        temperaments.add(Temperament.IMPULSIVE);
        temperaments.add(Temperament.FOCUSED);
        colors = new HashSet<>();
        colors.add(FurColor.BRONZE);

        imageFile = new File("PetImages/pixiebob.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("pixiebob.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Gandalf",Species.CAT,27,CoatLength.SHORT,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,3,temperaments,
                Health.FAIR, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);




        adoptionCenter = adoptionCenterService.getCenter((long)(8));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.RAGAMUFFIN);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.AGGRESSIVE);
        temperaments.add(Temperament.REACTIVE);
        temperaments.add(Temperament.DEMANDING);
        colors = new HashSet<>();
        colors.add(FurColor.GREY);
        colors.add(FurColor.BLACK);

        imageFile = new File("PetImages/ragamuffin.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("ragamuffin.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("girl",Species.CAT,25,CoatLength.LONG,
                FurType.DOUBLE, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,6,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(9));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.SNOWSHOE);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.BOLD);
        temperaments.add(Temperament.FOCUSED);
        temperaments.add(Temperament.VOCAL);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.WHITE);
        colors.add(FurColor.GREY);

        imageFile = new File("PetImages/snowshoe.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("snowshoe.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Snape",Species.CAT,23,CoatLength.SHORT,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,4,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(10));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.SINGAPURA);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.DEPENDENT);
        temperaments.add(Temperament.REACTIVE);
        temperaments.add(Temperament.EASY_GOING);
        temperaments.add(Temperament.INTROVERTED);
        colors = new HashSet<>();
        colors.add(FurColor.WHITE);

        imageFile = new File("PetImages/singapura.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("singapura.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("kittycat",Species.CAT,12,CoatLength.SHORT,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,3,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);




        //DOGS
        adoptionCenter = adoptionCenterService.getCenter((long)(1));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.BOERBOEL);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.CALM);
        temperaments.add(Temperament.SOCIABLE);
        temperaments.add(Temperament.BOLD);
        colors = new HashSet<>();
        colors.add(FurColor.BRONZE);


        imageFile = new File("PetImages/Boerboel.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("Boerboel.webp");

        image.setImageData(Files.readAllBytes(imageFile.toPath()));


        pet = generatePet("GeorgeFloyd",Species.DOG,80,CoatLength.SHORT,
                FurType.SILKY, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.EXTRA_LARGE,8,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);

        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(2));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.BORZOI);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.AFFECTIONATE);
        temperaments.add(Temperament.KID_FRIENDLY);
        temperaments.add(Temperament.POSSESSIVE);
        temperaments.add(Temperament.EXCITABLE);
        colors = new HashSet<>();
        colors.add(FurColor.BRONZE);
        colors.add(FurColor.WHITE);

        imageFile = new File("PetImages/borzoi.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("borzoi.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("TRAVIS",Species.DOG,102,CoatLength.MEDIUM,
                FurType.DOUBLE, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.EXTRA_LARGE,6,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(3));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.BOYKIN_SPANIEL);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.EVEN_TEMPERED);
        temperaments.add(Temperament.FOCUSED);
        temperaments.add(Temperament.INSISTENT);
        colors = new HashSet<>();
        colors.add(FurColor.BROWN);

        imageFile = new File("PetImages/boykin-spaniel.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("boykin-spaniel.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("BIGBOY",Species.DOG,34,CoatLength.MEDIUM,
                FurType.SILKY, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,4,temperaments,
                Health.GOOD, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(4));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.BRIARD);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.PLAYFUL);
        temperaments.add(Temperament.KID_FRIENDLY);
        temperaments.add(Temperament.CHILL);
        colors = new HashSet<>();
        colors.add(FurColor.GREY);

        imageFile = new File("PetImages/briard.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("briard.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Griffin",Species.DOG,62,CoatLength.LONG,
                FurType.SILKY, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.LARGE,10,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(5));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.BRITTANY_SPANIEL);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.CURIOUS);
        temperaments.add(Temperament.LOYAL);
        temperaments.add(Temperament.BOLD);
        colors = new HashSet<>();
        colors.add(FurColor.BROWN);
        colors.add(FurColor.WHITE);

        imageFile = new File("PetImages/brittany.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("brittany.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Optimus",Species.DOG,56,CoatLength.MEDIUM,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,5,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);



        adoptionCenter = adoptionCenterService.getCenter((long)(6));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.CAROLINA_DOG);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.REACTIVE);
        temperaments.add(Temperament.ENERGETIC);
        temperaments.add(Temperament.EASILY_TRAINED);
        colors = new HashSet<>();
        colors.add(FurColor.BRONZE);
        colors.add(FurColor.LIGHT);

        imageFile = new File("PetImages/Carolina.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("Carolina.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Austin",Species.DOG,64,CoatLength.SHORT,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.LARGE,4,temperaments,
                Health.GOOD, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);




        adoptionCenter = adoptionCenterService.getCenter((long)(7));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.CAUCASIAN_SHEPHERD_DOG);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.AFFECTIONATE);
        temperaments.add(Temperament.BOLD);
        temperaments.add(Temperament.FRIENDLY);
        temperaments.add(Temperament.REACTIVE);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.BRONZE);

        imageFile = new File("PetImages/Caucasian.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("Caucasian.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Whitaker",Species.DOG,75,CoatLength.MEDIUM,
                FurType.CURLY, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.LARGE,6,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);




        adoptionCenter = adoptionCenterService.getCenter((long)(8));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.CAVALIER_KING_CHARLES_SPANIEL);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.BOSSY);
        temperaments.add(Temperament.DEMANDING);
        temperaments.add(Temperament.LIVELY);
        colors = new HashSet<>();
        colors.add(FurColor.BROWN);
        colors.add(FurColor.WHITE);

        imageFile = new File("PetImages/cavalier.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("cavalier.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Diana",Species.DOG,30,CoatLength.LONG,
                FurType.SILKY, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,7,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(9));

        dogBreeds = new HashSet<>();
        dogBreeds.add(DogBreed.CHIHUAHUA);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.FRIENDLY);
        temperaments.add(Temperament.AGGRESSIVE);
        temperaments.add(Temperament.EASILY_TRAINED);
        temperaments.add(Temperament.LOYAL);
        colors = new HashSet<>();
        colors.add(FurColor.BROWN);
        colors.add(FurColor.WHITE);

        imageFile = new File("PetImages/chihuahua.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("chihuahua.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Sneako",Species.DOG,29,CoatLength.SHORT,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,9,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(10));

        dogBreeds = new HashSet<>();
            dogBreeds.add(DogBreed.CANAAN_DOG);
        catBreeds = new HashSet<>();
        temperaments = new HashSet<>();
        temperaments.add(Temperament.FRIENDLY);
        temperaments.add(Temperament.REACTIVE);
        temperaments.add(Temperament.KID_FRIENDLY);
        temperaments.add(Temperament.CUDDLY);
        colors = new HashSet<>();
        colors.add(FurColor.BRONZE);
        colors.add(FurColor.WHITE);

        imageFile = new File("PetImages/canaan.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("canaan.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("DAX",Species.DOG,49,CoatLength.MEDIUM,
                FurType.SMOOTH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,5,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        //CATS
        adoptionCenter = adoptionCenterService.getCenter((long)(1));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.SELKIRK_REX);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.BOLD);
        temperaments.add(Temperament.DEPENDENT);
        temperaments.add(Temperament.DOCILE);
        colors = new HashSet<>();
        colors.add(FurColor.GREY);
        colors.add(FurColor.WHITE);


        imageFile = new File("PetImages/Selkirk.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("Selkirk.webp");

        image.setImageData(Files.readAllBytes(imageFile.toPath()));


        pet = generatePet("Bob",Species.CAT,28,CoatLength.MEDIUM,
                FurType.WAVY, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,3,temperaments,
                Health.GOOD, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(2));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.TONKINESE);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.ANXIOUS);
        temperaments.add(Temperament.FRIENDLY);
        temperaments.add(Temperament.LOYAL);
        colors = new HashSet<>();
        colors.add(FurColor.BROWN);

        imageFile = new File("PetImages/tonkinese.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("tonkinese.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Olive",Species.CAT,26,CoatLength.MEDIUM,
                FurType.SMOOTH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,5,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(3));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.TURKISH_VAN);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.CALM);
        temperaments.add(Temperament.LOYAL);
        temperaments.add(Temperament.PLAYFUL);
        colors = new HashSet<>();
        colors.add(FurColor.BROWN);
        colors.add(FurColor.WHITE);

        imageFile = new File("PetImages/turkish.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("turkish.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Cindy",Species.CAT,26,CoatLength.MEDIUM,
                FurType.SMOOTH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,3,temperaments,
                Health.FAIR, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(4));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.SOMALI);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.ANXIOUS);
        temperaments.add(Temperament.VOCAL);
        temperaments.add(Temperament.IMPULSIVE);
        colors = new HashSet<>();
        colors.add(FurColor.BRONZE);

        imageFile = new File("PetImages/somali.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("somali.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Yamcha",Species.CAT,29,CoatLength.MEDIUM,
                FurType.SMOOTH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,7,temperaments,
                Health.GOOD, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);


        adoptionCenter = adoptionCenterService.getCenter((long)(5));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.TOYGER);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.DEPENDENT);
        temperaments.add(Temperament.TIMID);
        temperaments.add(Temperament.PLACID);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.BRONZE);

        imageFile = new File("PetImages/Toyger.jpg");
        image = new Image();
        image.setType("image/jpg");
        image.setName("Toyger.jpg");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Sam",Species.CAT,23,CoatLength.SHORT,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,4,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);



        adoptionCenter = adoptionCenterService.getCenter((long)(6));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.TUXEDO);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.SOCIABLE);
        temperaments.add(Temperament.INTELLIGENT);
        temperaments.add(Temperament.EVEN_TEMPERED);
        colors = new HashSet<>();
        colors.add(FurColor.BLACK);
        colors.add(FurColor.WHITE);

        imageFile = new File("PetImages/Tux.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("Tux.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Tuc",Species.CAT,27,CoatLength.MEDIUM,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,3,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);




        adoptionCenter = adoptionCenterService.getCenter((long)(7));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.LYKOI);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.AGGRESSIVE);
        temperaments.add(Temperament.DEPENDENT);
        temperaments.add(Temperament.REACTIVE);
        temperaments.add(Temperament.ACTIVE);
        colors = new HashSet<>();
        colors.add(FurColor.GREY);

        imageFile = new File("PetImages/lykoi.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("lykoi.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Ember",Species.CAT,23,CoatLength.MEDIUM,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,3,temperaments,
                Health.FAIR, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);




        adoptionCenter = adoptionCenterService.getCenter((long)(8));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.EUROPEAN_BURMERSE);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.CURIOUS);
        temperaments.add(Temperament.FRIENDLY);
        temperaments.add(Temperament.BOLD);
        colors = new HashSet<>();
        colors.add(FurColor.BRONZE);


        imageFile = new File("PetImages/Burmese.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("Burmese.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Oscar",Species.CAT,30,CoatLength.SHORT,
                FurType.WIRY, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.MEDIUM,9,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(9));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.NEBELUNG);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.CALM);
        temperaments.add(Temperament.BOLD);
        temperaments.add(Temperament.FOCUSED);
        temperaments.add(Temperament.AFFECTIONATE);
        colors = new HashSet<>();
        colors.add(FurColor.GREY);

        imageFile = new File("PetImages/NEBELUNG.webp");
        image = new Image();
        image.setType("image/webp");
        image.setName("NEBELUNG.webp");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Timothy",Species.CAT,15,CoatLength.LONG,
                FurType.SMOOTH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,6,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.MALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);





        adoptionCenter = adoptionCenterService.getCenter((long)(10));

        dogBreeds = new HashSet<>();
        catBreeds = new HashSet<>();
        catBreeds.add(CatBreed.ORIENTAL);
        temperaments = new HashSet<>();
        temperaments.add(Temperament.SUBMISSIVE);
        temperaments.add(Temperament.INTELLIGENT);
        temperaments.add(Temperament.INTROVERTED);
        temperaments.add(Temperament.DEMANDING);
        colors = new HashSet<>();
        colors.add(FurColor.BROWN);
        colors.add(FurColor.WHITE);

        imageFile = new File("PetImages/Oriental.jpg");
        image = new Image();
        image.setType("image/jpg");
        image.setName("Oriental.jpg");
        image.setImageData(Files.readAllBytes(imageFile.toPath()));

        pet = generatePet("Airy",Species.CAT,18,CoatLength.SHORT,
                FurType.ROUGH, colors, adoptionCenter.get(),
                dogBreeds, catBreeds, Size.SMALL,3,temperaments,
                Health.EXCELLENT, SpayedNeutered.SPAYED_NEUTERED,Sex.FEMALE);
        pet.setProfilePicture(image);
        samplePets.add(pet);

        repository.saveAll(samplePets);
    }
*/


}
