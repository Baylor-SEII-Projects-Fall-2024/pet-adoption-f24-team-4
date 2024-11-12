package petadoption.api.pet;

import lombok.Data;
import petadoption.api.Utility.EnumUtil;
import petadoption.api.pet.criteria.*;
import petadoption.api.pet.criteria.breed.DogBreed;
import petadoption.api.pet.criteria.breed.CatBreed;

import java.util.Set;
import java.util.stream.Collectors;

@Data
public class PetRequest {

    private String name;
    private Long id;
    private Species species;
    private int weight;
    private CoatLength coatLength;
    private FurType furType;
    private Long adoptionId;
    private Set<DogBreed> dogBreed;
    private Set<CatBreed> catBreed;
    private Size petSize;
    private int age;
    private Set<Temperament> temperament;
    private Health healthStatus;
    private Set<FurColor> furColor;
    private SpayedNeutered spayedNeutered;
    private Sex sex;

    public PetRequest(){}

    public void setName(String name) {
        this.name = name;
    }

    public void setSpecies(String speciesStr) {
        this.species = EnumUtil.mapStringToSpecies(speciesStr);
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void setCoatLength(String coatLengthStr) {
        this.coatLength = EnumUtil.mapStringToCoatLength(coatLengthStr);
    }

    public void setFurType(String furTypeStr) {
        this.furType = EnumUtil.mapStringToFurType(furTypeStr);
    }

    public void setFurColor(Set<String> furColorStr) {
        this.furColor = furColorStr.stream()
                .map(EnumUtil::mapStringToFurColor)
                .collect(Collectors.toSet());
    }

    public void setAdoptionId(Long adoptionId) {
        this.adoptionId = adoptionId;
    }

    public void setDogBreed(Set<String> dogBreedsStr) {
        this.dogBreed = dogBreedsStr.stream()
                .map(EnumUtil::mapStringToDogBreed)
                .collect(Collectors.toSet());
    }

    public void setCatBreed(Set<String> catBreedsStr) {
        this.catBreed = catBreedsStr.stream()
                .map(EnumUtil::mapStringToCatBreed)
                .collect(Collectors.toSet());
    }

    public void setPetSize(String petSizeStr) {
        this.petSize = EnumUtil.mapStringToSize(petSizeStr);
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setTemperament(Set<String> temperamentStr) {
        this.temperament = temperamentStr.stream()
                .map(EnumUtil::mapStringToTemperament)
                .collect(Collectors.toSet());
    }

    public void setHealthStatus(String healthStatusStr) {
        this.healthStatus = EnumUtil.mapStringToHealth(healthStatusStr);
    }

    public void setSpayedNeutered(String spayedNeutered) {
        this.spayedNeutered = EnumUtil.mapStringToSpayedNeutered(spayedNeutered);
    }

    public void setSex(String sex) {
        this.sex = EnumUtil.mapStringToSex(sex);
    }
}
