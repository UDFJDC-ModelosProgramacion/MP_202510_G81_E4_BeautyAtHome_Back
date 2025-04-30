package co.edu.udistrital.mdp.beautyathome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.sql.Date;

import co.edu.udistrital.mdp.beautyathome.entities.ServiceEntity;
import java.util.List;
import java.util.Optional;


@Repository

public interface ServiceRepository extends JpaRepository<ServiceEntity,Long>{
    
    Optional<ServiceEntity> findById(Long id);

    List<ServiceEntity> findByCompletionDate(Date completionDate);
    List<ServiceEntity> findByName(String name);
    List<ServiceEntity> findByPrice(Double price);

}
