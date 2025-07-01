package co.edu.udistrital.mdp.beautyathome.dto;

import lombok.Data;

@Data
public class AppointmentDTO {
    private Long serviceId;
    private Long professionalId;
    private Long clientId;
    private Long agendaId;
}