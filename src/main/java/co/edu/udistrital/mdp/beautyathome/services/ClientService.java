package co.edu.udistrital.mdp.beautyathome.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.beautyathome.entities.ClientEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
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
    public ClientEntity createClient(ClientEntity client) throws IllegalOperationException {
        log.info("Iniciando el proceso de creación de un cliente");
        if (client.getFullName() == null || client.getFullName().isEmpty()) {
            throw new IllegalOperationException("El nombre del cliente no puede ser nulo o vacío");
        }
        if (client.getEmail() == null || client.getEmail().isEmpty()) {
            throw new IllegalOperationException("El correo electrónico del cliente no puede ser nulo o vacío");
        }
        if (client.getPhoneNumber() == null || client.getPhoneNumber().isEmpty()) {
            throw new IllegalOperationException("El número de teléfono del cliente no puede ser nulo o vacío");
        }
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
        Optional<ClientEntity> clientEntity = clientRepository.findById(clientID);
        return clientEntity.orElseThrow(() -> new EntityNotFoundException("The client with the given id was not found: " + clientID));
    }

    /**
     * Actualiza un cliente existente.
     * 
     * @param clientId El ID del cliente a actualizar.
     * @param client El cliente con los datos actualizados.
     * @return El cliente actualizado.
     * @throws EntityNotFoundException Si el cliente no existe.
     * @throws IllegalOperationException Si el cliente no es válido.
     */
    @Transactional
    public ClientEntity updateClient(Long clientId, ClientEntity client) throws IllegalOperationException,EntityNotFoundException {
        log.info("Iniciando el proceso de actualización del cliente con id: {}", clientId);
        Optional<ClientEntity> optionalClientEntity = clientRepository.findById(clientId);
        optionalClientEntity.orElseThrow(() -> new EntityNotFoundException("The client with the given id was not found: " + clientId));
        client.setId(clientId);

        if (client.getFullName() == null || client.getFullName().isEmpty()) {
            throw new IllegalOperationException("El nombre del cliente no puede ser nulo o vacío");
        }
        if (client.getEmail() == null || client.getEmail().isEmpty()) {
            throw new IllegalOperationException("El correo electrónico del cliente no puede ser nulo o vacío");
        }
        if (client.getPhoneNumber() == null || client.getPhoneNumber().isEmpty()) {
            throw new IllegalOperationException("El número de teléfono del cliente no puede ser nulo o vacío");
        }
        if (client.getAddress() == null || client.getAddress().isEmpty()) {
            throw new IllegalOperationException("La dirección del cliente no puede ser nula o vacía");
        }
        if (client.getReviews() != null && client.getReviews().isEmpty()) {
            throw new IllegalOperationException("La lista de reseñas del cliente no puede ser nula o vacía");
        }
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
        Optional<ClientEntity> optionalClientEntity = clientRepository.findById(clientId);
        optionalClientEntity.orElseThrow(() -> new EntityNotFoundException("The client with the given id was not found: " + clientId));
        log.info("Iniciando el proceso de eliminación del cliente con id: {}", clientId);
        clientRepository.deleteById(clientId);
    }
}