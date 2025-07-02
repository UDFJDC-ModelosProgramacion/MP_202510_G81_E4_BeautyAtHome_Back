package co.edu.udistrital.mdp.beautyathome.dto;

import java.util.List;

import lombok.Data;

@Data
public class AgendaDetailDTO extends AgendaDTO{
    private List<AppointmentDetailDTO> appointments;
}