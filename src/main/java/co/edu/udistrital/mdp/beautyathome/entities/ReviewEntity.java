package co.edu.udistrital.mdp.beautyathome.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity

public class ReviewEntity extends BaseEntity{
    //private ServicioEntity service;
    //private ClienteEntity client;
    private String opinion;
    private int stars;
}
