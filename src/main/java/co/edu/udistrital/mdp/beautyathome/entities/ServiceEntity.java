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

    private String name;
    private String description;
    private Date completionDate;
    private Double price;

    @PodamExclude
    @OneToMany(mappedBy = "service")
    private List<ReviewEntity> reviews;

    @PodamExclude
    @ManyToOne
    private ProfessionalEntity professional;
}
