package petadoption.api.recommendationEngine;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import petadoption.api.pet.Pet;
import petadoption.api.pet.PetRepository;

import java.util.List;

@Service
public class RecEngService {
    @Autowired
    private PetRepository petRepository;

    private RecommendationEngine recommendationEngine;
}
