// Paquete donde se encuentra la clase
package co.edu.udistrital.mdp.beautyathome.dto;

// Importación de la anotación Lombok para generar automáticamente
// getters, setters, toString, equals, hashCode, etc.
import lombok.Data;

/**
 * Clase DTO (Data Transfer Object) que representa una Marca (Brand) en el sistema.
 * Se utiliza para transferir datos entre las capas de la aplicación.
 * La anotación @Data de Lombok genera automáticamente:
 * - Getters para todos los campos
 * - Setters para todos los campos no finales
 * - Métodos toString(), equals() y hashCode()
 * - Constructor con todos los campos no finales
 */
@Data // Anotación de Lombok para generación automática de métodos comunes
public class BrandDTO {

    /**
     * Identificador único de la marca en el sistema.
     * Tipo: Long (para soportar grandes cantidades de registros)
     */
    private Long id;

    /**
     * Nombre de la marca.
     * Ejemplo: "L'Oréal", "Maybelline", etc.
     * Tipo: String (cadena de texto)
     */
    private String name;

    /**
     * Fotografía o imagen representativa de la marca.
     * Puede contener una URL o un identificador de imagen.
     * Tipo: String
     */
    private String photograph;

    /**
     * Precio asociado a la marca (posiblemente precio promedio de sus productos).
     * Tipo: Double (para soportar valores decimales)
     */
    private Double price;

    /**
     * Indica si la marca está disponible actualmente en el sistema.
     * Tipo: Boolean (true/false)
     */
    private Boolean available;

    /**
     * Categoría principal a la que pertenece la marca.
     * Ejemplo: "Maquillaje", "Cuidado facial", etc.
     * Tipo: String
     */
    private String category;

    /**
     * Descripción detallada de la marca.
     * Puede incluir historia, especialidades, etc.
     * Tipo: String
     */
    private String description;

    /**
     * Producto destacado o principal de la marca.
     * Tipo: String
     */
    private String product;

    /**
     * URL del logotipo oficial de la marca.
     * Este campo es importante para la identidad visual de la marca en la aplicación.
     * Tipo: String
     */
    private String logoURL; // campo adicional que faltaba
}