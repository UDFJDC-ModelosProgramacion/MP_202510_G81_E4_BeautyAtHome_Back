// Paquete donde se encuentra la interfaz
package co.edu.udistrital.mdp.beautyathome.repositories;

// Importaciones necesarias
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.udistrital.mdp.beautyathome.entities.ExclusiveProductEntity;

/**
 * Repositorio JPA para la entidad ExclusiveProductEntity (Producto Exclusivo).
 * Proporciona operaciones CRUD básicas y métodos de consulta personalizados
 * para interactuar con la tabla de productos exclusivos en la base de datos.
 */
@Repository // Identifica esta interfaz como un componente de repositorio Spring
public interface ExclusiveProductRepository extends JpaRepository<ExclusiveProductEntity, Long> {

    /**
     * Metodo personalizado para buscar productos exclusivos por ID de marca.
     * Spring Data JPA implementa automáticamente este metodo basado en la convención de nombres:
     * - "findBy" → indica que es una consulta
     * - "Brand" → referencia a la relación con BrandEntity
     * - "Id" → campo id de la marca relacionada
     * Equivalente a: SELECT * FROM exclusive_product WHERE brand_id =?1
     *
     * @param brandId ID de la marca para filtrar los productos
     * @return Lista de productos exclusivos asociados a la marca especificada
     */
    List<ExclusiveProductEntity> findByBrand_Id(Long brandId);

    /*
     * Al extender JpaRepository<ExclusiveProductEntity, Long>, esta interfaz hereda
     * automáticamente todos los métodos CRUD estándar:
     * - save(), saveAll()
     * - findById(), findAll()
     * - delete(), deleteAll()
     * - count(), existsById()
     * - etc.
     *
     * Parámetros genéricos:
     * - ExclusiveProductEntity: Tipo de entidad que maneja este repositorio
     * - Long: Tipo del ID de la entidad (clave primaria)
     */
}