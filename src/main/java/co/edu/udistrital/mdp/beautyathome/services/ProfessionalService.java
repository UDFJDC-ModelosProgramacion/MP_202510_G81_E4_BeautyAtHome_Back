package co.edu.udistrital.mdp.beautyathome.services;

import java.util.List;
import java.util.Optional;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.beautyathome.entities.ProfessionalEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.repositories.ProfessionalRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Service


public class ProfessionalService {
    
    @Autowired
    ProfessionalRepository professionalRepository;
    /**
     * Método que se encarga de pedirle a ProfessionalRepository que guarda un professional en la base de datos.
     * @param professional a almacenar 
     * @return el professional guardado en la base de datos.
     */

     @Transactional
    public ProfessionalEntity createProfessional(ProfessionalEntity professionalEntity) {
        log.info("iniciando el proceso de creación de un professional");

        if (professionalEntity.getName() == null)
            throw new IllegalArgumentException("El nombre del professional no puede ser nulo");

        if (professionalEntity.getBirthDate() == null)
            throw new IllegalArgumentException("La fecha de nacimiento del professional no puede ser nula");
        if (professionalEntity.getProfessionalSummary() == null || professionalEntity.getProfessionalSummary().isEmpty())
            throw new IllegalArgumentException("El resumen de la experiencia del professional no puede ser nulo o vacío");
        if (professionalEntity.getPhoto() == null || professionalEntity.getPhoto().isEmpty())
            throw new IllegalArgumentException("La foto del professional no puede ser nula o vacía");
        ProfessionalEntity savedProfessional = professionalRepository.save(professionalEntity);
        log.info("Professional creado con éxito: {}", savedProfessional);
        return savedProfessional;
    }

    /**
     * Método que se encarga de obtener todas los ProfessionalEntity de la base de datos
     * @return La lista de professionales encontrados.
     */
    @Transactional 
    public List<ProfessionalEntity> getProfessionales() {
        log.info("Obteniendo todos los professionales");
        return professionalRepository.findAll();
    }

    /**
     * Método que se encarga de obtener un ProfessionalEntity de la base da deatos mediante su id.
     * @param professionalId id del ProfessionalEntity a buscar.
     * @return El ProfessionalEntity correspondiente. 
     * @throws EntityNotFoundException en caso de no encontrar el professional.
     */

    @Transactional
    public ProfessionalEntity getProfessionalById(Long professionalId) throws EntityNotFoundException{
        log.info("Iniciando la búsqueda del professional con id: {}", professionalId);
        Optional<ProfessionalEntity> professionalEntity = professionalRepository.findById(professionalId);
        if (professionalEntity.isEmpty())
            throw new EntityNotFoundException("Professional no encontrado con id: " + professionalId);
        log.info("terminal el proceso de buscar el professional por ID", professionalEntity);
        return professionalEntity.get();
    }

    /**
     * Método que se encarga de actualizar un professional en la base de datos.
     * @param professionalId id del professional a actualizar.
     * @param professionalEntity professional que contiene los datos a actualizar.
     * @return El professional actualizado.
     * @throws IllegalArgumentException si algún campo es nulo o vacío.
     * @throws EntityNotFoundException si no se encuentra el professional a actualizar.
     */

    @Transactional
    public ProfessionalEntity updateProfsional(Long professionalId, ProfessionalEntity professionalEntity) throws EntityNotFoundException {

        log.info("Iniciando el proceso de actualización del professional con id: {}", professionalId);
        Optional<ProfessionalEntity> optionalProfessionalEntity = professionalRepository.findById(professionalId);
        if (optionalProfessionalEntity.isEmpty()) {
            throw new EntityNotFoundException("Professional no encontrado con id: " + professionalId);
        }
        professionalEntity.setId(professionalId);
        log.info("Termina el proceso de actualización del professional con id: {}", professionalId);

        return professionalRepository.save(professionalEntity);
    } 

    /**
     * Método que se encarga de eliminar un professional por su ID.
     * @param professionalId id del professional a eliminar.
     * @throws EntityNotFoundException si no se encuentra el professional a eliminar.
     */
    @Transactional
    public void deleteProfessional(Long profesioanlID) throws EntityNotFoundException, IllegalOperationException{

        log.info("Iniciando el proceso de eliminación del professional con id: {}", profesioanlID);
        Optional<ProfessionalEntity> optionalProfessionalEntity = professionalRepository.findById(profesioanlID);
        if (optionalProfessionalEntity.isEmpty()) {
            throw new EntityNotFoundException("Professional no encontrado con id: " + profesioanlID);
        }
        if (optionalProfessionalEntity.get().getServices() != null && !optionalProfessionalEntity.get().getServices().isEmpty()) {
            throw new IllegalOperationException("El professional no puede ser eliminado porque tiene servicios asociados");
        }
    }
}