package co.edu.udistrital.mdp.beautyathome.entities;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.Data;

@Data
@Entity

/**
 * Entidad JPA para las áreas de covertura
 */
public class CoverageAreaEntity extends BaseEntity{

    private String name;

    @ManyToMany(mappedBy = "coverageAreas")
    private Set<ProfessionalEntity> professionals;
}
