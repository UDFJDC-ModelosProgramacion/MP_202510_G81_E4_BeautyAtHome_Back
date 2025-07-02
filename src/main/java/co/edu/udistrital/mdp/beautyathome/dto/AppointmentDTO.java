package co.edu.udistrital.mdp.beautyathome.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AppointmentDTO{
    private Long id;
    private String coverageAreaName;
    private LocalDateTime scheduledAt;
    private Long serviceId;
    private Long professionalId;
    private Long clientId;
    private Long agendaId;
}