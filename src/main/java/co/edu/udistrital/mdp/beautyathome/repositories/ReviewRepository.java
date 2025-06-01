package co.edu.udistrital.mdp.beautyathome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.udistrital.mdp.beautyathome.entities.ReviewEntity;

@Repository
public interface ReviewRepository extends JpaRepository <ReviewEntity, Long>  {
 
} 