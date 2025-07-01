package co.edu.udistrital.mdp.beautyathome.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity

public class ServiceEntity extends BaseEntity {

    private String name;
    private String description;
    private Double price;

    @PodamExclude
    @ElementCollection
    @CollectionTable(name = "service_reference_images", joinColumns = @JoinColumn(name = "service_id"))
    @Column(name = "image_url")
    private List<String> referenceImagesUrls;

    @PodamExclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professional_id")
    private ProfessionalEntity professional;

    @PodamExclude
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServiceRecordEntity> records = new ArrayList<>();

    @PodamExclude
    @ManyToOne
    private BrandEntity brand;
}
