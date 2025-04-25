package co.edu.udistrital.mdp.beautyathome.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.udistrital.mdp.beautyathome.entities.ClientEntity;
import co.edu.udistrital.mdp.beautyathome.entities.ReviewEntity;


public interface ReviewRepository extends  JpaRepository<ReviewEntity, Long> {
    
    Optional<ReviewEntity> findById(Long id);
    List<ReviewEntity> findByStars(int stars);
    List<ReviewEntity> findByClient(ClientEntity client);

}