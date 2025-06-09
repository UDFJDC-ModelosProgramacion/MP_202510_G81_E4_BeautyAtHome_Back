// Paquete donde se encuentra la clase
package co.edu.udistrital.mdp.beautyathome.dto;

// Importación de la anotación Lombok para generar automáticamente
// getters, setters, toString, equals, hashCode, etc.
import lombok.Data;

/**
 * Clase DTO (Data Transfer Object) que representa un Producto Exclusivo en el sistema.
 * Se utiliza para transferir datos entre las capas de la aplicación, especialmente
 * entre el controlador y los servicios.
 * La anotación @Data de Lombok genera automáticamente:
 * - Métodos getters para todos los campos
 * - Métodos setters para todos los campos no finales
 * - Metodo toString()
 * - Métodos equals() y hashCode()
 * - Constructor con todos los campos no finales
 */
@Data
public class ExclusiveProductDTO {

    /**
     * Identificador único del producto exclusivo en el sistema.
     * Tipo: Long (para soportar grandes cantidades de registros)
     */
    private Long id;

    /**
     * Nombre del producto exclusivo.
     * Ejemplo: "Kit de maquillaje profesional", "Crema rejuvenecedora", etc.
     * Tipo: String (cadena de texto)
     */
    private String name;

    /**
     * Foto o imagen representativa del producto.
     * Puede contener una URL o un identificador de imagen almacenada.
     * Tipo: String
     */
    private String photo;

    /**
     * Precio del producto exclusivo.
     * Tipo: Double (para soportar valores decimales en la moneda)
     */
    private Double price;

    /**
     * Indica si el producto está actualmente disponible para la venta.
     * Tipo: Boolean (true = disponible, false = no disponible)
     */
    private Boolean available;

    /**
     * Categoría a la que pertenece el producto.
     * Ejemplo: "Maquillaje", "Cuidado de la piel", "Accesorios", etc.
     * Tipo: String
     */
    private String category;

    /**
     * Descripción detallada del producto.
     * Incluye características, beneficios, modo de uso, etc.
     * Tipo: String
     */
    private String description;

    /**
     * Identificador de la marca (Brand) a la que pertenece este producto exclusivo.
     * Establece la relación muchos-a-uno entre productos y marcas.
     * Tipo: Long (debe coincidir con el ID de una Brand existente)
     */
    private Long brandId;
}