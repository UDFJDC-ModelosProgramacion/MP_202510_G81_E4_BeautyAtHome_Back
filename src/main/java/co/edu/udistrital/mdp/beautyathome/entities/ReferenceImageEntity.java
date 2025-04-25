package co.edu.udistrital.mdp.beautyathome.entities;

import java.util.Date;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ReferenceImageEntity extends BaseEntity {
    @PodamExclude
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String url;
    private String fileName;
    private String contentType;
    private String description;
    private Date uploadDate;
    private Boolean isMainImage;

    @ManyToOne
    @PodamExclude
    private ExclusiveProductEntity exclusiveProduct;

    @ManyToOne
    @PodamExclude
    private ServiceEntity service;
}
