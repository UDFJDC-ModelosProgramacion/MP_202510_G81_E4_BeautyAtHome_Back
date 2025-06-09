package co.edu.udistrital.mdp.beautyathome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.udistrital.mdp.beautyathome.entities.CoverageAreaEntity;

@Repository
public interface CoverageAreaRepository extends JpaRepository<CoverageAreaEntity, Long>{

}
