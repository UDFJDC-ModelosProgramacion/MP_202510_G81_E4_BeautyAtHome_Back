package co.edu.udistrital.mdp.beautyathome.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.udistrital.mdp.beautyathome.entities.BrandEntity;
import co.edu.udistrital.mdp.beautyathome.entities.ExclusiveProductEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.repositories.BrandRepository;
import co.edu.udistrital.mdp.beautyathome.repositories.ExclusiveProductRepository;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@EntityScan(basePackages = "co.edu.udistrital.mdp.beautyathome.entities")
@EnableJpaRepositories(basePackages = "co.edu.udistrital.mdp.beautyathome.repositories")
@Import(ExclusiveProductService.class)
public class ExclusiveProductServiceTest {

    @Autowired
    private ExclusiveProductService service;

    @Autowired
    private TestEntityManager em;

    @Autowired
    private BrandRepository brandRepo;

    @Autowired
    private ExclusiveProductRepository repo;

    private PodamFactory factory = new PodamFactoryImpl();
    private List<ExclusiveProductEntity> products = new ArrayList<>();
    private BrandEntity brand;

    @BeforeEach
    public void setUp() {
        // Limpia las tablas
        em.getEntityManager().createQuery("DELETE FROM ExclusiveProductEntity").executeUpdate();
        em.getEntityManager().createQuery("DELETE FROM BrandEntity").executeUpdate();

        // Crea y persiste un Brand
        brand = new BrandEntity();
        brand.setName("TestBrand");
        brand.setPrice(10.0);
        brand.setAvailable(true);
        brand.setCategory("TestCategory");
        brand.setDescription("TestDescription");
        brand.setPhotograph("test.jpg");
        brand.setProduct("TestProduct");
        em.persist(brand);

        // Crea 3 ExclusiveProductEntity manualmente
        for (int i = 0; i < 3; i++) {
            ExclusiveProductEntity p = new ExclusiveProductEntity();
            p.setName("Prod " + i);
            p.setPrice(i * 5.0 + 1);
            p.setAvailable(i % 2 == 0);
            p.setCategory("Cat " + i);
            p.setDescription("Desc " + i);
            p.setPhoto("photo" + i + ".jpg");
            p.setBrand(brand);
            em.persist(p);
            products.add(p);
        }
    }

    @Test
    public void testCreateValid() throws IllegalOperationException {
        ExclusiveProductEntity p = new ExclusiveProductEntity();
        p.setName("NewProd");
        p.setPrice(20.0);
        p.setAvailable(true);
        p.setCategory("NewCat");
        p.setDescription("NewDesc");
        p.setPhoto("newphoto.jpg");
        p.setBrand(brand);

        ExclusiveProductEntity out = service.createExclusiveProduct(p);
        assertNotNull(out.getId());
        ExclusiveProductEntity found = em.find(ExclusiveProductEntity.class, out.getId());
        assertEquals("NewProd", found.getName());
        assertEquals(20.0, found.getPrice());
        assertTrue(found.getAvailable());
    }

    @Test
    public void testCreateInvalidName() {
        assertThrows(IllegalOperationException.class, () -> {
            ExclusiveProductEntity p = new ExclusiveProductEntity();
            p.setName("");
            p.setPrice(5.0);
            p.setAvailable(true);
            p.setCategory("C");
            p.setDescription("D");
            p.setPhoto("photo.jpg");
            p.setBrand(brand);
            service.createExclusiveProduct(p);
        });
    }

    @Test
    public void testCreateInvalidPrice() {
        assertThrows(IllegalOperationException.class, () -> {
            ExclusiveProductEntity p = new ExclusiveProductEntity();
            p.setName("X");
            p.setPrice(-1.0);
            p.setAvailable(true);
            p.setCategory("C");
            p.setDescription("D");
            p.setPhoto("photo.jpg");
            p.setBrand(brand);
            service.createExclusiveProduct(p);
        });
    }

    @Test
    public void testCreateNullBrand() {
        assertThrows(IllegalOperationException.class, () -> {
            ExclusiveProductEntity p = new ExclusiveProductEntity();
            p.setName("X");
            p.setPrice(1.0);
            p.setAvailable(true);
            p.setCategory("C");
            p.setDescription("D");
            p.setPhoto("photo.jpg");
            p.setBrand(null);
            service.createExclusiveProduct(p);
        });
    }

    @Test
    public void testGetAll() {
        List<ExclusiveProductEntity> list = service.getAll();
        assertEquals(products.size(), list.size());
    }

    @Test
    public void testGetValid() {
        ExclusiveProductEntity p = products.get(0);
        ExclusiveProductEntity out = service.getOne(p.getId());
        assertEquals(p.getId(), out.getId());
    }

    @Test
    public void testGetInvalid() {
        assertThrows(EntityNotFoundException.class, () -> service.getOne(0L));
    }

    @Test
    public void testUpdateValid() throws IllegalOperationException {
        ExclusiveProductEntity p = products.get(1);
        ExclusiveProductEntity upd = new ExclusiveProductEntity();
        upd.setName("UpdName");
        upd.setPrice(99.0);
        upd.setAvailable(false);
        upd.setCategory("UpdCat");
        upd.setDescription("UpdDesc");
        upd.setPhoto("updphoto.jpg");
        upd.setBrand(brand);

        ExclusiveProductEntity out = service.updateExclusiveProduct(p.getId(), upd);
        ExclusiveProductEntity ver = em.find(ExclusiveProductEntity.class, p.getId());
        assertEquals("UpdName", ver.getName());
        assertEquals(99.0, ver.getPrice());
        assertFalse(ver.getAvailable());
    }

    @Test
    public void testUpdateInvalidId() {
        assertThrows(EntityNotFoundException.class, () -> {
            ExclusiveProductEntity fake = new ExclusiveProductEntity();
            fake.setName("X");
            fake.setPrice(1.0);
            fake.setAvailable(true);
            fake.setCategory("C");
            fake.setDescription("D");
            fake.setPhoto("photo.jpg");
            fake.setBrand(brand);
            service.updateExclusiveProduct(0L, fake);
        });
    }

    @Test
    public void testDeleteValid() {
        ExclusiveProductEntity p = products.get(2);
        service.deleteExclusiveProduct(p.getId());
        assertNull(em.find(ExclusiveProductEntity.class, p.getId()));
    }

    @Test
    public void testDeleteInvalid() {
        assertThrows(EntityNotFoundException.class, () -> service.deleteExclusiveProduct(0L));
    }
}
