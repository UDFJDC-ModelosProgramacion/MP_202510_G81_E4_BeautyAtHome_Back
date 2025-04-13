package co.edu.udistrital.mdp.beautyathome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.udistrital.mdp.beautyathome.entities.ExclusiveProductEntity;
@Repository

public interface ExclusiveProductRepository extends JpaRepository <ExclusiveProductEntity, Long> {
}