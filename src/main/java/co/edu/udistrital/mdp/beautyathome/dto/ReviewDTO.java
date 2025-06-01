package co.edu.udistrital.mdp.beautyathome.dto;

import lombok.Data;

@Data
public class ReviewDTO {
    private Long id;
    private String opinion;
    private int stars;

    private Long serviceId;
    private Long clientId;
}
