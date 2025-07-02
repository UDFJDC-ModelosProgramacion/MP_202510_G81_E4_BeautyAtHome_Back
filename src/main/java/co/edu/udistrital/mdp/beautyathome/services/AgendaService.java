package co.edu.udistrital.mdp.beautyathome.services;

import co.edu.udistrital.mdp.beautyathome.entities.AgendaEntity;
<<<<<<< HEAD
=======
import co.edu.udistrital.mdp.beautyathome.entities.ClientEntity;
>>>>>>> bb3985135ae4ac0249b7691d83b801a74dc0283a
import co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.repositories.AgendaRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
<<<<<<< HEAD
=======

@Slf4j
>>>>>>> bb3985135ae4ac0249b7691d83b801a74dc0283a

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    @Transactional
    public AgendaEntity createAgenda(AgendaEntity agendaEntity) throws IllegalOperationException {
        if(agendaEntity.getProfessional() == null){
            throw new IllegalOperationException("The professional is not valid");
        }
        return agendaRepository.save(agendaEntity);
    }

    @Transactional
    public List<AgendaEntity> getAgendas() {
        return agendaRepository.findAll();
    }

<<<<<<< HEAD
        /**
=======
    /**
>>>>>>> bb3985135ae4ac0249b7691d83b801a74dc0283a
     * Obtiene una agenda por su ID.
     * 
     * @param id El ID de la agenda a buscar.
     * @return La agenda encontrada.
     * @throws EntityNotFoundException Si la agenda no existe.
     */
    @Transactional
    public AgendaEntity getAgenda(Long id) throws EntityNotFoundException {
        Optional<AgendaEntity> agendaEntity = agendaRepository.findById(id);
        return agendaEntity.orElseThrow(() -> new EntityNotFoundException("The agenda with the given id was not found: " + id));
    }

<<<<<<< HEAD
        /**
=======
    /**
>>>>>>> bb3985135ae4ac0249b7691d83b801a74dc0283a
     * Actualiza una agenda existente.
     * 
     * @param agendaId El ID de la agenda a actualizar.
     * @param agenda La agenda con los datos actualizados.
     * @return La agenda actualizada.
     * @throws EntityNotFoundException Si la agenda no existe.
     * @throws IllegalOperationException Si la agenda no es válida.
     */
    @Transactional
    public AgendaEntity updateAgenda(Long agendaId, AgendaEntity agenda) throws IllegalOperationException, EntityNotFoundException {
<<<<<<< HEAD
=======
        log.info("Iniciando el proceso de actualización de la agenda con id: {}", agendaId);
>>>>>>> bb3985135ae4ac0249b7691d83b801a74dc0283a
        Optional<AgendaEntity> optionalAgendaEntity = agendaRepository.findById(agendaId);
        optionalAgendaEntity.orElseThrow(() -> new EntityNotFoundException("The agenda with the given id was not found: " + agendaId));
        agenda.setId(agendaId);

        if (agenda.getProfessional() == null) {
            throw new IllegalOperationException("El profesional no puede ser nulo");
        }
        if (agenda.getAppointments() == null || agenda.getAppointments().isEmpty()) {
            throw new IllegalOperationException("Las citas de la agenda no pueden ser nulas o vacías");
        }
        AgendaEntity updatedAgenda = agendaRepository.save(agenda);
<<<<<<< HEAD
        return updatedAgenda;
    }

=======
        log.info("Agenda actualizada con éxito: {}", updatedAgenda);
        return updatedAgenda;
    }
>>>>>>> bb3985135ae4ac0249b7691d83b801a74dc0283a
    /**
     * Elimina un agenda por su ID.
     * @param agendaId El ID del agenda a eliminar.
     * @throws EntityNotFoundException Si el agenda no existe.
     */
    @Transactional
    public void deleteAgenda(Long agendaId) throws EntityNotFoundException {
        Optional<AgendaEntity> optionalAgendaEntity = agendaRepository.findById(agendaId);
        optionalAgendaEntity.orElseThrow(() -> new EntityNotFoundException("The agenda with the given id was not found: " + agendaId));
<<<<<<< HEAD
=======
        log.info("Iniciando el proceso de eliminación de la agenda con id: {}", agendaId);
>>>>>>> bb3985135ae4ac0249b7691d83b801a74dc0283a
        agendaRepository.deleteById(agendaId);
    }
}

