package co.edu.udistrital.mdp.beautyathome.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.udistrital.mdp.beautyathome.entities.ClientEntity;

@Repository

public interface ClientRepository extends JpaRepository<ClientEntity, Long>{
    Optional<ClientEntity> findById(Long id);
    Optional<ClientEntity> findByName(String name);
    Optional<ClientEntity> findByPhoneNumber(String phoneNumber);
    Optional<ClientEntity> findByEmail(String email);
}
