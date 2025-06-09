// Paquete donde se encuentra la clase
package co.edu.udistrital.mdp.beautyathome.entities;

// Importaciones necesarias
import jakarta.persistence.*;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Entidad JPA que representa un Producto Exclusivo en el sistema.
 * Hereda de BaseEntity para campos comunes como id y timestamps.
 * Mapea a la tabla de productos exclusivos en la base de datos y establece
 * relación con la entidad BrandEntity (Marca).
 */
@Data // Anotación de Lombok para generación automática de getters, setters, etc.
@Entity // Indica que esta clase es una entidad JPA
@Inheritance(strategy = InheritanceType.JOINED) // Estrategia de herencia JOINED para posibles subclases
public class ExclusiveProductEntity extends BaseEntity {

    /**
     * Nombre del producto exclusivo.
     * Ejemplo: "Kit Maquillaje Profesional", "Serum Anti-Edad", etc.
     */
    private String name;

    /**
     * Foto o imagen representativa del producto.
     * Puede contener una URL o referencia a archivo almacenado.
     */
    private String photo;

    /**
     * Precio del producto exclusivo.
     * Usa Double para soportar valores decimales (precisión monetaria).
     */
    private Double price;

    /**
     * Indica si el producto está disponible para venta.
     * true = disponible, false = no disponible.
     */
    private Boolean available;

    /**
     * Categoría del producto.
     * Ejemplo: "Maquillaje", "Cuidado Facial", "Accesorios", etc.
     */
    private String category;

    /**
     * Descripción detallada del producto.
     * Incluye características, beneficios, modo de uso, etc.
     */
    private String description;

    /**
     * Relación muchos-a-uno con la marca (BrandEntity) a la que pertenece este producto.
     * podamExclude → Excluye este campo del llenado automático durante pruebas con podam
     * ManyToOne → Establece la relación muchos productos a una marca:
     *   - fetch = FetchType. LAZY → Carga perezosa (solo cuando se accede explícitamente)
     * JoinColumn → Especifica la columna de unión en la tabla:
     *   - name = "brand_id" → Nombre de la columna FK en la tabla de productos
     */
    @PodamExclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;
}