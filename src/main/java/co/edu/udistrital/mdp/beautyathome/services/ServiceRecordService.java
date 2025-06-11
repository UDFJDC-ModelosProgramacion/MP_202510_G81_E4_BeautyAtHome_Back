package co.edu.udistrital.mdp.beautyathome.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.beautyathome.entities.ReviewEntity;
import co.edu.udistrital.mdp.beautyathome.entities.ServiceEntity;
import co.edu.udistrital.mdp.beautyathome.entities.ServiceRecordEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.beautyathome.repositories.ServiceRecordRepository;
import jakarta.transaction.Transactional;

@Service
public class ServiceRecordService {
    @Autowired
    private ServiceRecordRepository serviceRecordRepository;

    /**
     * Método para crear guardar un ServiceRecord en la base de datos
     * @param serviceRecordEntity ServiceRecord a crear y guardar
     * @return savedServiceRecordEntity, entidad ya guardada
     */
    @Transactional
    public ServiceRecordEntity createServiceRecord(ServiceRecordEntity serviceRecordEntity){
        if(serviceRecordEntity.getDatePerformed() == null){
            throw new IllegalArgumentException("The date performed cannot be null");
        }
        if(serviceRecordEntity.getService() == null){
            throw new IllegalArgumentException("The service cannot be null");
        }
        ServiceRecordEntity savedServiceRecordEntity = serviceRecordRepository.save(serviceRecordEntity);
        return savedServiceRecordEntity;
    }

    /**
     * Método para asignar una review a un ServiceRecord, además de guardar la review gracias a CascadeType.ALL en ServiceRecordEntity
     * @param recordId id del record al que pertenecerá la review
     * @param review del ServiceRecord
     * @return el ServiceRecord guardado con review
     * @throws EntityNotFoundException en caso de que la review no exista
     */
    @Transactional
    public ServiceRecordEntity addReviewToRecord(Long recordId, ReviewEntity review) throws EntityNotFoundException {
        ServiceRecordEntity record = serviceRecordRepository.findById(recordId)
            .orElseThrow(() -> new EntityNotFoundException("The service record with the given id was not found: " + recordId));

        if (record.getReview() != null) {
            throw new IllegalStateException("This record already has a review");
        }
        review.setServiceRecord(record);
        record.setReview(review);
        return serviceRecordRepository.save(record);
    }

    @Transactional
    public void deleteServiceRecord(Long recordId) throws EntityNotFoundException {
        Optional<ServiceRecordEntity> optionalRecord = serviceRecordRepository.findById(recordId);
        if (optionalRecord.isEmpty()) {
            throw new EntityNotFoundException("The service record with the given id was not found: " + recordId);
        }
        serviceRecordRepository.deleteById(recordId);
    }
}
