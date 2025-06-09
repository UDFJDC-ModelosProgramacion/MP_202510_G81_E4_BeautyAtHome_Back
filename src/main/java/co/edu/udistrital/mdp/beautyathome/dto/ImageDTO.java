// Paquete donde se encuentra la clase DTO
package co.edu.udistrital.mdp.beautyathome.dto;

// Importación de la anotación Lombok
import lombok.Data;

/**
 * Clase DTO (Data Transfer Object) para la entidad Image.
 * Se utiliza para transferir datos entre el cliente y el servidor
 * sin exponer la estructura interna de la entidad.
 * Lombok @Data genera automáticamente:
 * - Getters para todos los campos
 * - Setters para todos los campos no-finales
 * - toString()
 * - equals() y hashCode()
 */
@Data // Anotación de Lombok para generar métodos boilerplate
public class ImageDTO {
    /**
     * Identificador único de la imagen
     * Corresponde al ID en la base de datos
     */
    private Long id;

    /**
     * URL donde se encuentra almacenada la imagen
     * Puede ser una ruta relativa o absoluta dependiendo de la configuración
     * del servidor de archivos o servicio de almacenamiento (S3, etc.)
     */
    private String url;
}