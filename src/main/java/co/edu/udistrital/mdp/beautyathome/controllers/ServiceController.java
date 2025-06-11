package co.edu.udistrital.mdp.beautyathome.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.udistrital.mdp.beautyathome.dto.ServiceDTO;
import co.edu.udistrital.mdp.beautyathome.entities.ServiceEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.services.ServiceService;

@RestController

@RequestMapping("/services")

public class ServiceController {
    
    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Método que utiliza ServiceService para crear un servicio
     * @param serviceDTO servicio a crear
     * @return una instancia de ServiceDTO
     * @throws IllegalOperationException
     * @throws EntityNotFoundException
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ServiceDTO create(@RequestBody ServiceDTO serviceDTO) throws IllegalOperationException, EntityNotFoundException {
        ServiceEntity serviceEntity = serviceService.createService(modelMapper.map(serviceDTO, ServiceEntity.class));
        return modelMapper.map(serviceEntity, ServiceDTO.class);
    }
    /**
     * Método que utiliza ServiceService para obtener todos los servicios
     * @return Lista con todos los serviciosDTO
     */
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ServiceDTO> findAll() {
        List<ServiceEntity> services = serviceService.getServices();
        return modelMapper.map(services, new TypeToken<List<ServiceDTO>>() {}.getType());
    }
    /**
     * Metodo que utiliza ServiceService para obtener un servicio
     * @param id identificador del servicio
     * @return una instancia de ServiceDTO
     * @throws EntityNotFoundException
     * @throws IllegalOperationException
     */
    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ServiceDTO findById(@PathVariable("id") Long id) throws EntityNotFoundException, IllegalOperationException {
        ServiceEntity serviceEntity = serviceService.getService(id);
        return modelMapper.map(serviceEntity, ServiceDTO.class);
    }
    /**
     * Método que utiliza ServiceService para actualizar un servicio
     * @param id identificador del servicio a actualizar
     * @param serviceDTO datos del servicio a actualizar
     * @return una instancia de ServiceDTO
     * @throws EntityNotFoundException
     * @throws IllegalOperationException
     */
    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ServiceDTO update(@PathVariable("id") Long id, @RequestBody ServiceDTO serviceDTO) throws EntityNotFoundException, IllegalOperationException {
        ServiceEntity serviceEntity = serviceService.updateService(id, modelMapper.map(serviceDTO, ServiceEntity.class));
        return modelMapper.map(serviceEntity, ServiceDTO.class);
    }
    /**
     * Método que utiliza ServiceService para eliminar un servicio
     * @param id identificador del servicio a eliminar
     * @throws EntityNotFoundException
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException {
        serviceService.deleteService(id);
    }
    
}
