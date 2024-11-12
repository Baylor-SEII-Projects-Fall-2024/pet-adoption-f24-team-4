package petadoption.api.recommendationEngine;

import petadoption.api.pet.Pet;

import java.util.Comparator;
import java.util.Map;

/**
 * Comparator to be used by <code>PriorityQueue</code> of
 * <code>Pet</code>s to recommend This is necessary due to
 * a pet not storing its total rating so it is in a
 * key-value pair with it's associated pet.
 *
 * @author Rafe Loya
 * @see RecommendationEngine
 */
public class PetComparator implements Comparator<Map.Entry<Pet, Double>> {
    @Override
    public int compare(Map.Entry<Pet, Double> o1, Map.Entry<Pet, Double> o2) {
        return o1.getValue().compareTo(o2.getValue());
    }
}
