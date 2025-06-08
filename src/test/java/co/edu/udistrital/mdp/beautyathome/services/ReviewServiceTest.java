package co.edu.udistrital.mdp.beautyathome.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.udistrital.mdp.beautyathome.entities.ClientEntity;
import co.edu.udistrital.mdp.beautyathome.entities.ReviewEntity;
import co.edu.udistrital.mdp.beautyathome.entities.ServiceEntity;
import co.edu.udistrital.mdp.beautyathome.entities.ServiceRecordEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(ReviewService.class)
public class ReviewServiceTest {

    @Autowired
    private ReviewService reviewService;

    @Autowired
    //TestEntityManager permite realizar operaciones de persistencia en una base de datos en memoria.
    private TestEntityManager entityManager;
    // Referencia a una librería que facilita la creación de instancias de objetos
    // con datos ficticios.
    private PodamFactory factory = new PodamFactoryImpl();
    // Lista de reseñas
    private List<ReviewEntity> reviews = new ArrayList<>();
    // Lista de servicios a ser reseñados
    private List<ServiceRecordEntity> services = new ArrayList<>();
    // Lista de clientes que hicieron las reseñas
    private List<ClientEntity> clients = new ArrayList<>();

    /**
     * Configuración inicial de la prueba
     */
    @BeforeEach
    public void setUp() {
        clearData();
        insertData();
    }

    public void clearData() {
        entityManager.getEntityManager().createQuery("delete from ReviewEntity").executeUpdate();
    }

    public void insertData() {

        // Crear servicios a reseñar
        for (int i = 0; i < 3; i++) {
            ServiceRecordEntity service = factory.manufacturePojo(ServiceRecordEntity.class);
            entityManager.persist(service);
            services.add(service);
        }

        // Crear clientes que harán las reseñas
        for (int i = 0; i < 3; i++) {
            ClientEntity client = factory.manufacturePojo(ClientEntity.class);
            entityManager.persist(client);
            clients.add(client);
        }

        // Crear tres reseñas
        for (int i = 0; i < 3; i++) {
            ReviewEntity review = factory.manufacturePojo(ReviewEntity.class);
            review.setServiceRecord(services.get(i % services.size()));
            review.setClient(clients.get(i % clients.size()));
            review.setOpinion("Opinión de prueba " + (i + 1));
            // estrellas entre 1 y 5
            review.setStars((i % 5) + 1);
            //Almacenar en la base de datos cada una de las reseñas
            entityManager.persist(review);
            reviews.add(review);
        }
    }

    @Test
    public void testCreateReview() throws IllegalOperationException {
        ReviewEntity newEntity = factory.manufacturePojo(ReviewEntity.class);
        newEntity.setServiceRecord(services.get(0));
        newEntity.setClient(clients.get(0));

        ReviewEntity result = reviewService.createReview(newEntity);
        assertNotNull(result);
        ReviewEntity entity = entityManager.find(ReviewEntity.class, result.getId());

        assertEquals(newEntity.getServiceRecord(), entity.getServiceRecord());
        assertEquals(newEntity.getClient(), entity.getClient());
        assertEquals(newEntity.getOpinion(), entity.getOpinion());
        assertEquals(newEntity.getStars(), entity.getStars());

    }

    @Test
    public void testCreateReviewWithNullService(){ 
        assertThrows(IllegalOperationException.class, ()-> {
            
        ReviewEntity newEntity = factory.manufacturePojo(ReviewEntity.class);
        newEntity.setServiceRecord(null);
        newEntity.setClient(clients.get(0));
        
        reviewService.createReview(newEntity);
    });    
    }

    @Test
    public void testCreateReviewWithNullClient(){
        assertThrows(IllegalOperationException.class, ()->{
            ReviewEntity newEntity = factory.manufacturePojo(ReviewEntity.class);
            newEntity.setServiceRecord(services.get(0));
            newEntity.setClient(null);

            reviewService.createReview(newEntity);
        });
    }

    @Test
    public void testCreateReviewWithEmptyOpinion(){
        assertThrows(IllegalOperationException.class, ()->{
            ReviewEntity newEntity = factory.manufacturePojo(ReviewEntity.class);

            newEntity.setServiceRecord(services.get(0));
            newEntity.setClient(clients.get(0));
            newEntity.setOpinion("");

            reviewService.createReview(newEntity);
        });
    }

    @Test
    public void testCreateReviewWithNullOpinion(){
        assertThrows(IllegalOperationException.class, ()->{
            ReviewEntity newEntity = factory.manufacturePojo(ReviewEntity.class);

            newEntity.setServiceRecord(services.get(0));
            newEntity.setClient(clients.get(0));
            newEntity.setOpinion(null);

            reviewService.createReview(newEntity);
        });
    }

    @Test
    public void testCreateReviewWithStarsLargerThanFive(){
        assertThrows(IllegalOperationException.class, ()->{
            ReviewEntity newEntity = factory.manufacturePojo(ReviewEntity.class);

            newEntity.setServiceRecord(services.get(0));
            newEntity.setClient(clients.get(0));
            newEntity.setStars(6);

            reviewService.createReview(newEntity);
        });
    }

    @Test
    public void testCreateReviewWithStarsSmallerThanFive(){
        assertThrows(IllegalOperationException.class, ()->{
            ReviewEntity newEntity = factory.manufacturePojo(ReviewEntity.class);

            newEntity.setServiceRecord(services.get(0));
            newEntity.setClient(clients.get(0));
            newEntity.setStars(-1);

            reviewService.createReview(newEntity);
        });
    }

    @Test
    public void testGetReviews(){
        List<ReviewEntity> reviewsFromDabaBase = reviewService.getReviews();
        assertEquals(reviewsFromDabaBase.size(), reviews.size());
        for(ReviewEntity entity : reviewsFromDabaBase){
            Boolean found = false;
            for(ReviewEntity storedEntity : reviews){
                if(entity.getId() == storedEntity.getId()){
                    found = true;
                }
            }
            assertTrue(found);
        }
    }

    @Test
    public void testGetValidReview(){
        ReviewEntity reviewEntity = reviews.get(0);
        ReviewEntity resulEntity = reviewService.getReview(reviewEntity.getId());
        assertEquals(reviewEntity.getId(), resulEntity.getId());
        assertEquals(reviewEntity.getServiceRecord(), resulEntity.getServiceRecord());
        assertEquals(reviewEntity.getClient(), resulEntity.getClient());
        assertEquals(reviewEntity.getOpinion(), resulEntity.getOpinion());
        assertEquals(reviewEntity.getStars(), resulEntity.getStars());
    }

    @Test 
    public void testGetInvalidReview(){
        assertThrows(EntityNotFoundException.class, ()-> {
            reviewService.getReview(0L);
        });
    }

    @Test
    public void testUpdateValidReview() throws IllegalOperationException{
        ReviewEntity reviewEntity = reviews.get(0);
        ReviewEntity updatedReviewEntity = factory.manufacturePojo(ReviewEntity.class);
        updatedReviewEntity.setClient(clients.get(1));
        updatedReviewEntity.setServiceRecord(services.get(1));

        updatedReviewEntity.setId(reviewEntity.getId());
        reviewService.updateReview(reviewEntity.getId(), updatedReviewEntity);

        ReviewEntity response = entityManager.find(ReviewEntity.class, reviewEntity.getId());

        assertEquals(updatedReviewEntity.getId(), response.getId());
        assertEquals(updatedReviewEntity.getServiceRecord(), response.getServiceRecord());
        assertEquals(updatedReviewEntity.getClient(), response.getClient());
        assertEquals(updatedReviewEntity.getOpinion(), response.getOpinion());
        assertEquals(updatedReviewEntity.getStars(), response.getStars());
    }

    @Test
    public void testUpdateInvalidReview(){
        assertThrows(EntityNotFoundException.class, ()->{
            ReviewEntity updatedReview = factory.manufacturePojo(ReviewEntity.class);
            updatedReview.setId(0L);
            reviewService.updateReview(0L, updatedReview);
        });
    }

    @Test
    public void testUpdateInvalidReviewWithNullService(){
        assertThrows(IllegalOperationException.class, ()->{
            ReviewEntity reviewEntity = reviews.get(0);
            ReviewEntity updatedReview = factory.manufacturePojo(ReviewEntity.class);
            updatedReview.setServiceRecord(null);
            updatedReview.setId(reviewEntity.getId());
            reviewService.updateReview(reviewEntity.getId(), updatedReview);
        });
    }

    @Test
    public void testUpdateInvalidReviewWithNullClient(){
        assertThrows(IllegalOperationException.class, ()->{
            ReviewEntity reviewEntity = reviews.get(0);
            ReviewEntity updatedReview = factory.manufacturePojo(ReviewEntity.class);
            updatedReview.setClient(null);
            updatedReview.setId(reviewEntity.getId());
            reviewService.updateReview(reviewEntity.getId(), updatedReview);
        });
    }

    @Test
    public void testUpdateInvalidReviewWithNullOpinion(){
        assertThrows(IllegalOperationException.class, ()->{
            ReviewEntity reviewEntity = reviews.get(0);
            ReviewEntity updatedReview = factory.manufacturePojo(ReviewEntity.class);
            updatedReview.setOpinion(null);
            updatedReview.setId(reviewEntity.getId());
            reviewService.updateReview(reviewEntity.getId(), updatedReview);
        });
    }

    @Test
    public void testUpdateInvalidReviewWithEmptyOpinion(){
        assertThrows(IllegalOperationException.class, ()->{
            ReviewEntity reviewEntity = reviews.get(0);
            ReviewEntity updatedReview = factory.manufacturePojo(ReviewEntity.class);
            updatedReview.setOpinion("");
            updatedReview.setId(reviewEntity.getId());
            reviewService.updateReview(reviewEntity.getId(), updatedReview);
        });
    }

    @Test
    public void testUpdateInvalidReviewWithStarsBelow1(){
        assertThrows(IllegalOperationException.class, ()->{
            ReviewEntity reviewEntity = reviews.get(0);
            ReviewEntity updatedReview = factory.manufacturePojo(ReviewEntity.class);
            updatedReview.setStars(-1);
            updatedReview.setId(reviewEntity.getId());
            reviewService.updateReview(reviewEntity.getId(), updatedReview);
        });
    }

    @Test
    public void testUpdateInvalidReviewWithStarsAbove5(){
        assertThrows(IllegalOperationException.class, ()->{
            ReviewEntity reviewEntity = reviews.get(0);
            ReviewEntity updatedReview = factory.manufacturePojo(ReviewEntity.class);
            updatedReview.setStars(6);
            updatedReview.setId(reviewEntity.getId());
            reviewService.updateReview(reviewEntity.getId(), updatedReview);
        });
    }

    @Test
    public void testDeleteValidReview(){
        ReviewEntity reviewEntity = reviews.get(0);
        reviewService.deleteReview(reviewEntity.getId());
        ReviewEntity deletedReview = entityManager.find(ReviewEntity.class, reviewEntity.getId());
        assertNull(deletedReview);
    }

    @Test
    public void testDeleteInvalidReview(){
        assertThrows(EntityNotFoundException.class, ()->{
            reviewService.deleteReview(0L);
        });
    }
}
  
     