package co.edu.udistrital.mdp.beautyathome.dto;

import lombok.Data;

@Data
public class BrandDTO {
    private Long id;
    private String name;
    private String photograph;
    private Double price;
    private Boolean available;
    private String category;
    private String description;
    private String product;
}
