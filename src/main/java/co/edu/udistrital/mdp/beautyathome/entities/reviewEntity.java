package co.edu.udistrital.mdp.beautyathome.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity
public class reviewEntity extends BaseEntity {

    private String opinion;
    private int stars;

}
