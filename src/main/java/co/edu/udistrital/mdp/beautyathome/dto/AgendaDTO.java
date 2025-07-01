package co.edu.udistrital.mdp.beautyathome.dto;

import java.util.List;

import lombok.Data;

@Data
public class AgendaDTO {
    private Long id;
    private Long professionalId;
    private List<Long> appointmentIds;
}