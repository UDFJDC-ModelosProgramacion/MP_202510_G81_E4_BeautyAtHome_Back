package co.edu.udistrital.mdp.beautyathome.dto;

import lombok.Data;

/**
 * Clase DetailDTO que representa una review detallada
 */
@Data
public class ReviewDetailDTO extends ReviewDTO{
    private Long id;
    private ServiceRecordDetailDTO serviceRecordDetail;
    private ClientDTO client;
}
