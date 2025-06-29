package co.edu.udistrital.mdp.beautyathome.entities;

import jakarta.persistence.*;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data // Anotación de Lombok para generación automática de getters, setters, etc.
@Entity // Indica que esta clase es una entidad JPA
@Inheritance(strategy = InheritanceType.JOINED) // Estrategia de herencia JOINED para posibles subclases
public class ExclusiveProductEntity extends BaseEntity {

    private String name;
    private String photo;
    private Double price;
    private Boolean available;
    private String category;
    private String description;

    @PodamExclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;
}