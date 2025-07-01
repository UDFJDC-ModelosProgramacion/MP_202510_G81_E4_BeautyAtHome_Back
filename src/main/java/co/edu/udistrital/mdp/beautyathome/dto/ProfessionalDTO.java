package co.edu.udistrital.mdp.beautyathome.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data   
public class ProfessionalDTO {
    private Long id;
    private String name;
    private String photoUrl;
    private String summary;
    private Boolean sponsored;
    private LocalDate birthDate;

    private Long brandId;
    private List<Long> coverageAreasIds;
    private List<Long> servicesIds;
    private Long agendaId;
}