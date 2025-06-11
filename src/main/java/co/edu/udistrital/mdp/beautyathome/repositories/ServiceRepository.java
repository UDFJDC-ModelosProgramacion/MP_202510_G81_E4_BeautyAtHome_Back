package co.edu.udistrital.mdp.beautyathome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.udistrital.mdp.beautyathome.entities.ServiceEntity;
import java.util.List;
import java.util.Optional;


@Repository
public interface ServiceRepository extends JpaRepository<ServiceEntity, Long> {
    List<ServiceEntity> findByProfessional_Id(Long professionalId);
    Optional<ServiceEntity> findById(Long id);
    Optional<ServiceEntity> findByName(String name);
    Optional<ServiceEntity> findByPrice(Double price);
} 

