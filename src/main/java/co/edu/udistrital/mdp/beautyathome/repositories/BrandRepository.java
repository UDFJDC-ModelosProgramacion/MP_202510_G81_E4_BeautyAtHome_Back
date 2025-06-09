// Paquete donde se encuentra la interfaz
package co.edu.udistrital.mdp.beautyathome.repositories;

// Importaciones necesarias
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.udistrital.mdp.beautyathome.entities.BrandEntity;

/**
 * Repositorio JPA para la entidad BrandEntity (Marca).
 * Esta interfaz proporciona operaciones CRUD básicas y métodos de consulta
 * para interactuar con la tabla de marcas en la base de datos.
 * Extiende JpaRepository que ya incluye implementaciones para:
 * - save(), saveAll() → Guardar entidades
 * - findById(), findAll() → Consultar entidades
 * - delete(), deleteAll() → Eliminar entidades
 * - count(), existsById() → Operaciones adicionales
 */
@Repository // Indica que es un componente de repositorio Spring (maneja excepciones específicas)
public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
    /*
     * Parámetros genéricos:
     * - BrandEntity → Tipo de entidad que maneja este repositorio
     * - Long → Tipo del ID de la entidad (clave primaria)
     *
     * Al extender JpaRepository, automáticamente obtiene todos los métodos CRUD básicos
     * sin necesidad de implementarlos.
     *
     * Se pueden agregar métodos personalizados con convención de nombres.
     */
}