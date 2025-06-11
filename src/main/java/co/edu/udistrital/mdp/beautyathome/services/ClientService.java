package co.edu.udistrital.mdp.beautyathome.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.beautyathome.entities.ClientEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.beautyathome.repositories.ClientRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j

@Service


public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    /**
     * Crea un nuevo cliente en la base de datos.
     * 
     * @param client El cliente a crear.
     * @return El cliente creado.
     * @throws IllegalArgumentException Si el cliente no es válido.
     */
    public ClientEntity createClient(ClientEntity client) {
        log.info("Iniciando el proceso de creación de un cliente");
        if (client.getFullName() == null || client.getFullName().isEmpty()) 
            throw new IllegalArgumentException("El nombre del cliente no puede ser nulo o vacío");
        if (client.getEmail() == null || client.getEmail().isEmpty())
            throw new IllegalArgumentException("El email del cliente no puede ser nulo o vacío");
        if (client.getPhoneNumber() == null || client.getPhoneNumber().isEmpty())
            throw new IllegalArgumentException("El número de teléfono del cliente no puede ser nulo o vacío");
        ClientEntity savedClient = clientRepository.save(client);
        log.info("Cliente creado con éxito: {}", savedClient);
        return savedClient;

    }
    /**
     * Obtiene todos los clientes de la base de datos.
     * 
     * @return Una lista de clientes.
     */
    @Transactional
   public List<ClientEntity> getClients() {
        log.info("Obteniendo todos los clientes");
        return clientRepository.findAll();
    }

    /**
     * Obtiene un cliente por su ID.
     * 
     * @param id El ID del cliente a buscar.
     * @return El cliente encontrado.
     * @throws EntityNotFoundException Si el cliente no existe.
     */
    @Transactional
    public ClientEntity getCLient(Long clientID) throws EntityNotFoundException{
        log.info("iniciado la busqueda del cliente con id: {}", clientID);
        Optional<ClientEntity> clientEntity = clientRepository.findById(clientID);
        if (clientEntity.isEmpty()) 
            throw new EntityNotFoundException("The client with the given id was not found: " + clientID);
        log.info("terminando el proceso de buscar el servicio por ID", clientEntity);
        return clientEntity.get();
    }

    /**
     * Actualiza un cliente existente.
     * 
     * @param clientId El ID del cliente a actualizar.
     * @param client El cliente con los datos actualizados.
     * @return El cliente actualizado.
     * @throws EntityNotFoundException Si el cliente no existe.
     */
    @Transactional
    public ClientEntity updateClient(Long clientId, ClientEntity client) throws EntityNotFoundException {
        log.info("Iniciando el proceso de actualización del cliente con id: {}", clientId);
        Optional<ClientEntity> optionalClientEntity = clientRepository.findById(clientId);
        if (optionalClientEntity.isEmpty()) {
            throw new EntityNotFoundException("The client with the given id was not found: " + clientId);
        }
        client.setId(clientId);
        ClientEntity updatedClient = clientRepository.save(client);
        log.info("Cliente actualizado con éxito: {}", updatedClient);
        return updatedClient;
    }
    /**
     * Elimina un cliente por su ID.
     * @param clientId El ID del cliente a eliminar.
     * @throws EntityNotFoundException Si el cliente no existe.
     */
    @Transactional
    public void deleteClient(Long clientId) throws EntityNotFoundException {
        log.info("Iniciando el proceso de eliminación del cliente con id: {}", clientId);
        Optional<ClientEntity> optionalClientEntity = clientRepository.findById(clientId);
        if (optionalClientEntity.isEmpty()) {
            throw new EntityNotFoundException("The client with the given id was not found: " + clientId);
        }
        clientRepository.deleteById(clientId);
        log.info("Cliente eliminado con éxito");
    }
}