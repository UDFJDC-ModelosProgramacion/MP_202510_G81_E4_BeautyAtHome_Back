package co.edu.udistrital.mdp.beautyathome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.sql.Date;

import co.edu.udistrital.mdp.beautyathome.entities.ServiceEntity;
import java.util.List;
import java.util.Optional;


@Repository

public interface ServicioRepository extends JpaRepository<ServiceEntity,Long>{
    
    Optional<ServiceEntity> findById(Long id);

    List<ServiceEntity> findByFechaDeRealizacion(Date fechaDeRealizacion);
    List<ServiceEntity> findByNombre(String nombre);
    List<ServiceEntity> findByPrecio(Double precio);



}
