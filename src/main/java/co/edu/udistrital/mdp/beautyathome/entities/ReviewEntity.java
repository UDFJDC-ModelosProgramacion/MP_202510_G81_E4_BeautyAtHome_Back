package co.edu.udistrital.mdp.beautyathome.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity

public class ReviewEntity extends BaseEntity{

    private String opinion;
    private int stars;

    @PodamExclude
    @ManyToOne
    private ServiceEntity service;

    @PodamExclude
    @ManyToOne
    private ClientEntity client;

}
