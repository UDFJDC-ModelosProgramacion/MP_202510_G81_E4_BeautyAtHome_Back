// Paquete donde se encuentra la entidad
package co.edu.udistrital.mdp.beautyathome.entities;

// Importaciones de JPA y Lombok
import jakarta.persistence.Entity;
import lombok.Data;

/**
 * Entidad que representa una imagen en el sistema.
 * Mapea a una tabla en la base de datos y hereda campos básicos
 * de la clase BaseEntity.
 *
 * La anotación @Data de Lombok genera automáticamente:
 * - Getters y setters para todos los campos
 * - Métodos toString(), equals() y hashCode()
 *
 * La anotación @Entity indica que esta clase es una entidad JPA
 * que se mapeará a una tabla en la base de datos.
 */
@Data // Anotación de Lombok para generación de métodos boilerplate
@Entity // Indica que esta clase es una entidad JPA
public class ImageEntity extends BaseEntity {
    /**
     * URL o ruta donde se almacena la imagen.
     * Este campo se mapea a una columna en la tabla de la base de datos.
     *
     * Puede contener:
     * - Rutas relativas (ej: "/uploads/imagenes/perfil.jpg")
     * - Rutas absolutas (ej: "https://bucket.s3.amazonaws.com/perfil.jpg")
     * - Identificadores de almacenamiento en la nube
     *
     * No está anotado con @Column porque JPA por defecto mapea
     * el nombre del campo directamente al nombre de la columna.
     */
    private String url;
}