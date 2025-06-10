package co.edu.udistrital.mdp.beautyathome.dto;

import lombok.Data;

/**
 * Clase DTO que representa una review
 */
@Data
public class ReviewDTO {
    private Long id;
    private String opinion;
    private int stars;

    private Long serviceId;
    private Long clientId;
}
