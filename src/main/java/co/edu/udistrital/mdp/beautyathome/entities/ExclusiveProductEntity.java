package co.edu.udistrital.mdp.beautyathome.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ExclusiveProductEntity extends BaseEntity {
    @PodamExclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String photograph;
    private Double price;
    private Boolean available;
    private String category;
    private String description;

    @ManyToOne
    @PodamExclude
    private BrandEntity brand;

    @OneToMany(mappedBy = "exclusiveProduct")
    @PodamExclude
    private List<ReferenceImageEntity> referenceImages;
}
