package co.edu.udistrital.mdp.beautyathome.entities;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity

public class ServiceEntity extends BaseEntity {

    String name,description;
    Date releaseDate;
    Double price;
    
    @PodamExclude
    @ManyToOne
    ProfessionalEntity professional;

    @PodamExclude
    @OneToMany(mappedBy = "service")
    List<ReviewEntity> reviews = new java.util.ArrayList<>();

    //@PodamExclude
    //@ManyToOne
    //CoverageAreaEntity coverageArea;

    @PodamExclude
    @ManyToOne
    BrandEntity brand;

    @PodamExclude
    @OneToMany(mappedBy = "service")
    List<ReferenceImageEntity> referenceImages = new java.util.ArrayList<>();

}
