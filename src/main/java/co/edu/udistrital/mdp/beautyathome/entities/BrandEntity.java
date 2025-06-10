// Paquete donde se encuentra la clase
package co.edu.udistrital.mdp.beautyathome.entities;

// Importaciones necesarias
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;


/**
 * Entidad JPA que representa una Marca en el sistema.
 * Hereda de BaseEntity para campos comunes como id y timestamps.
 * Mapea a la tabla de marcas en la base de datos y establece relaciones
 * con otras entidades del sistema.
 */
@Data // Anotación de Lombok para generación automática de getters, setters, etc.
@Entity // Indica que esta clase es una entidad JPA
@Inheritance(strategy = InheritanceType.JOINED) // Estrategia de herencia JOINED para posibles subclases
public class BrandEntity extends BaseEntity {

    /**
     * Nombre de la marca.
     * Ejemplo: "L'Oréal", "Maybelline", etc.
     */
    private String name;

    /**
     * URL del logotipo de la marca.
     * Almacena la ubicación/referencia de la imagen del logo.
     */
    private String logoURL;

    /**
     * Fotografía representativa de la marca.
     * Puede ser una URL o referencia a imagen almacenada.
     */
    private String photograph;

    /**
     * Precio asociado a la marca (posiblemente precio promedio).
     * Permite valores decimales para mayor precisión.
     */
    private Double price;

    /**
     * Indica si la marca está disponible actualmente en el sistema.
     * true = disponible, false = no disponible.
     */
    private Boolean available;

    /**
     * Categoría principal de la marca.
     * Ejemplo: "Maquillaje", "Cuidado facial", etc.
     */
    private String category;

    /**
     * Descripción detallada de la marca.
     * Puede incluir historia, especialidades, etc.
     */
    private String description;

    /**
     * Producto destacado o principal de la marca.
     * Referencia al producto más representativo.
     */
    private String product;

    /**
     * Relación uno-a-muchos con productos exclusivos de esta marca.
     * mappedBy = "brand" → Indica que la relación es bidireccional y está mapeada
     * por el campo 'brand' en ExclusiveProductEntity.
     * cascade = CascadeType. ALL → Las operaciones (persist, merge, remove, etc.)
     * se propagarán a los productos relacionados.
     * orphanRemoval = true → Si se elimina un producto de esta colección,
     * se eliminará automáticamente de la base de datos.
     */
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ExclusiveProductEntity> exclusiveProducts;
}
