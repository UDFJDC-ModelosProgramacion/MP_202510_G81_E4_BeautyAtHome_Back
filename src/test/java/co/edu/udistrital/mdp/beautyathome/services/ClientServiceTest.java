package co.edu.udistrital.mdp.beautyathome.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.udistrital.mdp.beautyathome.entities.ClientEntity;
import co.edu.udistrital.mdp.beautyathome.entities.ReviewEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(ClientService.class)
public class ClientServiceTest {

    @Autowired
    private ClientService clientService;
    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();
    private List<ClientEntity> clientList = new ArrayList<>();
    private ArrayList<ReviewEntity> reviewList = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        clearData();
        insertData();
    }


    private void clearData() {
        entityManager.getEntityManager().createQuery("DELETE FROM ReviewEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM ClientEntity").executeUpdate();
    }
    
    private void insertData() {
        for (int i = 0; i < 5; i++) {
            ClientEntity client = factory.manufacturePojo(ClientEntity.class);
            entityManager.persist(client);
            clientList.add(client);

            for (int j = 0; j < 3; j++) {
                ReviewEntity review = factory.manufacturePojo(ReviewEntity.class);
                review.setClient(client);
                entityManager.persist(review);
                reviewList.add(review);
            }
        }
    }

    /**
     * verifica que se pueda crear un cliente con éxito.
     */
    @Test
    public void testCreateClient()throws IllegalOperationException{
        ClientEntity newEntity = factory.manufacturePojo(ClientEntity.class);

        newEntity.setAddress("example address");
        newEntity.setEmail("example email");
        newEntity.setPhoneNumber("1234567890");
        newEntity.setFullName("example name");
        newEntity.setReviews(reviewList);
        
        ClientEntity result = clientService.createClient(newEntity);
        assertNotNull(result);
        ClientEntity entity = entityManager.find(ClientEntity.class, result.getId());

        assertEquals(newEntity.getAddress(), entity.getAddress());
        assertEquals(newEntity.getEmail(), entity.getEmail());
        assertEquals(newEntity.getPhoneNumber(), entity.getPhoneNumber());
        assertEquals(newEntity.getFullName(), entity.getFullName());
        assertEquals(newEntity.getReviews().size(), entity.getReviews().size());
    }

    @Test
    public void testCreateClientWithNullName() {
        assertThrows(IllegalOperationException.class, () -> {
            ClientEntity newEntity = factory.manufacturePojo(ClientEntity.class);
            newEntity.setAddress("example address");
            newEntity.setEmail("example email");
            newEntity.setPhoneNumber("1234567890");
            newEntity.setReviews(reviewList);
            newEntity.setFullName(null);

            clientService.createClient(newEntity);
        });
    }

    @Test
    public void testCreateClientWithNullAddress() {
        assertThrows(IllegalOperationException.class, () -> {
            ClientEntity newEntity = factory.manufacturePojo(ClientEntity.class);
            newEntity.setFullName("example name");
            newEntity.setEmail("example email");
            newEntity.setPhoneNumber("1234567890");
            newEntity.setReviews(reviewList);
            newEntity.setAddress(null);

            clientService.createClient(newEntity);
        });
    }
    @Test
    public void testCreateClientWithNullEmail() {
        assertThrows(IllegalOperationException.class, () -> {
            ClientEntity newEntity = factory.manufacturePojo(ClientEntity.class);
            newEntity.setFullName("example name");
            newEntity.setAddress("example address");
            newEntity.setPhoneNumber("1234567890");
            newEntity.setReviews(reviewList);
            newEntity.setEmail(null);

            clientService.createClient(newEntity);
        });
    }
    @Test
    public void testCreateClientWithNullPhoneNumber() {
        assertThrows(IllegalOperationException.class, () -> {
            ClientEntity newEntity = factory.manufacturePojo(ClientEntity.class);
            newEntity.setFullName("example name");
            newEntity.setAddress("example address");
            newEntity.setEmail("example email");
            newEntity.setReviews(reviewList);
            newEntity.setPhoneNumber(null);

            clientService.createClient(newEntity);
        });
    }
    @Test
    public void testCreateClientWithEmptyName() {
       assertThrows(IllegalOperationException.class, () -> {
            ClientEntity newEntity = factory.manufacturePojo(ClientEntity.class);
            newEntity.setAddress("example address");
            newEntity.setEmail("example email");
            newEntity.setPhoneNumber("1234567890");
            newEntity.setReviews(reviewList);
            newEntity.setFullName("");

            clientService.createClient(newEntity);
        });
    }
    @Test
    public void testCreateClientWithEmptyAddress() {
        assertThrows(IllegalOperationException.class, () -> {
            ClientEntity newEntity = factory.manufacturePojo(ClientEntity.class);
            newEntity.setFullName("example name");
            newEntity.setEmail("example email");
            newEntity.setPhoneNumber("1234567890");
            newEntity.setReviews(reviewList);
            newEntity.setAddress("");

            clientService.createClient(newEntity);
        });
    }
    @Test
    public void testCreateClientWithEmptyEmail() {
        assertThrows(IllegalOperationException.class, () -> {
            ClientEntity newEntity = factory.manufacturePojo(ClientEntity.class);
            newEntity.setFullName("example name");
            newEntity.setAddress("example address");
            newEntity.setPhoneNumber("1234567890");
            newEntity.setReviews(reviewList);
            newEntity.setEmail("");

            clientService.createClient(newEntity);
        });
    }
    @Test
    public void testCreateClientWithEmptyPhoneNumber() {
        assertThrows(IllegalOperationException.class, () -> {
            ClientEntity newEntity = factory.manufacturePojo(ClientEntity.class);
            newEntity.setFullName("example name");
            newEntity.setAddress("example address");
            newEntity.setEmail("example email");
            newEntity.setReviews(reviewList);
            newEntity.setPhoneNumber("");

            clientService.createClient(newEntity);
        });
    }
    @Test
    public void testCreateClientWithNullReviews() {
        assertThrows(IllegalOperationException.class, () -> {
            ClientEntity newEntity = factory.manufacturePojo(ClientEntity.class);
            newEntity.setFullName("example name");
            newEntity.setAddress("example address");
            newEntity.setEmail("example email");
            newEntity.setPhoneNumber("1234567890");
            newEntity.setReviews(null);

            clientService.createClient(newEntity);
        });
    }

    @Test
    public void testGetClients() {
        List<ClientEntity> list = clientService.getClients();
        assertEquals(clientList.size(), list.size());
        for (ClientEntity entity : list) {
            boolean found = false;
            for (ClientEntity storedEntity : clientList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                    break;
                }
            }
            assertEquals(true, found);
        }
    }

    @Test
    public void testGetValidClient() throws EntityNotFoundException{
        ClientEntity client = clientList.get(0);
        ClientEntity entity = clientService.getCLient(client.getId());
        assertEquals(client.getId(), entity.getId());
        assertEquals(client.getFullName(), entity.getFullName());
        assertEquals(client.getAddress(), entity.getAddress());
        assertEquals(client.getEmail(), entity.getEmail());
        assertEquals(client.getPhoneNumber(), entity.getPhoneNumber());
    }
    @Test
    public void testGetInvalidClient() {
    assertThrows(EntityNotFoundException.class, () -> {
        clientService.getCLient(0L);
    });
    }

    @Test
    public void testUpdateValidClient() throws IllegalOperationException,EntityNotFoundException {
        ClientEntity entity = clientList.get(0);
        ClientEntity updatedEntity = factory.manufacturePojo(ClientEntity.class);
        updatedEntity.setId(entity.getId());
        updatedEntity.setFullName("Updated Name");
        updatedEntity.setAddress("Updated Address");
        updatedEntity.setEmail("Updated Email");
        updatedEntity.setPhoneNumber("Updated Phone Number");
        updatedEntity.setReviews(reviewList);
        
        clientService.updateClient(entity.getId(), updatedEntity);
        ClientEntity response = entityManager.find(ClientEntity.class, entity.getId());

        assertEquals(updatedEntity.getId(), response.getId());
        assertEquals(updatedEntity.getFullName(), response.getFullName());
        assertEquals(updatedEntity.getAddress(), response.getAddress());
        assertEquals(updatedEntity.getEmail(), response.getEmail());
        assertEquals(updatedEntity.getPhoneNumber(), response.getPhoneNumber());
        assertEquals(updatedEntity.getReviews().size(), response.getReviews().size());

    }

    @Test
    public void testUpdateInvalidClient() {
        assertThrows(EntityNotFoundException.class, () -> {
            ClientEntity updatedEntity = factory.manufacturePojo(ClientEntity.class);
            updatedEntity.setId(0L);
            clientService.updateClient(0L, updatedEntity);
        });
    }

    @Test
    public void testUpdateClientWithNullName() {
        assertThrows(IllegalOperationException.class, () -> {
            ClientEntity entity = clientList.get(0);
            ClientEntity updatedEntity = factory.manufacturePojo(ClientEntity.class);
            updatedEntity.setId(entity.getId());
            updatedEntity.setFullName(null);
            updatedEntity.setAddress("Updated Address");
            updatedEntity.setEmail("Updated Email");
            updatedEntity.setPhoneNumber("Updated Phone Number");
            updatedEntity.setReviews(reviewList);
            clientService.updateClient(entity.getId(), updatedEntity);
        });
    }

    @Test
    public void testUpdateClientWithNullAddress() {
        assertThrows(IllegalOperationException.class, () -> {
            ClientEntity entity = clientList.get(0);
            ClientEntity updatedEntity = factory.manufacturePojo(ClientEntity.class);
            updatedEntity.setId(entity.getId());
            updatedEntity.setFullName("Updated Name");
            updatedEntity.setAddress(null);
            updatedEntity.setEmail("Updated Email");
            updatedEntity.setPhoneNumber("Updated Phone Number");
            updatedEntity.setReviews(reviewList);
            clientService.updateClient(entity.getId(), updatedEntity);
        });
    }

    @Test
    public void testUpdateClientWithNullEmail() {
        assertThrows(IllegalOperationException.class, () -> {
            ClientEntity entity = clientList.get(0);
            ClientEntity updatedEntity = factory.manufacturePojo(ClientEntity.class);
            updatedEntity.setId(entity.getId());
            updatedEntity.setFullName("Updated Name");
            updatedEntity.setAddress("Updated Address");
            updatedEntity.setEmail(null);
            updatedEntity.setPhoneNumber("Updated Phone Number");
            updatedEntity.setReviews(reviewList);
            clientService.updateClient(entity.getId(), updatedEntity);
        });
    }

    @Test
    public void testUpdateClientWithNullPhoneNumber() {
        assertThrows(IllegalOperationException.class, () -> {
            ClientEntity entity = clientList.get(0);
            ClientEntity updatedEntity = factory.manufacturePojo(ClientEntity.class);
            updatedEntity.setId(entity.getId());
            updatedEntity.setFullName("Updated Name");
            updatedEntity.setAddress("Updated Address");
            updatedEntity.setEmail("Updated Email");
            updatedEntity.setPhoneNumber(null);
            updatedEntity.setReviews(reviewList);
            clientService.updateClient(entity.getId(), updatedEntity);
        });
    }

    @Test
    public void testUpdateClientWithEmptyName() {
        assertThrows(IllegalOperationException.class, () -> {
            ClientEntity entity = clientList.get(0);
            ClientEntity updatedEntity = factory.manufacturePojo(ClientEntity.class);
            updatedEntity.setId(entity.getId());
            updatedEntity.setFullName("");
            updatedEntity.setAddress("Updated Address");
            updatedEntity.setEmail("Updated Email");
            updatedEntity.setPhoneNumber("Updated Phone Number");
            updatedEntity.setReviews(reviewList);
            clientService.updateClient(entity.getId(), updatedEntity);
        });
    }

    @Test
    public void testUpdateClientWithEmptyAddress() {
        assertThrows(IllegalOperationException.class, () -> {
            ClientEntity entity = clientList.get(0);
            ClientEntity updatedEntity = factory.manufacturePojo(ClientEntity.class);
            updatedEntity.setId(entity.getId());
            updatedEntity.setFullName("Updated Name");
            updatedEntity.setAddress("");
            updatedEntity.setEmail("Updated Email");
            updatedEntity.setPhoneNumber("Updated Phone Number");
            updatedEntity.setReviews(reviewList);
            clientService.updateClient(entity.getId(), updatedEntity);
        });
    }

    @Test
    public void testUpdateClientWithEmptyEmail() {
        assertThrows(IllegalOperationException.class, () -> {
            ClientEntity entity = clientList.get(0);
            ClientEntity updatedEntity = factory.manufacturePojo(ClientEntity.class);
            updatedEntity.setId(entity.getId());
            updatedEntity.setFullName("Updated Name");
            updatedEntity.setAddress("Updated Address");
            updatedEntity.setEmail("");
            updatedEntity.setPhoneNumber("Updated Phone Number");
            updatedEntity.setReviews(reviewList);
            clientService.updateClient(entity.getId(), updatedEntity);
        });
    }

    @Test
    public void testUpdateClientWithEmptyPhoneNumber() {
        assertThrows(IllegalOperationException.class, () -> {
            ClientEntity entity = clientList.get(0);
            ClientEntity updatedEntity = factory.manufacturePojo(ClientEntity.class);
            updatedEntity.setId(entity.getId());
            updatedEntity.setFullName("Updated Name");
            updatedEntity.setAddress("Updated Address");
            updatedEntity.setEmail("Updated Email");
            updatedEntity.setPhoneNumber("");
            updatedEntity.setReviews(reviewList);
            clientService.updateClient(entity.getId(), updatedEntity);
        });
    }

    @Test
    public void testUpdateClientWithNullReviews() {
        assertThrows(IllegalOperationException.class, () -> {
            ClientEntity entity = clientList.get(0);
            ClientEntity updatedEntity = factory.manufacturePojo(ClientEntity.class);
            updatedEntity.setId(entity.getId());
            updatedEntity.setFullName("Updated Name");
            updatedEntity.setAddress("Updated Address");
            updatedEntity.setEmail("Updated Email");
            updatedEntity.setPhoneNumber("Updated Phone Number");
            updatedEntity.setReviews(null);
            clientService.updateClient(entity.getId(), updatedEntity);
        });
    }

    

    @Test
    public void testDeleteValidClient() throws EntityNotFoundException {
        ClientEntity entity = clientList.get(0);
        clientService.deleteClient(entity.getId());
        ClientEntity deletedEntity = entityManager.find(ClientEntity.class, entity.getId());
        assertNull(deletedEntity);
    }
    @Test
    public void testDeleteInvalidClient() {
        assertThrows(EntityNotFoundException.class, () -> {
            clientService.deleteClient(0L);
        });
    }

}
