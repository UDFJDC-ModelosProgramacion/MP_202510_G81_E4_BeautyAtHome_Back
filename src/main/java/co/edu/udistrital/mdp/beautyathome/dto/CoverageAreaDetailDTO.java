package co.edu.udistrital.mdp.beautyathome.dto;

import java.util.List;

import lombok.Data;

@Data
public class CoverageAreaDetailDTO extends CoverageAreaDTO{
    private List<ProfessionalDetailDTO> professionals;
}
