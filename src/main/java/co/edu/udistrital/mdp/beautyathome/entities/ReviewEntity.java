package co.edu.udistrital.mdp.beautyathome.entities;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamIntValue;

@Data
@Entity

public class ReviewEntity extends BaseEntity{
    @PodamExclude
    @OneToOne(fetch = FetchType.LAZY, optional = false) 
    @JoinColumn(name = "service_record_id", nullable = false, unique = true)
    private ServiceRecordEntity serviceRecord;

    @PodamExclude
    @ManyToOne(optional = false)
    private ClientEntity client;

    @Column(length = 1000, nullable = false)
    private String opinion;

    //El valor aleatorio asignado por Podam para las pruebas unitarias irá desde 1 hasta 5
    @PodamIntValue(minValue = 1, maxValue = 5)
    @Min(1)
    @Max(5)
    @Column(nullable = false)
    private int stars;
}
