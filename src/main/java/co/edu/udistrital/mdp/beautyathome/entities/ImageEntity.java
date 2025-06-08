package co.edu.udistrital.mdp.beautyathome.entities;

import jakarta.persistence.Entity;
import lombok.Data;


@Data
@Entity
public abstract class ImageEntity extends BaseEntity {
    private String url;
}
