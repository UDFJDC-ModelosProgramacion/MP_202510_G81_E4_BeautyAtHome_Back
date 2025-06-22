package co.edu.udistrital.mdp.beautyathome.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.beautyathome.entities.ProfessionalEntity;
import co.edu.udistrital.mdp.beautyathome.entities.ServiceEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import jakarta.persistence.EntityNotFoundException;
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
    public ProfessionalEntity createProfessional(ProfessionalEntity professionalEntity) throws IllegalOperationException {
        log.info("Iniciando el proceso de creación de un professional");
        if (professionalEntity.getName() == null || professionalEntity.getName().isEmpty()) {
            throw new IllegalOperationException("El nombre del professional no puede ser nulo o vacío");
        }
        if (professionalEntity.getBirthDate() == null) {
            throw new IllegalOperationException("La fecha de nacimiento del professional no puede ser nula");
        }
        if (professionalEntity.getSummary() == null || professionalEntity.getSummary().isEmpty()) {
            throw new IllegalOperationException("El resumen de la experiencia del professional no puede ser nulo o vacío");
        }
        if (professionalEntity.getPhotoUrl() == null || professionalEntity.getPhotoUrl().isEmpty()) {
            throw new IllegalOperationException("La foto del professional no puede ser nula o vacía");
        }
        if (professionalEntity.getAgenda() == null) {
            throw new IllegalOperationException("La agenda del professional no puede ser nula");
        }
        if (professionalEntity.getCoverageAreas() == null || professionalEntity.getCoverageAreas().isEmpty()) {
            throw new IllegalOperationException("Las áreas de cobertura del professional no pueden ser nulas o vacías");
        }

        ProfessionalEntity savedProfessional = professionalRepository.save(professionalEntity);
        log.info("Professional creado con éxito: {}", savedProfessional);
        return savedProfessional;
    }

    /**
     * Método que se encarga de obtener todas los ProfessionalEntity de la base de datos
     * @return La lista de professionales encontrados.
     */
    @Transactional 
    public List<ProfessionalEntity> getProfessionals() {
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
    public ProfessionalEntity getProfessional(Long professionalId) throws EntityNotFoundException{
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
    public ProfessionalEntity updateProfessional(Long professionalId, ProfessionalEntity professionalEntity) throws EntityNotFoundException, IllegalOperationException {
        Optional<ProfessionalEntity> optionalProfessionalEntity = professionalRepository.findById(professionalId);
        optionalProfessionalEntity.orElseThrow(() -> new EntityNotFoundException("Professional no encontrado con id: " + professionalId));
        professionalEntity.setId(professionalId);
        if (professionalEntity.getName() == null || professionalEntity.getName().isEmpty()) {
            throw new IllegalOperationException("El nombre del professional no puede ser nulo o vacío");
        }
        if (professionalEntity.getBirthDate() == null) {
            throw new IllegalOperationException("La fecha de nacimiento del professional no puede ser nula");
        }
        if (professionalEntity.getSummary() == null || professionalEntity.getSummary().isEmpty()) {
            throw new IllegalOperationException("El resumen de la experiencia del professional no puede ser nulo o vacío");
        }
        if (professionalEntity.getPhotoUrl() == null || professionalEntity.getPhotoUrl().isEmpty()) {
            throw new IllegalOperationException("La foto del professional no puede ser nula o vacía");
        }
        if (professionalEntity.getAgenda() == null) {
            throw new IllegalOperationException("La agenda del professional no puede ser nula");
        }
        if (professionalEntity.getCoverageAreas() == null || professionalEntity.getCoverageAreas().isEmpty()) {
            throw new IllegalOperationException("Las áreas de cobertura del professional no pueden ser nulas o vacías");
        }
        ProfessionalEntity updatedProfessional = professionalRepository.save(professionalEntity);
        log.info("Professional actualizado con éxito: {}", updatedProfessional);
        return updatedProfessional;
    }
    /**
     * Método que se encarga de eliminar un professional por su ID.
     * @param professionalId id del professional a eliminar.
     * @throws EntityNotFoundException si no se encuentra el professional a eliminar.
     */
    @Transactional
    public void deleteProfessional(Long profesioanlID) throws EntityNotFoundException{
        Optional<ProfessionalEntity> optionalProfessionalEntity = professionalRepository.findById(profesioanlID);
        optionalProfessionalEntity.orElseThrow(() -> new EntityNotFoundException("Professional no encontrado con id: " + profesioanlID));
        professionalRepository.deleteById(profesioanlID);
    }
}