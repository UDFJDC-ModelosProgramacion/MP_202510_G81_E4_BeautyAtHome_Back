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

import co.edu.udistrital.mdp.beautyathome.entities.BrandEntity;
import co.edu.udistrital.mdp.beautyathome.entities.ProfessionalEntity;
import co.edu.udistrital.mdp.beautyathome.entities.ServiceEntity;

import co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException;

import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(ServiceService.class)
public class ServiceServiceTest {
    @Autowired
    private ServiceService serviceService;
    @Autowired
    private TestEntityManager entityManager;
    
    private PodamFactory factory = new PodamFactoryImpl();
    private List<ServiceEntity> serviceList = new ArrayList<>();
    private List<ProfessionalEntity> professionalList = new ArrayList<>();
    private List<BrandEntity> brandList = new ArrayList<>();
  
    /*
     * Configuración inicial de la prueba.
     */
    
    @BeforeEach
    public void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from ServiceEntity");
        entityManager.getEntityManager().createQuery("delete from ProfessionalEntity");
        entityManager.getEntityManager().createQuery("delete from BrandEntity");

    }
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ServiceEntity ServiceEntity = factory.manufacturePojo(ServiceEntity.class);
            entityManager.persist(ServiceEntity);
            serviceList.add(ServiceEntity);
        }
        for (int i = 0; i < 3; i++) {
            ServiceEntity serviceEntity = factory.manufacturePojo(ServiceEntity.class);
            entityManager.persist(serviceEntity);
            serviceList.add(serviceEntity);
        }
        for (int i = 0; i < 3; i++){
            BrandEntity brandEntity = factory.manufacturePojo(BrandEntity.class);
            entityManager.persist(brandEntity);
            brandList.add(brandEntity);
        }
        ProfessionalEntity professionalEntity = factory.manufacturePojo(ProfessionalEntity.class);
        entityManager.persist(professionalEntity);
        professionalEntity.getServices().addAll(serviceList);
        professionalList.add(professionalEntity);
    }


    /**
     * Verifica que se pueda crear un servicio correctamente.
     */
    @Test
    public void testCreateService() throws EntityNotFoundException, IllegalArgumentException {
        ServiceEntity newEntity = factory.manufacturePojo(ServiceEntity.class);
        
        newEntity.setProfessional(professionalList.get(0));

        newEntity.setDescription("Test Description");
        newEntity.setName("Test Service");
        newEntity.setPrice(100.0);
        newEntity.setBrand(brandList.get(0));

        ServiceEntity result = serviceService.createService(newEntity);
        assertNotNull(result);
        ServiceEntity entity = entityManager.find(ServiceEntity.class, result.getId());

        assertEquals(newEntity.getName(), entity.getName());
        assertEquals(newEntity.getDescription(), entity.getDescription());
        assertEquals(newEntity.getPrice(), entity.getPrice());
        assertEquals(newEntity.getProfessional().getId(), entity.getProfessional().getId());
        assertEquals(newEntity.getBrand().getId(), entity.getBrand().getId());
    }

    @Test
    public void testCreateServiceWithNullProfessional() {
        ServiceEntity newEntity = factory.manufacturePojo(ServiceEntity.class);
        newEntity.setProfessional(null);
        newEntity.setDescription("Test Description");
        newEntity.setName("Test Service");
        newEntity.setPrice(100.0);
        newEntity.setBrand(brandList.get(0));

        
        serviceService.createService(newEntity);
    }

    @Test
    public void testCreateServiceWithNullBrand() {
        ServiceEntity newEntity = factory.manufacturePojo(ServiceEntity.class);
        newEntity.setProfessional(professionalList.get(0));
        newEntity.setDescription("Test Description");
        newEntity.setName("Test Service");
        newEntity.setPrice(100.0);
        newEntity.setBrand(null);

        serviceService.createService(newEntity);
    }

    @Test
    public void testCreateServiceWithEmptyName() {
        ServiceEntity newEntity = factory.manufacturePojo(ServiceEntity.class);
        newEntity.setProfessional(professionalList.get(0));
        newEntity.setDescription("Test Description");
        newEntity.setName("");
        newEntity.setPrice(100.0);
        newEntity.setBrand(brandList.get(0));
        
        serviceService.createService(newEntity);
    }

    @Test
    public void testCreateServiceWithEmptyDescription() {
        ServiceEntity newEntity = factory.manufacturePojo(ServiceEntity.class);
        newEntity.setProfessional(professionalList.get(0));
        newEntity.setDescription("");
        newEntity.setName("Test Service");
        newEntity.setPrice(100.0);
        newEntity.setBrand(brandList.get(0));
        
        serviceService.createService(newEntity);
    }

    @Test
    public void testCreateServiceWithNullName() {
        ServiceEntity newEntity = factory.manufacturePojo(ServiceEntity.class);
        newEntity.setProfessional(professionalList.get(0));
        newEntity.setDescription("Test Description");
        newEntity.setName(null);
        newEntity.setPrice(100.0);
        newEntity.setBrand(brandList.get(0));
        serviceService.createService(newEntity);
    }
    @Test
    public void testCreateServiceWithNullDescription() {
        ServiceEntity newEntity = factory.manufacturePojo(ServiceEntity.class);
        newEntity.setProfessional(professionalList.get(0));
        newEntity.setDescription(null);
        newEntity.setName("Test Service");
        newEntity.setPrice(100.0);
        newEntity.setBrand(brandList.get(0));
        
        serviceService.createService(newEntity);
    }


    /**
     * Verifica que se obtengan todos los servicios correctamente.
     */
    @Test
    public void testGetServices() {
        List<ServiceEntity> list = serviceService.getServices();
        assertEquals(serviceList.size(), list.size());
        for (ServiceEntity entity : list) {
            boolean found = false;
            for (ServiceEntity storedEntity : serviceList) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                    break;
                }
            }
            assertEquals(true, found);
        }
    }

    @Test
    public void testGetValidService() {
        ServiceEntity entity = serviceList.get(0);
        ServiceEntity result = serviceService.getService(entity.getId());
        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getName(), result.getName());
        assertEquals(entity.getDescription(), result.getDescription());
        assertEquals(entity.getPrice(), result.getPrice());
    }

    @Test
    public void testGetInvalidService() {
        assertThrows(EntityNotFoundException.class,() -> {
            serviceService.getService(0L);
        });
    }

    /**
     * Verifica que se pueda actualizar un servicio correctamente.
     */
    @Test
    public void testUpdateService(){
        ServiceEntity serviceEntity = serviceList.get(0);
        ServiceEntity updatedServiceEntity = factory.manufacturePojo(ServiceEntity.class);
        updatedServiceEntity.setId(serviceEntity.getId());
        updatedServiceEntity.setProfessional(professionalList.get(0));
        updatedServiceEntity.setDescription("Updated Description");
        updatedServiceEntity.setName("Updated Service");
        updatedServiceEntity.setPrice(150.0);
        updatedServiceEntity.setBrand(brandList.get(0));


        serviceService.updateService(serviceEntity.getId(),updatedServiceEntity);

        ServiceEntity response = entityManager.find(ServiceEntity.class, serviceEntity.getId());
        
        assertEquals(updatedServiceEntity.getId(), response.getId());
        assertEquals(updatedServiceEntity.getBrand(), response.getBrand());
        assertEquals(updatedServiceEntity.getProfessional(), response.getProfessional());
        assertEquals(updatedServiceEntity.getName(), response.getName());
        assertEquals(updatedServiceEntity.getDescription(), response.getDescription());
        assertEquals(updatedServiceEntity.getPrice(),  response.getPrice());
    }

    @Test
    public void testUpdateInvalidService(){
        assertThrows(EntityNotFoundException.class, () -> {
            ServiceEntity updatedService = factory.manufacturePojo(ServiceEntity.class);
            updatedService.setId(0L);
            serviceService.updateService(0L, updatedService);
        });
    }

    @Test
    public void testUpdateInvalidServiceWithNullProfessional(){
        assertThrows(EntityNotFoundException.class, ()->{
            ServiceEntity serviceEntity = serviceList.get(0);
            ServiceEntity updatedService = factory.manufacturePojo(ServiceEntity.class);
            updatedService.setProfessional(null);
            updatedService.setDescription("Updated Description");
            updatedService.setName("Updated Service");
            updatedService.setPrice(150.0);
            updatedService.setBrand(brandList.get(0));
            updatedService.setId(serviceEntity.getId());
            serviceService.updateService(serviceEntity.getId(), updatedService);
        });
    }

    @Test
    public void testUpdateInvalidServiceWithNullBrand() {
        assertThrows(EntityNotFoundException.class, () -> {
            ServiceEntity serviceEntity = serviceList.get(0);
            ServiceEntity updatedService = factory.manufacturePojo(ServiceEntity.class);
            updatedService.setProfessional(professionalList.get(0));
            updatedService.setDescription("Updated Description");
            updatedService.setName("Updated Service");
            updatedService.setPrice(150.0);

            updatedService.setBrand(null);
            updatedService.setId(serviceEntity.getId());
            serviceService.updateService(serviceEntity.getId(), updatedService);
        });
    }
    @Test
    public void testUpdateInvalidServiceWithEmptyName() {
        assertThrows(EntityNotFoundException.class, () -> {
            ServiceEntity serviceEntity = serviceList.get(0);
            ServiceEntity updatedService = factory.manufacturePojo(ServiceEntity.class);
            updatedService.setProfessional(professionalList.get(0));
            updatedService.setDescription("Updated Description");
            updatedService.setName("");
            updatedService.setPrice(150.0);
            updatedService.setBrand(brandList.get(0));
            updatedService.setId(serviceEntity.getId());
            serviceService.updateService(serviceEntity.getId(), updatedService);
        });
    }

    @Test
    public void testUpdateInvalidServiceWithEmptyDescription() {
        assertThrows(EntityNotFoundException.class, () -> {
            ServiceEntity serviceEntity = serviceList.get(0);
            ServiceEntity updatedService = factory.manufacturePojo(ServiceEntity.class);
            updatedService.setProfessional(professionalList.get(0));
            updatedService.setDescription("");
            updatedService.setName("Updated Service");
            updatedService.setPrice(150.0);
            updatedService.setBrand(brandList.get(0));
            updatedService.setId(serviceEntity.getId());
            serviceService.updateService(serviceEntity.getId(), updatedService);
        });
    }
    
    @Test
    public void testUpdateInvalidServiceWithNullName() {
        assertThrows(EntityNotFoundException.class, () -> {
            ServiceEntity serviceEntity = serviceList.get(0);
            ServiceEntity updatedService = factory.manufacturePojo(ServiceEntity.class);
            updatedService.setProfessional(professionalList.get(0));
            updatedService.setDescription("Updated Description");
            updatedService.setName(null);
            updatedService.setPrice(150.0);
            updatedService.setBrand(brandList.get(0));
            updatedService.setId(serviceEntity.getId());
            serviceService.updateService(serviceEntity.getId(), updatedService);
        });
    }

    @Test
    public void testUpdateInvalidServiceWithNullDescription() {
        assertThrows(EntityNotFoundException.class, () -> {
            ServiceEntity serviceEntity = serviceList.get(0);
            ServiceEntity updatedService = factory.manufacturePojo(ServiceEntity.class);
            updatedService.setProfessional(professionalList.get(0));
            updatedService.setDescription(null);
            updatedService.setName("Updated Service");
            updatedService.setPrice(150.0);
            updatedService.setBrand(brandList.get(0));
            updatedService.setId(serviceEntity.getId());
            serviceService.updateService(serviceEntity.getId(), updatedService);
        });
    }

    /**
     * Verifica que se pueda eliminar un servicio correctamente.
     */
    @Test
    public void testDeleteValidService() {
        ServiceEntity serviceEntity = serviceList.get(0);
        serviceService.deleteService(serviceEntity.getId());
        ServiceEntity deleted = entityManager.find(ServiceEntity.class, serviceEntity.getId());
        assertNull(deleted);
    }

    @Test
    public void testDeleteInvalidService() {
        assertThrows(EntityNotFoundException.class, () -> {
            serviceService.deleteService(0L);
        });
    }

}
