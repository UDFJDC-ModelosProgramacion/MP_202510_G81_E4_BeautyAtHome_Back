package co.edu.udistrital.mdp.beautyathome.dto;

import java.util.List;

import lombok.Data;

@Data
public class AgendaDetailDTO extends AgendaDTO{
    private ProfessionalDetailDTO professionalDetail;
    private List<AppointmentDetailDTO> appointments;
}