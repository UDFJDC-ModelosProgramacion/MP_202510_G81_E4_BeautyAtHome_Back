package co.edu.udistrital.mdp.beautyathome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.udistrital.mdp.beautyathome.entities.AgendaEntity;

@Repository
public interface AgendaRepository extends JpaRepository<AgendaEntity, Long> {
    AgendaEntity findByProfessional_Id(Long professionalId);
}
