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
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.udistrital.mdp.beautyathome.entities.BrandEntity;
import co.edu.udistrital.mdp.beautyathome.entities.ExclusiveProductEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.repositories.BrandRepository;
import co.edu.udistrital.mdp.beautyathome.repositories.ExclusiveProductRepository;

/**
 * Clase de pruebas para el servicio ExclusiveProductService.
 * Prueba las operaciones CRUD y validaciones para productos exclusivos.
 */
@DataJpaTest // Configura entorno de prueba JPA con base de datos en memoria
@Transactional // Cada prueba se ejecuta en transacción que se revierte al final
@EntityScan(basePackages = "co.edu.udistrital.mdp.beautyathome.entities") // Escanea entidades
@Import(ExclusiveProductService.class) // Importa el servicio a probar
public class ExclusiveProductServiceTest {

    @Autowired
    private ExclusiveProductService service; // Servicio bajo prueba

    @Autowired
    private TestEntityManager em; // EntityManager para pruebas

    @Autowired
    private BrandRepository brandRepo; // Repositorio de marcas (no usado directamente)

    @Autowired
    private ExclusiveProductRepository productRepo; // Repositorio de productos (no usado directamente)

    private List<ExclusiveProductEntity> products; // Lista de productos de prueba
    private BrandEntity brand; // Marca de prueba

    /**
     * Configuración inicial antes de cada prueba.
     * Limpia la base de datos y crea datos de prueba.
     */
    @BeforeEach
    public void setUp() {
        // Limpia las tablas relacionadas
        em.getEntityManager().createQuery("DELETE FROM ExclusiveProductEntity").executeUpdate();
        em.getEntityManager().createQuery("DELETE FROM BrandEntity").executeUpdate();

        products = new ArrayList<>();

        // Crea y persiste una marca de prueba
        brand = new BrandEntity();
        brand.setName("TestBrand");
        brand.setLogoURL("test.jpg");
        em.persist(brand);

        // Crea 3 productos de prueba asociados a la marca
        for (int i = 0; i < 3; i++) {
            ExclusiveProductEntity p = new ExclusiveProductEntity();
            p.setName("Prod " + i);
            p.setPrice(i * 5.0 + 1); // Precio incremental
            p.setAvailable(i % 2 == 0); // Disponibilidad alternada
            p.setCategory("Cat " + i);
            p.setDescription("Desc " + i);
            p.setPhoto("photo" + i + ".jpg");
            p.setBrand(brand); // Asocia a la marca
            em.persist(p);
            products.add(p);
        }

        em.flush(); // Sincroniza con la base de datos
    }

    /**
     * Prueba creación exitosa de producto exclusivo.
     */
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
        assertNotNull(out.getId()); // Verifica ID generado

        // Verifica persistencia en BD
        ExclusiveProductEntity found = em.find(ExclusiveProductEntity.class, out.getId());
        assertEquals("NewProd", found.getName());
        assertEquals(20.0, found.getPrice());
        assertTrue(found.getAvailable());
    }

    /**
     * Prueba creación con nombre inválido (vacío).
     */
    @Test
    public void testCreateInvalidName() {
        ExclusiveProductEntity p = new ExclusiveProductEntity();
        p.setName(""); // Nombre vacío
        p.setPrice(5.0);
        p.setAvailable(true);
        p.setCategory("C");
        p.setDescription("D");
        p.setPhoto("photo.jpg");
        p.setBrand(brand);

        assertThrows(IllegalOperationException.class,
                () -> service.createExclusiveProduct(p));
    }

    /**
     * Prueba creación con precio inválido (negativo).
     */
    @Test
    public void testCreateInvalidPrice() {
        ExclusiveProductEntity p = new ExclusiveProductEntity();
        p.setName("X");
        p.setPrice(-1.0); // Precio negativo
        p.setAvailable(true);
        p.setCategory("C");
        p.setDescription("D");
        p.setPhoto("photo.jpg");
        p.setBrand(brand);

        assertThrows(IllegalOperationException.class,
                () -> service.createExclusiveProduct(p));
    }

    /**
     * Prueba creación con marca nula.
     */
    @Test
    public void testCreateNullBrand() {
        ExclusiveProductEntity p = new ExclusiveProductEntity();
        p.setName("X");
        p.setPrice(1.0);
        p.setAvailable(true);
        p.setCategory("C");
        p.setDescription("D");
        p.setPhoto("photo.jpg");
        p.setBrand(null); // Marca nula

        assertThrows(IllegalOperationException.class,
                () -> service.createExclusiveProduct(p));
    }

    /**
     * Prueba obtención de todos los productos.
     */
    @Test
    public void testGetAll() {
        List<ExclusiveProductEntity> list = service.getAll();
        assertEquals(products.size(), list.size()); // Verifica cantidad
    }

    /**
     * Prueba obtención de producto existente.
     */
    @Test
    public void testGetValid() {
        ExclusiveProductEntity p = products.get(0);
        ExclusiveProductEntity out = service.getOne(p.getId());
        assertEquals(p.getId(), out.getId()); // Verifica coincidencia de ID
    }

    /**
     * Prueba obtención con ID inválido.
     */
    @Test
    public void testGetInvalid() {
        assertThrows(EntityNotFoundException.class,
                () -> service.getOne(9999L)); // ID inexistente
    }

    /**
     * Prueba actualización exitosa.
     */
    @Test
    public void testUpdateValid() throws IllegalOperationException {
        ExclusiveProductEntity p = products.get(1);

        ExclusiveProductEntity upd = new ExclusiveProductEntity();
        upd.setName("UpdatedName");
        upd.setPrice(99.0);
        upd.setAvailable(false);
        upd.setCategory("UpdatedCat");
        upd.setDescription("UpdatedDesc");
        upd.setPhoto("updatedphoto.jpg");
        upd.setBrand(brand);

        service.updateExclusiveProduct(p.getId(), upd);

        // Verifica cambios en BD
        ExclusiveProductEntity ver = em.find(ExclusiveProductEntity.class, p.getId());
        assertEquals("UpdatedName", ver.getName());
        assertEquals(99.0, ver.getPrice());
        assertFalse(ver.getAvailable());
    }

    /**
     * Prueba actualización con ID inválido.
     */
    @Test
    public void testUpdateInvalidId() {
        ExclusiveProductEntity upd = new ExclusiveProductEntity();
        upd.setName("X");
        upd.setPrice(1.0);
        upd.setAvailable(true);
        upd.setCategory("C");
        upd.setDescription("D");
        upd.setPhoto("photo.jpg");
        upd.setBrand(brand);

        assertThrows(EntityNotFoundException.class,
                () -> service.updateExclusiveProduct(9999L, upd));
    }

    /**
     * Prueba eliminación exitosa.
     */
    @Test
    public void testDeleteValid() {
        ExclusiveProductEntity p = products.get(2);
        service.deleteExclusiveProduct(p.getId());
        assertNull(em.find(ExclusiveProductEntity.class, p.getId())); // Verifica eliminación
    }

    /**
     * Prueba eliminación con ID inválido.
     */
    @Test
    public void testDeleteInvalid() {
        assertThrows(EntityNotFoundException.class,
                () -> service.deleteExclusiveProduct(9999L));
    }
}