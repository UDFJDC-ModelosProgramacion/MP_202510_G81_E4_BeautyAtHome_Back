package co.edu.udistrital.mdp.beautyathome.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.beautyathome.entities.ServiceEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.repositories.ServiceRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Service


public class ServiceService {
    
    @Autowired  
    private ServiceRepository serviceRepository;

    /**
     * Crea un nuevo servicio en la base de datos.
     * 
     * @param service El servicio a crear.
     * @return El servicio creado.
     * @throws IllegalOperationException Si el servicio no es válido.
     */
    @Transactional
    public ServiceEntity createService(ServiceEntity service) throws IllegalOperationException {
        log.info("Inciando el proceso de creacion de un servicio");
        if (service.getName() == null )
            throw new IllegalOperationException("El nombre del servicio no puede ser nulo");
        if (service.getDescription() == null )
            throw new IllegalOperationException("La descripción del servicio no puede ser nula");
        if (service.getPrice() <= 0)
            throw new IllegalOperationException("El precio del servicio debe ser mayor a cero");
        if (service.getProfessional() == null )
            throw new IllegalOperationException("El profesional del servicio no es válido o no existe");
        if (service.getBrand() == null )
            throw new IllegalOperationException("La marca del servicio no es válida o no existe");
        if (service.getRecords() == null)
            throw new IllegalOperationException("El servicio debe tener al menos un registro asociado");
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
            throw new EntityNotFoundException("The client with the given id was not found: " + serviceID);
        log.info("terminando el proceso de buscar el servicio por ID", serviceEntity);
        return serviceEntity.get();
    }

    /**
     * Actualiza un servicio existente.
     * 
     * @param service El servicio con los datos actualizados.
     * @return El servicio actualizado.
     * @throws EntityNotFoundException Si el servicio no existe.
     * @throws IllegalOperationException Si el servicio no es válido.
     */
    @Transactional
    public ServiceEntity updateService(Long serviceId, ServiceEntity service) throws EntityNotFoundException, IllegalOperationException {
        Optional <ServiceEntity> optionalServiceEntity = serviceRepository.findById(serviceId);
        optionalServiceEntity.orElseThrow(() -> new EntityNotFoundException("The service with the given id was not found: " + serviceId));
        service.setId(serviceId);

        if (service.getName() == null ) {
            throw new IllegalOperationException("El nombre del servicio no puede ser nulo o vacío");
        }
        if (service.getDescription() == null ) {
            throw new IllegalOperationException("La descripción del servicio no puede ser nula o vacía");
        }
        if (service.getPrice() <= 0) {
            throw new IllegalOperationException("El precio del servicio debe ser mayor a cero");
        }
        if (service.getProfessional() == null ) {
            throw new IllegalOperationException("El profesional del servicio no es válido o no existe");
        }
        if (service.getBrand() == null ) {
            throw new IllegalOperationException("La marca del servicio no es válida o no existe");
        }
        if (service.getRecords() == null ) {
            throw new IllegalOperationException("El servicio debe tener al menos un registro asociado");
        }
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
            throw new EntityNotFoundException("The service with the given id was not found: " + serviceId);
        }
        serviceRepository.deleteById(serviceId);
        log.info("Servicio eliminado con éxito");
    }

}
