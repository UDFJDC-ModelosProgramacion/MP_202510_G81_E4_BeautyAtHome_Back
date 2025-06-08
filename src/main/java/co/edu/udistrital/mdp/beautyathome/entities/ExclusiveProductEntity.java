package co.edu.udistrital.mdp.beautyathome.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
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
