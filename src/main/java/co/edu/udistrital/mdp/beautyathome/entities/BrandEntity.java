package co.edu.udistrital.mdp.beautyathome.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BrandEntity extends BaseEntity {

    private String name;
    private String photograph;
    private Double price;
    private Boolean available;
    private String category;
    private String description;
    private String product;

    @OneToMany(mappedBy = "brand")
    @PodamExclude
    private List<ExclusiveProductEntity> exclusiveProducts;

}
