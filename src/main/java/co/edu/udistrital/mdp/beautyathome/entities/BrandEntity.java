package co.edu.udistrital.mdp.beautyathome.entities;

import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;


@Data
@Entity

@Inheritance(strategy = InheritanceType.JOINED)
public class BrandEntity extends BaseEntity {

    private String name;
    private String logoURL;

    @PodamExclude
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ExclusiveProductEntity> exclusiveProducts;
    
    @PodamExclude
    @OneToMany(mappedBy = "brand")
    private List<ServiceEntity> services;

}
