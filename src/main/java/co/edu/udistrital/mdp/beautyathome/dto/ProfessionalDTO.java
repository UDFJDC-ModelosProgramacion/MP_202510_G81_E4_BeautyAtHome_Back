package co.edu.udistrital.mdp.beautyathome.dto;

import java.sql.Date;

import lombok.Data;

@Data   

public class ProfessionalDTO {
    private Long id;
    private String name,photo,professionalSummary;
    private Date birthDate;

    //private AgendaDTO agenda;
}
