package co.edu.udistrital.mdp.beautyathome.dto;

import java.util.List;

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
    private List<String> referenceImagesUrls;
    private Long professionalId;
    private Long brandId;
}