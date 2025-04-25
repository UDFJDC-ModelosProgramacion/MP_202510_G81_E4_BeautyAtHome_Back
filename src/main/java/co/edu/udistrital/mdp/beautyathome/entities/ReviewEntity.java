package co.edu.udistrital.mdp.beautyathome.entities;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamIntValue;

@Data
@Entity

public class ReviewEntity extends BaseEntity{
    @PodamExclude
    @ManyToOne(optional = false)
    private ServiceEntity service;

    @PodamExclude
    @ManyToOne(optional = false)
    private ClientEntity client;

    @Column(nullable = false)
    private String opinion;

    //El valor aleatorio asignado por Podam para las pruebas unitarias irá desde 1 hasta 5
    @PodamIntValue(minValue = 1, maxValue = 5)
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private int stars;
}
