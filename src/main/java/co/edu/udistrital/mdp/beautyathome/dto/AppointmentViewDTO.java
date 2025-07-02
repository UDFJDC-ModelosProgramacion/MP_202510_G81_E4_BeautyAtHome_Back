package co.edu.udistrital.mdp.beautyathome.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class AppointmentViewDTO {
    private String clientName;
    private String coverageAreaName;
    private String serviceName;
    private String professionalName;
    private LocalDateTime date;
}

