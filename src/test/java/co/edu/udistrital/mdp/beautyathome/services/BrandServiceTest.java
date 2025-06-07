package co.edu.udistrital.mdp.beautyathome.services;

import static org.junit.jupiter.api.Assertions.*;

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

@DataJpaTest
@Transactional
@Import(BrandService.class)
public class BrandServiceTest {

    @Autowired
    private BrandService brandService;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BrandRepository brandRepository; // Aunque no se usa directamente aquí, se mantiene por si es necesario en el futuro.

    private PodamFactory factory = new PodamFactoryImpl();
    private List<BrandEntity> brands = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        entityManager.getEntityManager()
                .createQuery("delete from BrandEntity")
                .executeUpdate();
        for (int i = 0; i < 3; i++) {
            BrandEntity b = factory.manufacturePojo(BrandEntity.class);
            b.setName("Brand " + i);
            b.setPrice((double) i * 10 + 1);
            b.setAvailable(true);
            entityManager.persist(b);
            brands.add(b);
        }
    }

    @Test
    public void testCreateValid() throws IllegalOperationException {
        BrandEntity b = factory.manufacturePojo(BrandEntity.class);
        b.setName("NewBrand");
        b.setPrice(50.0);
        b.setAvailable(false);

        BrandEntity result = brandService.createBrand(b);
        assertNotNull(result.getId());
        BrandEntity found = entityManager.find(BrandEntity.class, result.getId());
        assertEquals("NewBrand", found.getName());
        assertEquals(50.0, found.getPrice());
        assertFalse(found.getAvailable());
    }

    @Test
    void testCreateInvalidName() {
        // Preparamos el objeto BrandEntity por separado
        BrandEntity b = factory.manufacturePojo(BrandEntity.class);
        b.setName(""); // Nombre inválido
        b.setPrice(10.0);
        b.setAvailable(true);

        // La invocación del método que se espera que lance la excepción es única aquí
        assertThrows(IllegalOperationException.class, () -> brandService.createBrand(b));
    }

    @Test
    void testCreateInvalidPrice() {
        // Preparamos el objeto BrandEntity por separado
        BrandEntity b = factory.manufacturePojo(BrandEntity.class);
        b.setName("X");
        b.setPrice(-5.0); // Precio inválido
        b.setAvailable(true);

        // La invocación del método que se espera que lance la excepción es única aquí
        assertThrows(IllegalOperationException.class, () -> brandService.createBrand(b));
    }

    @Test
    public void testGetAll() {
        List<BrandEntity> list = brandService.getBrands();
        assertEquals(brands.size(), list.size());
    }

    @Test
    public void testGetValid() {
        BrandEntity b = brands.get(0);
        BrandEntity found = brandService.getBrand(b.getId());
        assertEquals(b.getId(), found.getId());
    }

    @Test
    public void testGetInvalid() {
        Long invalidId = 0L; // ID inválido
        // La invocación del método que se espera que lance la excepción es única aquí
        assertThrows(EntityNotFoundException.class, () -> brandService.getBrand(invalidId));
    }

    @Test
    public void testUpdateValid() throws IllegalOperationException {
        BrandEntity b = brands.get(1);
        BrandEntity update = factory.manufacturePojo(BrandEntity.class);
        update.setName("Updated");
        update.setPrice(99.0);
        update.setAvailable(false);

// BrandEntity out = brandService.updateBrand(b.getId(), update); // Se elimina esta línea
        brandService.updateBrand(b.getId(), update); // Se mantiene la invocación si tiene efectos secundarios importantes
        BrandEntity verified = entityManager.find(BrandEntity.class, b.getId());
        assertEquals("Updated", verified.getName());
        assertEquals(99.0, verified.getPrice());
        assertFalse(verified.getAvailable());
    }

    @Test
    public void testUpdateInvalidId() {
        Long invalidId = 0L; // ID inválido
        BrandEntity fake = factory.manufacturePojo(BrandEntity.class); // Objeto fake separado

        // La invocación del método que se espera que lance la excepción es única aquí
        assertThrows(EntityNotFoundException.class, () -> brandService.updateBrand(invalidId, fake));
    }

    @Test
    public void testDeleteValid() {
        BrandEntity b = brands.get(2);
        brandService.deleteBrand(b.getId());
        assertNull(entityManager.find(BrandEntity.class, b.getId()));
    }

    @Test
    public void testDeleteInvalid() {
        Long invalidId = 0L; // ID inválido
        // La invocación del método que se espera que lance la excepción es única aquí
        assertThrows(EntityNotFoundException.class, () -> brandService.deleteBrand(invalidId));
    }
}