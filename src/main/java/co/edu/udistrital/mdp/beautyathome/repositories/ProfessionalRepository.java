package co.edu.udistrital.mdp.beautyathome.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.udistrital.mdp.beautyathome.entities.ProfessionalEntity;

@Repository
public interface ProfessionalRepository extends JpaRepository<ProfessionalEntity,Long>{

    Optional<ProfessionalEntity> findByName(String name);
    Optional<ProfessionalEntity> findById(Long id);
    Optional<ProfessionalEntity> findBySummary(String summary);


}
