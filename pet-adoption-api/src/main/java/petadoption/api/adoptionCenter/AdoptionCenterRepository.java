package petadoption.api.adoptionCenter;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdoptionCenterRepository extends JpaRepository<AdoptionCenter, Long> {
    Optional<AdoptionCenter> findByUserId(Long id);
    //Optional<AdoptionCenter> findByBuildingName(String buildingName);
}