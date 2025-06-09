package co.edu.udistrital.mdp.beautyathome.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import co.edu.udistrital.mdp.beautyathome.entities.ServiceRecordEntity;

@Repository
public interface ServiceRecordRepository extends JpaRepository<ServiceRecordEntity, Long> {
    List<ServiceRecordEntity> findByService_Id(Long serviceId);
}
