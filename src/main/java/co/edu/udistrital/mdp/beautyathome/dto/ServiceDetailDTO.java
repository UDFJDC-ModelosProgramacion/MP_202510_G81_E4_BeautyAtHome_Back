package co.edu.udistrital.mdp.beautyathome.dto;

import java.util.List;

import lombok.Data;

@Data

public class ServiceDetailDTO extends ServiceDTO{
    private BrandDTO brand;
    private ProfessionalDTO professional;
    List<ReviewDetailDTO> reviews = new java.util.ArrayList<>();
}
