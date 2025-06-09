package co.edu.udistrital.mdp.beautyathome.dto;

import java.util.List;

import lombok.Data;

@Data

public class ProfessionalDetailDTO extends ProfessionalDTO{
    private List<ServiceDTO> services = new java.util.ArrayList<>();
}
