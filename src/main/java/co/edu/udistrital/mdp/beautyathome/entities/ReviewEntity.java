package co.edu.udistrital.mdp.beautyathome.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity

public class ReviewEntity {

    private ClientEntity client;
    private ServiceEntity service;
    private String comment;
    private int stars;

}
