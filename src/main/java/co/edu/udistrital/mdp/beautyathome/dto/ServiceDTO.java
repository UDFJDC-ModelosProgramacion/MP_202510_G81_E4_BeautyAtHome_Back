package co.edu.udistrital.mdp.beautyathome.dto;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * Clase DTO que representa un servicio
 */
@Data
public class ServiceDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private Long professionalId;
    private Long brandId;
}