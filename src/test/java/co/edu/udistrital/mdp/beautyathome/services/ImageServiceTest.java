// Paquete donde se encuentran las pruebas
package co.edu.udistrital.mdp.beautyathome.services;

// Importaciones estáticas para aserciones
import static org.junit.jupiter.api.Assertions.*;

// Otras importaciones necesarias
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import co.edu.udistrital.mdp.beautyathome.entities.ImageEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.repositories.ImageRepository;

/**
 * Clase de pruebas para el servicio ImageService.
 * Prueba las operaciones CRUD y validaciones de imágenes.
 */
@DataJpaTest // Configura entorno de prueba JPA con base de datos en memoria
@Transactional // Cada prueba se ejecuta en una transacción que se revierte al final
@EntityScan("co.edu.udistrital.mdp.beautyathome.entities") // Escanea entidades
@EnableJpaRepositories("co.edu.udistrital.mdp.beautyathome.repositories") // Habilita repositorios
@Import(ImageService.class) // Importa el servicio a probar
public class ImageServiceTest {

    @Autowired
    private ImageService service; // Servicio bajo prueba

    @Autowired
    private TestEntityManager em; // EntityManager para pruebas

    @Autowired
    private ImageRepository repo; // Repositorio (usado implícitamente por el servicio)

    private List<ImageEntity> images; // Lista de imágenes de prueba

    /**
     * Configuración inicial antes de cada prueba.
     * Limpia la base de datos y crea datos de prueba.
     */
    @BeforeEach
    public void setUp() {
        // Limpia la tabla de imágenes
        em.getEntityManager().createQuery("DELETE FROM ImageEntity").executeUpdate();

        // Inicializa lista de imágenes
        images = new ArrayList<>();

        // Crea 3 imágenes de prueba
        for (int i = 1; i <= 3; i++) {
            ImageEntity img = new ImageEntity();
            img.setUrl("http://example.com/img" + i + ".jpg"); // URLs de ejemplo
            em.persist(img); // Persiste en la base de datos
            images.add(img); // Guarda en la lista
        }

        em.flush(); // Sincroniza con la base de datos
    }

    /**
     * Prueba la creación exitosa de una imagen.
     */
    @Test
    public void testCreateValid() throws IllegalOperationException {
        ImageEntity img = new ImageEntity();
        img.setUrl("http://new.com/pic.jpg"); // URL válida

        ImageEntity out = service.createImage(img);

        // Verificaciones
        assertNotNull(out.getId()); // Debe tener ID asignado
        ImageEntity found = em.find(ImageEntity.class, out.getId());
        assertEquals("http://new.com/pic.jpg", found.getUrl()); // URL correcta
    }

    /**
     * Prueba la creación con URL inválida (vacía).
     */
    @Test
    public void testCreateInvalidUrl() {
        ImageEntity img = new ImageEntity();
        img.setUrl(""); // URL vacía - inválida

        assertThrows(IllegalOperationException.class,
                () -> service.createImage(img));
    }

    /**
     * Prueba la obtención de todas las imágenes.
     */
    @Test
    public void testGetAll() {
        List<ImageEntity> list = service.getAll();
        assertEquals(images.size(), list.size()); // Debe retornar las 3 imágenes creadas
    }

    /**
     * Prueba la obtención de una imagen existente.
     */
    @Test
    public void testGetValid() {
        ImageEntity img = images.get(0); // Primera imagen creada
        ImageEntity out = service.getOne(img.getId());

        assertEquals(img.getId(), out.getId()); // Debe ser la misma imagen
    }

    /**
     * Prueba la obtención con ID inválido.
     */
    @Test
    public void testGetInvalid() {
        assertThrows(EntityNotFoundException.class,
                () -> service.getOne(999L)); // ID inexistente
    }

    /**
     * Prueba la actualización exitosa de una imagen.
     */
    @Test
    public void testUpdateValid() throws IllegalOperationException {
        ImageEntity img = images.get(1); // Segunda imagen creada
        ImageEntity upd = new ImageEntity();
        upd.setUrl("http://updated.com/pic.jpg"); // Nueva URL

        service.updateImage(img.getId(), upd);

        // Verifica que se actualizó en la base de datos
        ImageEntity ver = em.find(ImageEntity.class, img.getId());
        assertEquals("http://updated.com/pic.jpg", ver.getUrl());
    }

    /**
     * Prueba la actualización con URL inválida.
     */
    @Test
    public void testUpdateInvalidUrl() {
        ImageEntity img = images.get(1);
        ImageEntity upd = new ImageEntity();
        upd.setUrl(""); // URL vacía - inválida

        assertThrows(IllegalOperationException.class,
                () -> service.updateImage(img.getId(), upd));
    }

    /**
     * Prueba la actualización con ID inválido.
     */
    @Test
    public void testUpdateInvalidId() {
        ImageEntity upd = new ImageEntity();
        upd.setUrl("http://any.com/pic.jpg");

        assertThrows(EntityNotFoundException.class,
                () -> service.updateImage(999L, upd)); // ID inexistente
    }

    /**
     * Prueba la eliminación exitosa de una imagen.
     */
    @Test
    public void testDeleteValid() {
        ImageEntity img = images.get(2); // Tercera imagen creada
        service.deleteImage(img.getId());

        assertNull(em.find(ImageEntity.class, img.getId())); // Debe ser eliminada
    }

    /**
     * Prueba la eliminación con ID inválido.
     */
    @Test
    public void testDeleteInvalid() {
        assertThrows(EntityNotFoundException.class,
                () -> service.deleteImage(999L)); // ID inexistente
    }
}