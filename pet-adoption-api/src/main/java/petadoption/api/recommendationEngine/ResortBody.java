package petadoption.api.recommendationEngine;

import petadoption.api.pet.Pet;

import java.util.List;

public class ResortBody {
    public Long userId;
    public Integer numPetsSeen;
    public List<Pet> sample;
}