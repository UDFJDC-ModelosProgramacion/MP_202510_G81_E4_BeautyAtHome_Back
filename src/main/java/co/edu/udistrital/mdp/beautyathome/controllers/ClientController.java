package co.edu.udistrital.mdp.beautyathome.controllers;



import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.udistrital.mdp.beautyathome.dto.ClientDTO;
import co.edu.udistrital.mdp.beautyathome.entities.ClientEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.beautyathome.services.ClientService;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/clients")

public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private ModelMapper modelMapper;
    
    /**
    *Metodo que utiliza ClientService para crear un client
    * @param clientDTO client a crear
    * @return una instancia de ClientDTO
    * @throws EntityNotFoundException
    */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ClientDTO create(ClientDTO clientDTO) throws EntityNotFoundException {
        ClientEntity clientEntity = clientService.createClient(modelMapper.map(clientDTO, ClientEntity.class));
        return modelMapper.map(clientEntity, ClientDTO.class);
    }
    
    /**
    * Metodo que utiliza ClientService para obtener todas los clients
    * @return Lista con todas los clientsDTO
    */
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ClientDTO> findAll() {
        List<ClientEntity> clients = clientService.getClients();
        return modelMapper.map(clients, new TypeToken<List<ClientDTO>>() {}.getType());
    }
    /**
     * Metodo que utiliza ClientService para obtener un cliente
     * @param id identificador del cliente
     * @return una instancia de ClientDTO
     * @throws EntityNotFoundException
     */
    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ClientDTO findById(Long id) throws EntityNotFoundException {
        ClientEntity clientEntity = clientService.getCLient(id);
        return modelMapper.map(clientEntity, ClientDTO.class);
    }
    /**
     * Metodo que utiliza ClientService para actualizar un cliente
     * @param id identificador del cliente
     * @param clientDTO cliente a actualizado
     * @return una instancia de ClientDTO
     * @throws EntityNotFoundException
     */
    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ClientDTO update(@PathVariable Long id, @RequestBody ClientDTO clientDTO) throws EntityNotFoundException {
        ClientEntity clientEntity = clientService.updateClient(id, modelMapper.map(clientDTO, ClientEntity.class));
        return modelMapper.map(clientEntity, ClientDTO.class);
    }
    /**
     * Metodo que utiliza ClientService para eliminar un cliente
     * @param id identificador del cliente
     * @throws EntityNotFoundException
     */
    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws EntityNotFoundException {
        clientService.deleteClient(id);
    }
}
