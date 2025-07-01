package co.edu.udistrital.mdp.beautyathome.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity

public class ProfessionalEntity extends BaseEntity{
    
    private String name;
    @Column(length = 800)
    private String photoUrl;
    private String summary;
    private boolean sponsored;
    private LocalDate birthDate;

    @PodamExclude
    // Marca patrocinadora, solo si sponsored == true
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private BrandEntity brand;

    @PodamExclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "professional_area",
        joinColumns = @JoinColumn(name = "professional_id"),
        inverseJoinColumns = @JoinColumn(name = "area_id")
    )
    private List<CoverageAreaEntity> coverageAreas;

    @PodamExclude
    @OneToMany(mappedBy = "professional")
    private List<ServiceEntity> services = new ArrayList<>();

    @PodamExclude
    @OneToOne(mappedBy = "professional", cascade = CascadeType.ALL, orphanRemoval = true)
    private AgendaEntity agenda;
}
