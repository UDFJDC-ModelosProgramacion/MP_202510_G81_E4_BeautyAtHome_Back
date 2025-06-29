package co.edu.udistrital.mdp.beautyathome.dto;

import lombok.Data;

@Data // Anotación de Lombok para generar getters, setters, toString, equals, hashCode
public class BrandDTO {

    private Long id;
    private String name;
    private String logoURL;

}