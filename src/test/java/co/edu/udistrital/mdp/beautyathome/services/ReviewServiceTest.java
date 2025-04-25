package co.edu.udistrital.mdp.beautyathome.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
import co.edu.udistrital.mdp.beautyathome.entities.ServiceEntity;
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
    private TestEntityManager entityManager;

    // Referencia a una librería que facilita la creación de instancias de objetos
    // con datos ficticios.
    private PodamFactory factory = new PodamFactoryImpl();
    // Lista de reseñas
    private List<ReviewEntity> reviews = new ArrayList<>();
    // Lista de servicios a ser reseñados
    private List<ServiceEntity> services = new ArrayList<>();
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
            ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
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
            review.setService(services.get(i % services.size()));
            review.setClient(clients.get(i % clients.size()));
            review.setOpinion("Opinión de prueba " + (i + 1));
            // estrellas entre 1 y 5
            review.setStars((i % 5) + 1);
            entityManager.persist(review);
            reviews.add(review);
        }
    }

    @Test
    public void testCreateReview() {
        ReviewEntity newEntity = factory.manufacturePojo(ReviewEntity.class);
        newEntity.setService(services.get(0));
        newEntity.setClient(clients.get(0));

        ReviewEntity result = reviewService.createReview(newEntity);
        assertNotNull(result);
        ReviewEntity entity = entityManager.find(ReviewEntity.class, result.getId());

        assertEquals(newEntity.getService(), entity.getService());
        assertEquals(newEntity.getClient(), entity.getClient());
        assertEquals(newEntity.getOpinion(), entity.getOpinion());
        assertEquals(newEntity.getStars(), entity.getStars());

    }

    @Test
    public void testCreateReviewWithNullService(){ 
        assertThrows(IllegalArgumentException.class, ()-> {
            
        ReviewEntity newEntity = factory.manufacturePojo(ReviewEntity.class);
        newEntity.setService(null);
        newEntity.setClient(clients.get(0));
        
        reviewService.createReview(newEntity);
    });    
    }

    @Test
    public void testCreateReviewWithNullClient(){
        assertThrows(IllegalArgumentException.class, ()->{
            ReviewEntity newEntity = factory.manufacturePojo(ReviewEntity.class);
            newEntity.setService(services.get(0));
            newEntity.setClient(null);

            reviewService.createReview(newEntity);
        });
    }

    @Test
    public void testCreateReviewWithEmptyOpinion(){
        assertThrows(IllegalArgumentException.class, ()->{
            ReviewEntity newEntity = factory.manufacturePojo(ReviewEntity.class);

            newEntity.setService(services.get(0));
            newEntity.setClient(clients.get(0));
            newEntity.setOpinion("");

            reviewService.createReview(newEntity);
        });
    }

    @Test
    public void testCreateReviewWithNullOpinion(){
        assertThrows(IllegalArgumentException.class, ()->{
            ReviewEntity newEntity = factory.manufacturePojo(ReviewEntity.class);

            newEntity.setService(services.get(0));
            newEntity.setClient(clients.get(0));
            newEntity.setOpinion(null);

            reviewService.createReview(newEntity);
        });
    }

    @Test
    public void testCreateReviewWithStarsLargerThanFive(){
        assertThrows(IllegalArgumentException.class, ()->{
            ReviewEntity newEntity = factory.manufacturePojo(ReviewEntity.class);

            newEntity.setService(services.get(0));
            newEntity.setClient(clients.get(0));
            newEntity.setStars(6);

            reviewService.createReview(newEntity);
        });
    }

    @Test
    public void testCreateReviewWithStarsSmallerThanFive(){
        assertThrows(IllegalArgumentException.class, ()->{
            ReviewEntity newEntity = factory.manufacturePojo(ReviewEntity.class);

            newEntity.setService(services.get(0));
            newEntity.setClient(clients.get(0));
            newEntity.setStars(-1);

            reviewService.createReview(newEntity);
        });
    }

}
  
     