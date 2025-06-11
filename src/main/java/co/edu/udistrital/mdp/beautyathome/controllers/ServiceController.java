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
import co.edu.udistrital.mdp.beautyathome.dto.ServiceDetailDTO;
import co.edu.udistrital.mdp.beautyathome.entities.ServiceEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.services.ServiceService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/services")
public class ServiceController {

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Método que utiliza ServiceService para crear un service
     * @param serviceDTO service a crear
     * @return una instancia de ServiceDTO
     * @throws IllegalOperationException
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ServiceDTO create(@RequestBody ServiceDTO serviceDTO) throws IllegalOperationException{
        ServiceEntity serviceEntity = serviceService.createService(modelMapper.map(serviceDTO, ServiceEntity.class));
        return modelMapper.map(serviceEntity, ServiceDTO.class);
    }

    /**
     * Método que utiliza ServiceService para obtener todos los services
     * @return Lista con todos los serviceDTO
     */
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ServiceDTO> findAll() {
	    List<ServiceEntity> services = serviceService.getServices();
	    return modelMapper.map(services, new TypeToken<List<ServiceDTO>>() {
	    }.getType());
    }

    /**
     * Método que utiliza ServiceService para obtener un service
     * @param id del service a obtener
     * @return una instancia de ServiceDTO
     * @throws EntityNotFoundException
     * @throws co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException 
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ServiceDTO findOne(@PathVariable Long id) throws EntityNotFoundException, co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException{
        ServiceEntity serviceEntity = serviceService.getService(id);
        return modelMapper.map(serviceEntity, ServiceDTO.class);
    }

    /**
     * Método que utiliza ServiceService para actualizar un service
     * @param id del service a actualizar
     * @param serviceDTO entidad ServiceDTO actualizada
     * @return entidad ServiceDTO actualizada
     * @throws IllegalOperationException
     * @throws co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException 
     */
    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ServiceDTO update(@PathVariable Long id, @RequestBody ServiceDTO serviceDTO) throws IllegalOperationException, co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException{
        ServiceEntity serviceEntity = serviceService.updateService(id, modelMapper.map(serviceDTO, ServiceEntity.class));
        return modelMapper.map(serviceEntity, ServiceDTO.class);
    }

    /**
     * Método que utiliza ServiceService para eliminar un service
     * @param id del service a eliminar
     * @throws EntityNotFoundException
     * @throws co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException 
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws EntityNotFoundException, co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException{
       serviceService.deleteService(id);
    }
}
