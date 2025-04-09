package co.edu.udistrital.mdp.beautyathome.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.udistrital.mdp.beautyathome.entities.ClienteEntity;

@Repository

public interface ClienteRepository extends JpaRepository<ClienteEntity, Long>{
    Optional<ClienteEntity> findByEmail(String email);
}
