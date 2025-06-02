package co.edu.udistrital.mdp.beautyathome.services;

import java.lang.classfile.ClassFile.Option;
import java.util.List;
import java.util.Optional;

import org.modelmapper.spi.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.beautyathome.entities.ServiceEntity;
import co.edu.udistrital.mdp.beautyathome.repositories.ProfessionalRepository;
import co.edu.udistrital.mdp.beautyathome.repositories.ServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Service


public class ServiceService {
    
    @Autowired  
    private ServiceRepository serviceRepository;

    @Autowired
    private ProfessionalRepository professionalRepository;


    /**
     * Crea un nuevo servicio en la base de datos.
     * 
     * @param service El servicio a crear.
     * @return El servicio creado.
     * @throws IllegalArgumentException Si el servicio no es válido.
     */
    @Transactional
    public ServiceEntity createService(ServiceEntity service) {
        log.info("Inciando el proceso de creacion de un servicio");
        if (service.getName() == null || service.getName().isEmpty()) 
            throw new IllegalArgumentException("El nombre del servicio no puede ser nulo o vacío");
        if (service.getDescription() == null || service.getDescription().isEmpty())
            throw new IllegalArgumentException("La descripción del servicio no puede ser nula o vacía");
        if (service.getPrice() <= 0)
            throw new IllegalArgumentException("El precio del servicio debe ser mayor a cero");
        if (service.getProfessional() == null || !professionalRepository.existsById(service.getProfessional().getId()))
            throw new IllegalArgumentException("El profesional del servicio no es válido o no existe");
        ServiceEntity savedService = serviceRepository.save(service);
        log.info("Servicio creado con éxito: {}", savedService);
        return savedService;
    }
        
    /**
     * Obtiene todos los servicios de la base de datos.
     * 
     * @return Una lista de servicios.
     */
    @Transactional
    public List<ServiceEntity> getServices() {
        log.info("Obteniendo todos los servicios");
        return serviceRepository.findAll();
    }
    /**
     * Obtiene un servicio por su ID.
     * 
     * @param serviceId El ID del servicio a buscar.
     * @return El servicio encontrado.
     * @throws EntityNotFoundException Si el servicio no existe.
     */
    @Transactional
    public ServiceEntity getService(Long serviceID) throws EntityNotFoundException{
        log.info("iniciado la busqueda del servicio con id: {}", serviceID);
        Optional<ServiceEntity> serviceEntity = serviceRepository.findById(serviceID);
        if (serviceEntity.isEmpty()) 
            throw new EntityNotFoundException("Servicio no encontrado con id: " + serviceID);
        log.info("terminando el proceso de buscar el servicio por ID", serviceEntity);
        return serviceEntity.get();
    }

    /**
     * Actualiza un servicio existente.
     * 
     * @param service El servicio con los datos actualizados.
     * @return El servicio actualizado.
     * @throws EntityNotFoundException Si el servicio no existe.
     */
    @Transactional
    public ServiceEntity updateService(Long serviceId, ServiceEntity service) throws EntityNotFoundException {
        log.info("Iniciando el proceso de actualización del servicio con id: {}", serviceId);
        Optional<ServiceEntity> optionalServiceEntity = serviceRepository.findById(serviceId);
        if (optionalServiceEntity.isEmpty()) {
            throw new EntityNotFoundException("Servicio no encontrado con id: " + serviceId);
        }
        service.setId(serviceId);
        ServiceEntity updatedService = serviceRepository.save(service);
        log.info("Servicio actualizado con éxito: {}", updatedService);
        return updatedService;
    }

    /**
     * Elimina un servicio por su ID.
     * 
     * @param serviceId El ID del servicio a eliminar.
     * @throws EntityNotFoundException Si el servicio no existe.
     */
    @Transactional
    public void deleteService(Long serviceId) throws EntityNotFoundException {
        log.info("Iniciando el proceso de eliminación del servicio con id: {}", serviceId);
        Optional<ServiceEntity> optionalServiceEntity = serviceRepository.findById(serviceId);
        if (optionalServiceEntity.isEmpty()) {
            throw new EntityNotFoundException("Servicio no encontrado con id: " + serviceId);
        }
        serviceRepository.deleteById(serviceId);
        log.info("Servicio eliminado con éxito");
    }

}
