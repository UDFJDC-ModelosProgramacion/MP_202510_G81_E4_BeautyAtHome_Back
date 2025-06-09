// Paquete donde se encuentra la clase de prueba
package co.edu.udistrital.mdp.beautyathome.services;

// Importaciones estáticas para aserciones de JUnit
import static org.junit.jupiter.api.Assertions.*;

// Importaciones de clases necesarias
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import co.edu.udistrital.mdp.beautyathome.entities.BrandEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.repositories.BrandRepository;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Clase de pruebas unitarias para el servicio BrandService.
 * Prueba las operaciones CRUD y validaciones de marcas.
 */
@DataJpaTest // Anotación para configurar pruebas JPA
@Transactional // Todas las pruebas se ejecutan dentro de una transacción
@Import(BrandService.class) // Importa el servicio a probar
public class BrandServiceTest {

    // Inyección del servicio a probar
    @Autowired
    private BrandService brandService;

    // EntityManager para pruebas, permite interactuar con la base de datos en memoria
    @Autowired
    private TestEntityManager entityManager;

    // Repositorio de marcas (aunque no se usa directamente en estas pruebas)
    @Autowired
    private BrandRepository brandRepository;

    // Factoría para generar objetos de prueba aleatorios
    private PodamFactory factory = new PodamFactoryImpl();

    // Lista para almacenar las marcas de prueba
    private List<BrandEntity> brands = new ArrayList<>();

    /**
     * Metodo ejecutado antes de cada prueba.
     * Limpia la base de datos y crea datos de prueba.
     */
    @BeforeEach
    public void setUp() {
        // Elimina todos los registros existentes
        entityManager.getEntityManager()
                .createQuery("delete from BrandEntity")
                .executeUpdate();

        // Crea 3 marcas de prueba con datos controlados
        for (int i = 0; i < 3; i++) {
            BrandEntity b = factory.manufacturePojo(BrandEntity.class);
            b.setName("Brand " + i); // Nombre predecible
            b.setPrice((double) i * 10 + 1); // Precio calculado
            b.setAvailable(true); // Disponibilidad fija
            entityManager.persist(b); // Persiste en la base de datos
            brands.add(b); // Guarda en la lista para referencia
        }
    }

    /**
     * Prueba la creación exitosa de una marca.
     * @throws IllegalOperationException Si la validación falla
     */
    @Test
    public void testCreateValid() throws IllegalOperationException {
        // Crea una nueva marca con datos válidos
        BrandEntity b = factory.manufacturePojo(BrandEntity.class);
        b.setName("NewBrand");
        b.setPrice(50.0);
        b.setAvailable(false);

        // Ejecuta el metodo bajo prueba
        BrandEntity result = brandService.createBrand(b);

        // Verificaciones
        assertNotNull(result.getId()); // Debe tener ID asignado
        BrandEntity found = entityManager.find(BrandEntity.class, result.getId());
        assertEquals("NewBrand", found.getName()); // Nombre correcto
        assertEquals(50.0, found.getPrice()); // Precio correcto
        assertFalse(found.getAvailable()); // Disponibilidad correcta
    }

    /**
     * Prueba la creación con nombre inválido (vacío).
     */
    @Test
    public void testCreateInvalidName() {
        // Prepara marca con nombre inválido
        BrandEntity b = factory.manufacturePojo(BrandEntity.class);
        b.setName(""); // Nombre vacío - inválido
        b.setPrice(10.0);
        b.setAvailable(true);

        // Verifica que se lance la excepción esperada
        assertThrows(IllegalOperationException.class, () -> brandService.createBrand(b));
    }

    /**
     * Prueba la creación con precio inválido (negativo).
     */
    @Test
    public void testCreateInvalidPrice() {
        // Prepara marca con precio inválido
        BrandEntity b = factory.manufacturePojo(BrandEntity.class);
        b.setName("X");
        b.setPrice(-5.0); // Precio negativo - inválido
        b.setAvailable(true);

        // Verifica que se lance la excepción esperada
        assertThrows(IllegalOperationException.class, () -> brandService.createBrand(b));
    }

    /**
     * Prueba la obtención de todas las marcas.
     */
    @Test
    public void testGetAll() {
        // Ejecuta el metodo bajo prueba
        List<BrandEntity> list = brandService.getBrands();

        // Verifica que se obtengan todas las marcas creadas en setUp()
        assertEquals(brands.size(), list.size());
    }

    /**
     * Prueba la obtención de una marca existente.
     */
    @Test
    public void testGetValid() {
        // Obtiene una marca de prueba
        BrandEntity b = brands.get(0);

        // Ejecuta el metodo bajo prueba
        BrandEntity found = brandService.getBrand(b.getId());

        // Verifica que sea la misma marca
        assertEquals(b.getId(), found.getId());
    }

    /**
     * Prueba la obtención de una marca con ID inválido.
     */
    @Test
    public void testGetInvalid() {
        Long invalidId = 0L; // ID que no existe

        // Verifica que se lance la excepción esperada
        assertThrows(EntityNotFoundException.class, () -> brandService.getBrand(invalidId));
    }

    /**
     * Prueba la actualización exitosa de una marca.
     * @throws IllegalOperationException Si la validación falla
     */
    @Test
    public void testUpdateValid() throws IllegalOperationException {
        // Obtiene una marca existente
        BrandEntity b = brands.get(1);

        // Prepara datos de actualización
        BrandEntity update = factory.manufacturePojo(BrandEntity.class);
        update.setName("Updated");
        update.setPrice(99.0);
        update.setAvailable(false);

        // Ejecuta la actualización
        brandService.updateBrand(b.getId(), update);

        // Verifica los cambios en la base de datos
        BrandEntity verified = entityManager.find(BrandEntity.class, b.getId());
        assertEquals("Updated", verified.getName());
        assertEquals(99.0, verified.getPrice());
        assertFalse(verified.getAvailable());
    }

    /**
     * Prueba la actualización con ID inválido.
     */
    @Test
    public void testUpdateInvalidId() {
        Long invalidId = 0L; // ID que no existe
        BrandEntity fake = factory.manufacturePojo(BrandEntity.class); // Datos fake

        // Verifica que se lance la excepción esperada
        assertThrows(EntityNotFoundException.class, () -> brandService.updateBrand(invalidId, fake));
    }

    /**
     * Prueba la eliminación exitosa de una marca.
     */
    @Test
    public void testDeleteValid() {
        // Obtiene una marca existente
        BrandEntity b = brands.get(2);

        // Ejecuta la eliminación
        brandService.deleteBrand(b.getId());

        // Verifica que ya no exista en la base de datos
        assertNull(entityManager.find(BrandEntity.class, b.getId()));
    }

    /**
     * Prueba la eliminación con ID inválido.
     */
    @Test
    public void testDeleteInvalid() {
        Long invalidId = 0L; // ID que no existe

        // Verifica que se lance la excepción esperada
        assertThrows(EntityNotFoundException.class, () -> brandService.deleteBrand(invalidId));
    }
}