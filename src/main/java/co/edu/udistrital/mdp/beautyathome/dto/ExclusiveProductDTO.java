package co.edu.udistrital.mdp.beautyathome.dto;

import lombok.Data;

@Data
public class ExclusiveProductDTO {
    private Long id;
    private String name;
    private String photo;
    private Double price;
    private Boolean available;
    private String category;
    private String description;
    private Long brandId;
}
