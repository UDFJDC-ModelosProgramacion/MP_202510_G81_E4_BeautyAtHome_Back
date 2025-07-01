package co.edu.udistrital.mdp.beautyathome.dto;

import java.util.List;

import lombok.Data;

@Data

public class ProfessionalDetailDTO extends ProfessionalDTO{
    private BrandDetailDTO brandDetail;
    private List<CoverageAreaDetailDTO> coverageAreasDetail;
    private List<ServiceDetailDTO> servicesDetail;
    private AgendaDetailDTO agendaDetail;
}