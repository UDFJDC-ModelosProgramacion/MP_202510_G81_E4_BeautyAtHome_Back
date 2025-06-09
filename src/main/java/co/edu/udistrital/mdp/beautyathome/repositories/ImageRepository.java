// Paquete donde se encuentra el repositorio
package co.edu.udistrital.mdp.beautyathome.repositories;

// Importaciones necesarias
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.edu.udistrital.mdp.beautyathome.entities.ImageEntity;

/**
 * Repositorio para la entidad ImageEntity que proporciona operaciones CRUD
 * básicas y queries automáticas sin necesidad de implementación.
 * Esta interfaz extiende JpaRepository que ya incluye métodos como:
 * - save(), saveAll()
 * - findById(), findAll()
 * - delete(), deleteAll()
 * - count()
 * - existsById()
 * La anotación @Repository indica que esta interfaz es un componente
 * Spring que maneja operaciones de persistencia y conversión de excepciones
 * específicas de JPA a excepciones de Spring.
 */
@Repository // Identifica esta interfaz como un componente de repositorio Spring
public interface ImageRepository extends JpaRepository<ImageEntity, Long> {

    /*
     * Hereda automáticamente todos los métodos CRUD básicos de JpaRepository.
     * Parámetros genéricos:
     * - ImageEntity: Tipo de entidad que maneja el repositorio
     * - Long: Tipo del ID de la entidad
     * No requiere implementación - Spring Data JPA provee la implementación
     * automática en tiempo de ejecución.
     * Ejemplos de uso:
     * - imageRepository.save(imageEntity)
     * - imageRepository.findById(1L)
     * - imageRepository.deleteById(1L)
     */
}