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
import co.edu.udistrital.mdp.beautyathome.repositories.BrandRepository; // Se mantiene por si se necesita para algo, aunque no se usa en los tests directos
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Clase de pruebas unitarias para el servicio BrandService.
 * Prueba las operaciones CRUD y validaciones de marcas.
 * Ajustada para los cambios en BrandEntity (sin campos de precio/disponibilidad).
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

    // Repositorio de marcas (se mantiene si hay alguna razón para testearlo directamente,
    // aunque para los tests del servicio, el servicio ya usa su propio repo inyectado)
    @Autowired(required = false) // No es estrictamente necesario para este test de servicio
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
            b.setLogoURL("http://logo.com/brand" + i + ".png"); // Añadimos logoURL ya que es un campo de BrandEntity
            // Eliminados los campos setPrice y setAvailable ya que no son parte de BrandEntity
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
        b.setLogoURL("http://newbrand.com/logo.png");
        // Eliminados los campos setPrice y setAvailable ya que no son parte de BrandEntity

        // Ejecuta el metodo bajo prueba
        BrandEntity result = brandService.createBrand(b);

        // Verificaciones
        assertNotNull(result.getId()); // Debe tener ID asignado
        BrandEntity found = entityManager.find(BrandEntity.class, result.getId());
        assertEquals("NewBrand", found.getName()); // Nombre correcto
        assertEquals("http://newbrand.com/logo.png", found.getLogoURL()); // LogoURL correcto
        // Eliminadas las aserciones de price y available
    }

    /**
     * Prueba la creación con nombre inválido (vacío o nulo).
     */
    @Test
    public void testCreateInvalidName() {
        // Prepara marca con nombre inválido (vacío)
        BrandEntity bEmptyName = factory.manufacturePojo(BrandEntity.class);
        bEmptyName.setName(""); // Nombre vacío - inválido
        bEmptyName.setLogoURL("http://test.com/logo.png");

        // Verifica que se lance la excepción esperada para nombre vacío
        assertThrows(IllegalOperationException.class, () -> brandService.createBrand(bEmptyName),
                "Se esperaba IllegalOperationException para nombre vacío.");

        // Prepara marca con nombre inválido (nulo)
        BrandEntity bNullName = factory.manufacturePojo(BrandEntity.class);
        bNullName.setName(null); // Nombre nulo - inválido
        bNullName.setLogoURL("http://test.com/logo.png");

        // Verifica que se lance la excepción esperada para nombre nulo
        assertThrows(IllegalOperationException.class, () -> brandService.createBrand(bNullName),
                "Se esperaba IllegalOperationException para nombre nulo.");
    }

    /**
     * **TEST ELIMINADO:**
     * Prueba la creación con precio inválido (negativo). Este test ya no es aplicable
     * porque BrandEntity ya no tiene el campo 'price'.
     *
     * Si en el futuro necesitas validar precios, esto debe hacerse en ExclusiveProductServiceTest.
     */
    // @Test
    // public void testCreateInvalidPrice() { ... } // Comentado o eliminado

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
        assertNotNull(found);
        assertEquals(b.getId(), found.getId());
        assertEquals(b.getName(), found.getName());
        assertEquals(b.getLogoURL(), found.getLogoURL());
    }

    /**
     * Prueba la obtención de una marca con ID inválido.
     */
    @Test
    public void testGetInvalid() {
        Long invalidId = -1L; // ID que no existe y es improbable que exista

        // Verifica que se lance la excepción esperada
        assertThrows(EntityNotFoundException.class, () -> brandService.getBrand(invalidId));
    }

    /**
     * Prueba la actualización exitosa de una marca.
     * @throws IllegalOperationException Si la validación falla
     * @throws EntityNotFoundException Si la marca a actualizar no se encuentra
     */
    @Test
    public void testUpdateValid() throws IllegalOperationException, EntityNotFoundException {
        // Obtiene una marca existente
        BrandEntity b = brands.get(1);

        // Prepara datos de actualización
        BrandEntity update = factory.manufacturePojo(BrandEntity.class);
        update.setName("UpdatedBrandName");
        update.setLogoURL("http://updated.com/logo.png");
        // Eliminados los campos setPrice y setAvailable ya que no son parte de BrandEntity

        // Ejecuta la actualización
        brandService.updateBrand(b.getId(), update);

        // Verifica los cambios en la base de datos
        BrandEntity verified = entityManager.find(BrandEntity.class, b.getId());
        assertEquals("UpdatedBrandName", verified.getName());
        assertEquals("http://updated.com/logo.png", verified.getLogoURL());
        // Eliminadas las aserciones de price y available
    }

    /**
     * Prueba la actualización con ID inválido.
     */
    @Test
    public void testUpdateInvalidId() {
        Long invalidId = -1L; // ID que no existe
        BrandEntity fake = factory.manufacturePojo(BrandEntity.class); // Datos fake (el contenido no importa tanto aquí)
        fake.setName("FakeBrand"); // Necesita un nombre válido para la validación del servicio
        fake.setLogoURL("http://fake.com/logo.png");


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
        Long invalidId = -1L; // ID que no existe

        // Verifica que se lance la excepción esperada
        assertThrows(EntityNotFoundException.class, () -> brandService.deleteBrand(invalidId));
    }
}