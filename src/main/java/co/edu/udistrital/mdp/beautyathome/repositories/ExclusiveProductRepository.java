package co.edu.udistrital.mdp.beautyathome.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.udistrital.mdp.beautyathome.entities.ExclusiveProductEntity;

@Repository 
public interface ExclusiveProductRepository extends JpaRepository<ExclusiveProductEntity, Long> {

    /**
     * Metodo personalizado para buscar productos exclusivos por ID de marca.
     * @param brandId ID de la marca para filtrar los productos
     * @return Lista de productos exclusivos asociados a la marca especificada
     */
    List<ExclusiveProductEntity> findByBrand_Id(Long brandId);
}