package co.edu.udistrital.mdp.beautyathome.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class ServiceDTO {
    private Long id;
    private String name;
    private String description;
    private Date completionDate;
    private Double price;
    //private BrandDTO brand;
    private ProfessionalDTO professional;

}
