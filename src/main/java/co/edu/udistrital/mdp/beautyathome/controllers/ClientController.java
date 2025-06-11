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

import co.edu.udistrital.mdp.beautyathome.dto.ClientDTO;
import co.edu.udistrital.mdp.beautyathome.dto.ClientDetailDTO;
import co.edu.udistrital.mdp.beautyathome.entities.ClientEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.services.ClientService;
import jakarta.persistence.EntityNotFoundException;

/**
 * Controlador que expone endpoints para los clientes
 */
@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Método que utiliza ClientService para crear una review
     * @param clientDTO review a crear
     * @return una instancia de ClientDTO
     * @throws IllegalOperationException
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ClientDTO create(@RequestBody ClientDTO clientDTO) throws IllegalOperationException{
        ClientEntity clientEntity = clientService.createClient(modelMapper.map(clientDTO, ClientEntity.class));
        return modelMapper.map(clientEntity, ClientDTO .class);
    }

    /**
     * Método que utiliza ClientService para obtener todas las reviews
     * @return Lista con todos los ClientDTO
     */
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ClientDTO> findAll() {
	    List<ClientEntity> clients = clientService.getClients();
	    return modelMapper.map(clients, new TypeToken<List<ClientDTO>>() {
	    }.getType());
    }

    /**
     * Método que utiliza ClientService para obtener una review
     * @param id del client a obtener
     * @return una instancia de ClientDTO
     * @throws EntityNotFoundException
     * @throws co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException 
     */
    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ClientDTO findOne(@PathVariable Long id) throws EntityNotFoundException, co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException{
        ClientEntity clientEntity = clientService.getCLient(id);
        return modelMapper.map(clientEntity, ClientDTO.class);
    }

    /**
     * Método que utiliza ClientService para actualizar una review
     * @param id del client a actualizar
     * @param reviewDTO entidad ClientDTO actualizada
     * @return entidad ClientDTO actualizada
     * @throws IllegalOperationException
     * @throws co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException 
     */
    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ClientDTO update(@PathVariable Long id, @RequestBody ClientDTO clientDTO) throws IllegalOperationException, co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException{
        ClientEntity clientEntity = clientService.updateClient(id, modelMapper.map(clientDTO, ClientEntity.class));
        return modelMapper.map(clientEntity, ClientDTO.class);
    }

    /**
     * Método que utiliza ClientService para eliminar un client
     * @param id del client a eliminar
     * @throws EntityNotFoundException
     * @throws co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException 
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws EntityNotFoundException, co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException{
        clientService.deleteClient(id);
    }
}
