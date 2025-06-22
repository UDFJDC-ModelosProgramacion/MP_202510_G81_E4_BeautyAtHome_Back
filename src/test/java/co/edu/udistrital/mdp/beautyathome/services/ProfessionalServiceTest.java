package co.edu.udistrital.mdp.beautyathome.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.udistrital.mdp.beautyathome.entities.AgendaEntity;
import co.edu.udistrital.mdp.beautyathome.entities.CoverageAreaEntity;
import co.edu.udistrital.mdp.beautyathome.entities.ProfessionalEntity;
import co.edu.udistrital.mdp.beautyathome.entities.ServiceEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(ProfessionalService.class)
public class ProfessionalServiceTest {
    @Autowired
    private ProfessionalService professionalService;
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();
    private List<ProfessionalEntity> professionalList = new ArrayList<>();
    private List<ServiceEntity> serviceList = new ArrayList<>();
    private List<AgendaEntity> agendaList = new ArrayList<>();
    private Set<CoverageAreaEntity> coverageAreaList;

    @BeforeEach
    public void setUp(){
        clearData();
        insertData();
    }
    private void clearData() {
        entityManager.getEntityManager().createQuery("DELETE FROM ProfessionalEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM ServiceEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM AgendaEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM CoverageAreaEntity").executeUpdate();
    }
    private void insertData() {
        for (int i = 0; i < 5; i++) {
            ProfessionalEntity professional = factory.manufacturePojo(ProfessionalEntity.class);
            entityManager.persist(professional);
            professionalList.add(professional);

        }
        for (int i = 0; i < 5; i++) {
            ServiceEntity service = factory.manufacturePojo(ServiceEntity.class);
            entityManager.persist(service);
            serviceList.add(service);
        }
        for (int i = 0; i < 5; i++) {
            AgendaEntity agenda = factory.manufacturePojo(AgendaEntity.class);
            entityManager.persist(agenda);
            agendaList.add(agenda);
        }
        for (int i = 0; i < 5; i++) {
            CoverageAreaEntity coverageArea = factory.manufacturePojo(CoverageAreaEntity.class);
            entityManager.persist(coverageArea);
            coverageAreaList.add(coverageArea);
        }
    }

    /**
     * Verifica que se pueda crear un profesional con éxito.
     */
    @Test
    public void testCreateProfessional() throws IllegalOperationException {
        ProfessionalEntity newEntity = factory.manufacturePojo(ProfessionalEntity.class);
      
        newEntity.setServices(serviceList);
        newEntity.setAgenda(agendaList.get(0));
        newEntity.setBirthDate(LocalDate.of(1990,01,01));
        newEntity.setCoverageAreas(coverageAreaList);
        newEntity.setName("Test Professional");
        newEntity.setPhotoUrl("http://example.com/photo.jpg");
        newEntity.setSummary("Test summary of professional experience");

        ProfessionalEntity result = professionalService.createProfessional(newEntity);
        assertNotNull(result);
        ProfessionalEntity entity = entityManager.find(ProfessionalEntity.class,result.getId());

        assertEquals(newEntity.getName(), entity.getName());
        assertEquals(newEntity.getPhotoUrl(), entity.getPhotoUrl());
        assertEquals(newEntity.getSummary(), entity.getSummary());
        assertEquals(newEntity.getBirthDate(), entity.getBirthDate());
        assertEquals(newEntity.getCoverageAreas().size(), entity.getCoverageAreas().size());
        assertEquals(newEntity.getServices().size(), entity.getServices().size());
        assertEquals(newEntity.getAgenda().getId(), entity.getAgenda().getId());
    }

    @Test
    public void testCreateProfessionalWithNullServices() {
        assertThrows(IllegalOperationException.class, () -> {
            ProfessionalEntity newEntity = factory.manufacturePojo(ProfessionalEntity.class);
            newEntity.setServices(null);
            newEntity.setAgenda(agendaList.get(0));
            newEntity.setBirthDate(LocalDate.of(1990,01,01));
            newEntity.setCoverageAreas(coverageAreaList);
            
            professionalService.createProfessional(newEntity);
        });
    }
    @Test
    public void testCreateProfessionalWithNullAgenda() {
        assertThrows(IllegalOperationException.class, () -> {
            ProfessionalEntity newEntity = factory.manufacturePojo(ProfessionalEntity.class);
            newEntity.setServices(serviceList);
            newEntity.setAgenda(null);
            newEntity.setBirthDate(LocalDate.of(1990,01,01));
            newEntity.setCoverageAreas(coverageAreaList);
            
            professionalService.createProfessional(newEntity);
        });
    }
    @Test
    public void testCreateProfessionalWithNullCoverageAreas() {
        assertThrows(IllegalOperationException.class, () -> {
            ProfessionalEntity newEntity = factory.manufacturePojo(ProfessionalEntity.class);
            newEntity.setServices(serviceList);
            newEntity.setAgenda(agendaList.get(0));
            newEntity.setBirthDate(LocalDate.of(1990,01,01));
            newEntity.setCoverageAreas(null);
            
            professionalService.createProfessional(newEntity);
        });
    }
    @Test
    public void testCreateProfessionalWithNullName() {
        assertThrows(IllegalOperationException.class, () -> {
            ProfessionalEntity newEntity = factory.manufacturePojo(ProfessionalEntity.class);
            newEntity.setName(null);
            newEntity.setPhotoUrl("http://example.com/photo.jpg");
            newEntity.setSummary("Test summary of professional experience");
            newEntity.setBirthDate(LocalDate.of(1990,01,01));
            newEntity.setServices(serviceList);
            newEntity.setAgenda(agendaList.get(0));
            newEntity.setCoverageAreas(coverageAreaList);
            
            professionalService.createProfessional(newEntity);
        });
    }
    @Test
    public void testCreateProfessionalWithNullPhotoUrl() {
        assertThrows(IllegalOperationException.class, () -> {
            ProfessionalEntity newEntity = factory.manufacturePojo(ProfessionalEntity.class);
            newEntity.setName("Test Professional");
            newEntity.setPhotoUrl(null);
            newEntity.setSummary("Test summary of professional experience");
            newEntity.setBirthDate(LocalDate.of(1990,01,01));
            newEntity.setServices(serviceList);
            newEntity.setAgenda(agendaList.get(0));
            newEntity.setCoverageAreas(coverageAreaList);
            
            professionalService.createProfessional(newEntity);
        });
    }
    @Test
    public void testCreateProfessionalWithNullSummary() {
        assertThrows(IllegalOperationException.class, () -> {
            ProfessionalEntity newEntity = factory.manufacturePojo(ProfessionalEntity.class);
            newEntity.setName("Test Professional");
            newEntity.setPhotoUrl("http://example.com/photo.jpg");
            newEntity.setSummary(null);
            newEntity.setBirthDate(LocalDate.of(1990,01,01));
            newEntity.setServices(serviceList);
            newEntity.setAgenda(agendaList.get(0));
            newEntity.setCoverageAreas(coverageAreaList);
            
            professionalService.createProfessional(newEntity);
        });
    }
    @Test
    public void testCreateProfessionalWithNullBirthDate() {
        assertThrows(IllegalOperationException.class, () -> {
            ProfessionalEntity newEntity = factory.manufacturePojo(ProfessionalEntity.class);
            newEntity.setName("Test Professional");
            newEntity.setPhotoUrl("http://example.com/photo.jpg");
            newEntity.setSummary("Test summary of professional experience");
            newEntity.setBirthDate(null);
            newEntity.setServices(serviceList);
            newEntity.setAgenda(agendaList.get(0));
            newEntity.setCoverageAreas(coverageAreaList);
            
            professionalService.createProfessional(newEntity);
        });
    }

    @Test
    public void testCreateProfessionalWithEmptyName() {
        assertThrows(IllegalOperationException.class, () -> {
            ProfessionalEntity newEntity = factory.manufacturePojo(ProfessionalEntity.class);
            newEntity.setName("");
            newEntity.setPhotoUrl("http://example.com/photo.jpg");
            newEntity.setSummary("Test summary of professional experience");
            newEntity.setBirthDate(LocalDate.of(1990,01,01));
            newEntity.setServices(serviceList);
            newEntity.setAgenda(agendaList.get(0));
            newEntity.setCoverageAreas(coverageAreaList);
            professionalService.createProfessional(newEntity);
        });
    }

    @Test
    public void testCreateProfessionalWithEmptyPhotoUrl() {
        assertThrows(IllegalOperationException.class, () -> {
            ProfessionalEntity newEntity = factory.manufacturePojo(ProfessionalEntity.class);
            newEntity.setName("Test Professional");
            newEntity.setPhotoUrl("");
            newEntity.setSummary("Test summary of professional experience");
            newEntity.setBirthDate(LocalDate.of(1990,01,01));
            newEntity.setServices(serviceList);
            newEntity.setAgenda(agendaList.get(0));
            newEntity.setCoverageAreas(coverageAreaList);
            professionalService.createProfessional(newEntity);
        });
    }

    @Test
    public void testCreateProfessionalWithEmptySummary() {
        assertThrows(IllegalOperationException.class, () -> {
            ProfessionalEntity newEntity = factory.manufacturePojo(ProfessionalEntity.class);
            newEntity.setName("Test Professional");
            newEntity.setPhotoUrl("http://example.com/photo.jpg");
            newEntity.setSummary("");

            newEntity.setBirthDate(LocalDate.of(1990,01,01));
            newEntity.setServices(serviceList);
            newEntity.setAgenda(agendaList.get(0));
            newEntity.setCoverageAreas(coverageAreaList);
            professionalService.createProfessional(newEntity);
        });
    }

    @Test
    public void testCreateProfessionalWithEmptyBirthDate() {
        assertThrows(IllegalOperationException.class, () -> {
            ProfessionalEntity newEntity = factory.manufacturePojo(ProfessionalEntity.class);
            newEntity.setName("Test Professional");
            newEntity.setPhotoUrl("http://example.com/photo.jpg");
            newEntity.setSummary("Test summary of professional experience");
            newEntity.setBirthDate(null);
            newEntity.setServices(serviceList);
            newEntity.setAgenda(agendaList.get(0));
            newEntity.setCoverageAreas(coverageAreaList);
            professionalService.createProfessional(newEntity);
        });
    }





    @Test
    public void testGetProfessionals(){
        List<ProfessionalEntity> professionalsFromDataBase = professionalService.getProfessionals();
        assertEquals(professionalsFromDataBase, professionalList.size());
        for(ProfessionalEntity professional : professionalsFromDataBase) {
            boolean found = false;
            for (ProfessionalEntity entity : professionalList) {
                if (entity.getId().equals(professional.getId())) {
                    found = true;
                    break;
                }
            }
            assertEquals(true, found);
        }
    }
    @Test
    public void testGetValidProfessional() throws EntityNotFoundException {
        ProfessionalEntity entity = professionalList.get(0);
        ProfessionalEntity result = professionalService.getProfessional(entity.getId());
        assertEquals(entity.getId(), result.getId());
        assertEquals(entity.getName(), result.getName());
        assertEquals(entity.getPhotoUrl(), result.getPhotoUrl());
        assertEquals(entity.getSummary(), result.getSummary());
        assertEquals(entity.getBirthDate(), result.getBirthDate());
        assertEquals(entity.getCoverageAreas().size(), result.getCoverageAreas().size());
        assertEquals(entity.getServices().size(), result.getServices().size());
        assertEquals(entity.getAgenda().getId(), result.getAgenda().getId());
    }

    @Test
    public void testGetInvalidProfessional(){
        assertThrows(EntityNotFoundException.class,()->{
            professionalService.getProfessional(0L);
        });

    }

    @Test
    public void testUpdateProfessional() throws EntityNotFoundException, IllegalOperationException {
        ProfessionalEntity entity = professionalList.get(0);
        ProfessionalEntity updatedEntity = factory.manufacturePojo(ProfessionalEntity.class);
        updatedEntity.setId(entity.getId());
        updatedEntity.setName("Updated Professional");
        updatedEntity.setPhotoUrl("http://example.com/updated_photo.jpg");
        updatedEntity.setSummary("Updated summary of professional experience");
        updatedEntity.setBirthDate(LocalDate.of(1990,01,01));
        updatedEntity.setCoverageAreas(coverageAreaList);
        updatedEntity.setServices(serviceList);
        updatedEntity.setAgenda(agendaList.get(0));

        professionalService.updateProfessional((entity.getId()), updatedEntity);

        ProfessionalEntity response = entityManager.find(ProfessionalEntity.class,entity.getId());

        assertEquals(updatedEntity.getId(), response.getId());
        assertEquals(updatedEntity.getName(), response.getName());
        assertEquals(updatedEntity.getPhotoUrl(), response.getPhotoUrl());
        assertEquals(updatedEntity.getSummary(), response.getSummary());
        assertEquals(updatedEntity.getBirthDate(), response.getBirthDate());
        assertEquals(updatedEntity.getCoverageAreas().size(), response.getCoverageAreas().size());
        assertEquals(updatedEntity.getServices().size(), response.getServices().size());
        assertEquals(updatedEntity.getAgenda().getId(), response.getAgenda().getId());

    }

    @Test 
    public void testUpdateInvalidProfessional() {
        assertThrows(EntityNotFoundException.class, () -> {
            ProfessionalEntity updatedProfessional = factory.manufacturePojo(ProfessionalEntity.class);
            updatedProfessional.setId(0L);
            professionalService.updateProfessional(0L, updatedProfessional);
        });
    }

    @Test
    public void testUpdateInvalidProfessionalWithNullName() {
        assertThrows(IllegalOperationException.class, () -> {
            ProfessionalEntity entity = professionalList.get(0);
            ProfessionalEntity updatedEntity = factory.manufacturePojo(ProfessionalEntity.class);
            updatedEntity.setId(entity.getId());
            updatedEntity.setName(null);
            updatedEntity.setPhotoUrl("http://example.com/updated_photo.jpg");
            updatedEntity.setSummary("Updated summary of professional experience");
            updatedEntity.setBirthDate(LocalDate.of(1990,01,01));
            updatedEntity.setCoverageAreas(coverageAreaList);
            updatedEntity.setServices(serviceList);
            updatedEntity.setAgenda(agendaList.get(0));
            professionalService.updateProfessional(entity.getId(), updatedEntity);
        });
    }

    @Test
    public void testUpdateInvalidProfessionalWithNullPhotoUrl() {
        assertThrows(IllegalOperationException.class, () -> {
            ProfessionalEntity entity = professionalList.get(0);
            ProfessionalEntity updatedEntity = factory.manufacturePojo(ProfessionalEntity.class);
            updatedEntity.setId(entity.getId());
            updatedEntity.setName("Updated Professional");
            updatedEntity.setPhotoUrl(null);
            updatedEntity.setSummary("Updated summary of professional experience");
            updatedEntity.setBirthDate(LocalDate.of(1990,01,01));
            updatedEntity.setCoverageAreas(coverageAreaList);
            updatedEntity.setServices(serviceList);
            updatedEntity.setAgenda(agendaList.get(0));
            professionalService.updateProfessional(entity.getId(), updatedEntity);
        });
    }

    @Test
    public void testUpdateInvalidProfessionalWithNullSummary() {
        assertThrows(IllegalOperationException.class, () -> {
            ProfessionalEntity entity = professionalList.get(0);
            ProfessionalEntity updatedEntity = factory.manufacturePojo(ProfessionalEntity.class);
            updatedEntity.setId(entity.getId());
            updatedEntity.setName("Updated Professional");
            updatedEntity.setPhotoUrl("http://example.com/updated_photo.jpg");
            updatedEntity.setSummary(null);
            updatedEntity.setBirthDate(LocalDate.of(1990,01,01));
            updatedEntity.setCoverageAreas(coverageAreaList);
            updatedEntity.setServices(serviceList);
            updatedEntity.setAgenda(agendaList.get(0));

            professionalService.updateProfessional(entity.getId(), updatedEntity);
        });
    }

    @Test
    public void testUpdateInvalidProfessionalWithNullBirthDate() {
        assertThrows(IllegalOperationException.class, () -> {
            ProfessionalEntity entity = professionalList.get(0);
            ProfessionalEntity updatedEntity = factory.manufacturePojo(ProfessionalEntity.class);
            updatedEntity.setId(entity.getId());
            updatedEntity.setName("Updated Professional");
            updatedEntity.setPhotoUrl("http://example.com/updated_photo.jpg");
            updatedEntity.setSummary("Updated summary of professional experience");
            updatedEntity.setBirthDate(null);
            updatedEntity.setCoverageAreas(coverageAreaList);
            updatedEntity.setServices(serviceList);
            updatedEntity.setAgenda(agendaList.get(0));

            professionalService.updateProfessional(entity.getId(), updatedEntity);
        });
    }

    @Test
    public void testUpdateInvalidProfessionalWithNullCoverageAreas() {
        assertThrows(IllegalOperationException.class, () -> {
            ProfessionalEntity entity = professionalList.get(0);
            ProfessionalEntity updatedEntity = factory.manufacturePojo(ProfessionalEntity.class);
            updatedEntity.setId(entity.getId());
            updatedEntity.setName("Updated Professional");
            updatedEntity.setPhotoUrl("http://example.com/updated_photo.jpg");
            updatedEntity.setSummary("Updated summary of professional experience");
            updatedEntity.setBirthDate(LocalDate.of(1990,01,01));
            updatedEntity.setCoverageAreas(null);
            updatedEntity.setServices(serviceList);
            updatedEntity.setAgenda(agendaList.get(0));

            professionalService.updateProfessional(entity.getId(), updatedEntity);
        });
    }

    @Test
    public void testUpdateInvalidProfessionalWithNullServices() {
        assertThrows(IllegalOperationException.class, () -> {
            ProfessionalEntity entity = professionalList.get(0);
            ProfessionalEntity updatedEntity = factory.manufacturePojo(ProfessionalEntity.class);
            updatedEntity.setId(entity.getId());
            updatedEntity.setName("Updated Professional");
            updatedEntity.setPhotoUrl("http://example.com/updated_photo.jpg");
            updatedEntity.setSummary("Updated summary of professional experience");
            updatedEntity.setBirthDate(LocalDate.of(1990,01,01));
            updatedEntity.setCoverageAreas(coverageAreaList);
            updatedEntity.setServices(null);
            updatedEntity.setAgenda(agendaList.get(0));

            professionalService.updateProfessional(entity.getId(), updatedEntity);
        });
    }

    @Test
    public void testUpdateInvalidProfessionalWithNullAgenda() {
        assertThrows(IllegalOperationException.class, () -> {
            ProfessionalEntity entity = professionalList.get(0);
            ProfessionalEntity updatedEntity = factory.manufacturePojo(ProfessionalEntity.class);
            updatedEntity.setId(entity.getId());
            updatedEntity.setName("Updated Professional");
            updatedEntity.setPhotoUrl("http://example.com/updated_photo.jpg");
            updatedEntity.setSummary("Updated summary of professional experience");
            updatedEntity.setBirthDate(LocalDate.of(1990,01,01));
            updatedEntity.setCoverageAreas(coverageAreaList);
            updatedEntity.setServices(serviceList);
            updatedEntity.setAgenda(null);
            professionalService.updateProfessional(entity.getId(), updatedEntity);
        });
    }

    @Test
    public void testDeleteValidProfessional() throws EntityNotFoundException {
        ProfessionalEntity entity = professionalList.get(0);
        professionalService.deleteProfessional(entity.getId());
        ProfessionalEntity deletedEntity = entityManager.find(ProfessionalEntity.class, entity.getId());
        assertNull(deletedEntity);
    }
    @Test
    public void testDeleteInvalidProfessional() {
        assertThrows(EntityNotFoundException.class, () -> {
            professionalService.deleteProfessional(0L);
        });
    }

}
