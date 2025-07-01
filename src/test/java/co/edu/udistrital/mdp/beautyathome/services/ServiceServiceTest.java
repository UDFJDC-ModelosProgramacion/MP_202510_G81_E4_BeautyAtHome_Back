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
import co.edu.udistrital.mdp.beautyathome.entities.ServiceRecordEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
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
    private ArrayList<ServiceRecordEntity> serviceRecordList = new ArrayList<>();
  
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
        entityManager.getEntityManager().createQuery("delete from ServiceRecordEntity");

    }
    private void insertData() {
    
        for (int i = 0; i < 3; i++){
            BrandEntity brandEntity = factory.manufacturePojo(BrandEntity.class);
            entityManager.persist(brandEntity);
            brandList.add(brandEntity);
        }
        for (int i = 0; i < 3; i++) {
            ServiceRecordEntity serviceRecordEntity = factory.manufacturePojo(ServiceRecordEntity.class);
            entityManager.persist(serviceRecordEntity);
            serviceRecordList.add(serviceRecordEntity);
        }
       for (int i = 0; i < 3; i++) {
            ProfessionalEntity professionalEntity = factory.manufacturePojo(ProfessionalEntity.class);
            entityManager.persist(professionalEntity);
            professionalList.add(professionalEntity);
       }
        for (int i = 0; i < 3; i++) {
            ServiceEntity serviceEntity = factory.manufacturePojo(ServiceEntity.class);
            serviceEntity.setProfessional(professionalList.get(i));
            serviceEntity.setBrand(brandList.get(i));
            serviceEntity.setRecords(serviceRecordList);
            entityManager.persist(serviceEntity);
            serviceList.add(serviceEntity);
        }
    }


    /**
     * Verifica que se pueda crear un servicio correctamente.
     */
    @Test
    public void testCreateService() throws IllegalOperationException {
        ServiceEntity newEntity = factory.manufacturePojo(ServiceEntity.class);
        
        newEntity.setProfessional(professionalList.get(0));

        newEntity.setDescription("Test Description");
        newEntity.setName("Test Service");
        newEntity.setPrice(100.0);
        newEntity.setBrand(brandList.get(0));
        newEntity.setRecords(serviceRecordList);

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
        assertThrows(IllegalOperationException.class, () -> {
            ServiceEntity newEntity = factory.manufacturePojo(ServiceEntity.class);
            newEntity.setProfessional(null);
            newEntity.setDescription("Test Description");
            newEntity.setName("Test Service");
            newEntity.setPrice(100.0);
            newEntity.setBrand(brandList.get(0));
            newEntity.setRecords(serviceRecordList);

            serviceService.createService(newEntity);
        });
    }

    @Test
    public void testCreateServiceWithNullBrand() {
        assertThrows(IllegalOperationException.class, () -> {
            ServiceEntity newEntity = factory.manufacturePojo(ServiceEntity.class);
            newEntity.setProfessional(professionalList.get(0));
            newEntity.setDescription("Test Description");
            newEntity.setName("Test Service");
            newEntity.setPrice(100.0);
            newEntity.setBrand(null);
            newEntity.setRecords(serviceRecordList);

            serviceService.createService(newEntity);
        });
    }

    @Test
    public void testCreateServiceWithEmptyName() {
        assertThrows(IllegalOperationException.class, () -> {
            ServiceEntity newEntity = factory.manufacturePojo(ServiceEntity.class);
            newEntity.setProfessional(professionalList.get(0));
            newEntity.setDescription("Test Description");
            newEntity.setName("");
            newEntity.setPrice(100.0);
            newEntity.setBrand(brandList.get(0));
            newEntity.setRecords(serviceRecordList);

            serviceService.createService(newEntity);
        });
    }

    @Test
    public void testCreateServiceWithEmptyDescription() {
        assertThrows(IllegalOperationException.class, () -> {
            ServiceEntity newEntity = factory.manufacturePojo(ServiceEntity.class);
            newEntity.setProfessional(professionalList.get(0));
            newEntity.setDescription("");
            newEntity.setName("Test Service");
            newEntity.setPrice(100.0);
            newEntity.setBrand(brandList.get(0));
            newEntity.setRecords(serviceRecordList);

            serviceService.createService(newEntity);
        });
    }

    @Test
    public void testCreateServiceWithNullName() {
        assertThrows(IllegalOperationException.class, () -> {
            ServiceEntity newEntity = factory.manufacturePojo(ServiceEntity.class);
            newEntity.setProfessional(professionalList.get(0));
            newEntity.setDescription("Test Description");
            newEntity.setName(null);
            newEntity.setPrice(100.0);
            newEntity.setBrand(brandList.get(0));
            newEntity.setRecords(serviceRecordList);

            serviceService.createService(newEntity);
        });
    }
    @Test
    public void testCreateServiceWithNullDescription() {
        assertThrows(IllegalOperationException.class, () -> {
            ServiceEntity newEntity = factory.manufacturePojo(ServiceEntity.class);
            newEntity.setProfessional(professionalList.get(0));
            newEntity.setDescription(null);
            newEntity.setName("Test Service");
            newEntity.setPrice(100.0);
            newEntity.setBrand(brandList.get(0));

            serviceService.createService(newEntity);
        });
    }
    /**
     * Verifica que se obtenga una lista vacía si no hay servicios.
     */
    @Test
    public void testCreateServiceWithNullRecords() {
        assertThrows(IllegalOperationException.class, () -> {
            ServiceEntity newEntity = factory.manufacturePojo(ServiceEntity.class);
            newEntity.setProfessional(professionalList.get(0));
            newEntity.setDescription("Test Description");
            newEntity.setName("Test Service");
            newEntity.setPrice(100.0);
            newEntity.setBrand(brandList.get(0));
            newEntity.setRecords(null);

            serviceService.createService(newEntity);
        });
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
    public void testGetValidService() throws EntityNotFoundException {
        ServiceEntity entity = serviceList.get(0);
        ServiceEntity result = serviceService.getService(entity.getId());
        assertNotNull(result);
        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getName(), result.getName());
        assertEquals(entity.getDescription(), result.getDescription());
        assertEquals(entity.getPrice(), result.getPrice());
        assertEquals(entity.getProfessional().getId(), result.getProfessional().getId());
        assertEquals(entity.getBrand().getId(), result.getBrand().getId());
        assertEquals(entity.getRecords().size(), result.getRecords().size());
    }

    @Test
    public void testGetInvalidService() {
        assertThrows(EntityNotFoundException.class,() -> {
            serviceService.getService(0L);
        });
    }

    /**
     * Verifica que se pueda actualizar un servicio correctamente.
     * @throws EntityNotFoundException 
     */
    @Test
    public void testUpdateService() throws IllegalOperationException, EntityNotFoundException {
        ServiceEntity serviceEntity = serviceList.get(0);
        ServiceEntity updatedServiceEntity = factory.manufacturePojo(ServiceEntity.class);
        updatedServiceEntity.setId(serviceEntity.getId());
        updatedServiceEntity.setProfessional(professionalList.get(0));
        updatedServiceEntity.setDescription("Updated Description");
        updatedServiceEntity.setName("Updated Service");
        updatedServiceEntity.setPrice(150.0);
        updatedServiceEntity.setBrand(brandList.get(0));
        updatedServiceEntity.setRecords(serviceRecordList);


        serviceService.updateService(serviceEntity.getId(),updatedServiceEntity);

        ServiceEntity response = entityManager.find(ServiceEntity.class, serviceEntity.getId());
        
        assertEquals(updatedServiceEntity.getId(), response.getId());
        assertEquals(updatedServiceEntity.getBrand(), response.getBrand());
        assertEquals(updatedServiceEntity.getProfessional(), response.getProfessional());
        assertEquals(updatedServiceEntity.getName(), response.getName());
        assertEquals(updatedServiceEntity.getDescription(), response.getDescription());
        assertEquals(updatedServiceEntity.getPrice(),  response.getPrice());
        assertEquals(updatedServiceEntity.getRecords().size(), response.getRecords().size());
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
        assertThrows(IllegalOperationException.class, ()->{
            ServiceEntity serviceEntity = serviceList.get(0);
            ServiceEntity updatedService = factory.manufacturePojo(ServiceEntity.class);
            updatedService.setProfessional(null);
            updatedService.setDescription("Updated Description");
            updatedService.setName("Updated Service");
            updatedService.setPrice(150.0);
            updatedService.setBrand(brandList.get(0));
            updatedService.setId(serviceEntity.getId());
            updatedService.setRecords(serviceRecordList);
            serviceService.updateService(serviceEntity.getId(), updatedService);
        });
    }

    @Test
    public void testUpdateInvalidServiceWithNullBrand() {
        assertThrows(IllegalOperationException.class, () -> {
            ServiceEntity serviceEntity = serviceList.get(0);
            ServiceEntity updatedService = factory.manufacturePojo(ServiceEntity.class);
            updatedService.setProfessional(professionalList.get(0));
            updatedService.setDescription("Updated Description");
            updatedService.setName("Updated Service");
            updatedService.setPrice(150.0);
            updatedService.setRecords(serviceRecordList);
            updatedService.setBrand(null);
            updatedService.setId(serviceEntity.getId());
            serviceService.updateService(serviceEntity.getId(), updatedService);
        });
    }
    @Test
    public void testUpdateInvalidServiceWithEmptyName() {
        assertThrows(IllegalOperationException.class, () -> {
            ServiceEntity serviceEntity = serviceList.get(0);
            ServiceEntity updatedService = factory.manufacturePojo(ServiceEntity.class);
            updatedService.setProfessional(professionalList.get(0));
            updatedService.setDescription("Updated Description");
            updatedService.setName("");
            updatedService.setPrice(150.0);
            updatedService.setBrand(brandList.get(0));
            updatedService.setId(serviceEntity.getId());
            updatedService.setRecords(serviceRecordList);
            serviceService.updateService(serviceEntity.getId(), updatedService);
        });
    }

    @Test
    public void testUpdateInvalidServiceWithEmptyDescription() {
        assertThrows(IllegalOperationException.class, () -> {
            ServiceEntity serviceEntity = serviceList.get(0);
            ServiceEntity updatedService = factory.manufacturePojo(ServiceEntity.class);
            updatedService.setProfessional(professionalList.get(0));
            updatedService.setDescription("");
            updatedService.setName("Updated Service");
            updatedService.setPrice(150.0);
            updatedService.setBrand(brandList.get(0));
            updatedService.setId(serviceEntity.getId());
            updatedService.setRecords(serviceRecordList);
            serviceService.updateService(serviceEntity.getId(), updatedService);
        });
    }
    
    @Test
    public void testUpdateInvalidServiceWithNullName() {
        assertThrows(IllegalOperationException.class, () -> {
            ServiceEntity serviceEntity = serviceList.get(0);
            ServiceEntity updatedService = factory.manufacturePojo(ServiceEntity.class);
            updatedService.setProfessional(professionalList.get(0));
            updatedService.setDescription("Updated Description");
            updatedService.setName(null);
            updatedService.setPrice(150.0);
            updatedService.setBrand(brandList.get(0));
            updatedService.setId(serviceEntity.getId());
            updatedService.setRecords(serviceRecordList);
            serviceService.updateService(serviceEntity.getId(), updatedService);
        });
    }

    @Test
    public void testUpdateInvalidServiceWithNullDescription() {
        assertThrows(IllegalOperationException.class, () -> {
            ServiceEntity serviceEntity = serviceList.get(0);
            ServiceEntity updatedService = factory.manufacturePojo(ServiceEntity.class);
            updatedService.setProfessional(professionalList.get(0));
            updatedService.setDescription(null);
            updatedService.setName("Updated Service");
            updatedService.setPrice(150.0);
            updatedService.setBrand(brandList.get(0));
            updatedService.setId(serviceEntity.getId());
            updatedService.setRecords(serviceRecordList);
            serviceService.updateService(serviceEntity.getId(), updatedService);
        });
    }

    @Test
    public void testUpdateInvalidServiceWithNullRecords() {
        assertThrows(IllegalOperationException.class, () -> {
            ServiceEntity serviceEntity = serviceList.get(0);
            ServiceEntity updatedService = factory.manufacturePojo(ServiceEntity.class);
            updatedService.setProfessional(professionalList.get(0));
            updatedService.setDescription("Updated Description");
            updatedService.setName("Updated Service");
            updatedService.setPrice(150.0);
            updatedService.setBrand(brandList.get(0));
            updatedService.setId(serviceEntity.getId());
            updatedService.setRecords(null);
            serviceService.updateService(serviceEntity.getId(), updatedService);
        });
    }

    /**
     * Verifica que se pueda eliminar un servicio correctamente.
     * @throws EntityNotFoundException 
     */
    @Test
    public void testDeleteValidService() throws EntityNotFoundException, IllegalOperationException {
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
