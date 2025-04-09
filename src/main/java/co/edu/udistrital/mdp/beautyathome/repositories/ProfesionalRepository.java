package co.edu.udistrital.mdp.beautyathome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.udistrital.mdp.beautyathome.entities.ProfesionalEntity;
import java.util.Optional;


@Repository

public interface ProfesionalRepository extends JpaRepository<ProfesionalEntity,Long>{

    Optional<ProfesionalEntity> findById(Long id);

}
