package co.edu.udistrital.mdp.beautyathome.dto;

import java.sql.Date;

import lombok.Data;

/**
 * Clase DTO que representa un servicio
 */
@Data
public class ServiceDTO {
    private String name;
    private String description;
    private Date completionDate;
    private Double price;
}
