package co.edu.udistrital.mdp.beautyathome.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import co.edu.udistrital.mdp.beautyathome.entities.ServicioEntity;
import java.util.List;
import java.util.Optional;


@Repository

public interface ServicioRepository extends JpaRepository<ServicioEntity,Long>{
    
    Optional<ServicioEntity> findById(Long id);

    List<ServicioEntity> findByFechaDeRealizacion(String fechaDeRealizacion);
    List<ServicioEntity> findByNombre(String nombre);
    List<ServicioEntity> findByPrecio(Double precio);



}
