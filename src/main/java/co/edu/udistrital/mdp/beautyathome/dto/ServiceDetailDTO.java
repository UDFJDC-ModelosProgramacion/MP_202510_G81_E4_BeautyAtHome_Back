package co.edu.udistrital.mdp.beautyathome.dto;

import java.util.List;

import lombok.Data;

@Data

public class ServiceDetailDTO extends ServiceDTO{
    private BrandDTO brand;
    private ProfessionalDTO professional;
    private List<ReviewDetailDTO> reviews = new java.util.ArrayList<>();
}
