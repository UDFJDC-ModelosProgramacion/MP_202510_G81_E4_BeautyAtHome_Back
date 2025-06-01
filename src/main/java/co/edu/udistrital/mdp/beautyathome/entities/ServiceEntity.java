package co.edu.udistrital.mdp.beautyathome.entities;

import java.sql.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity

public class ServiceEntity extends BaseEntity {

    @OneToMany(mappedBy = "service")
    private List<ReviewEntity> reviews;
    private String name;
    private String description;
    private Date completionDate;
    private Double price;
}
