package co.edu.udistrital.mdp.beautyathome.dto;

import lombok.Data;

@Data
public class AppointmentDetailDTO extends AppointmentDTO{
    private ServiceDetailDTO serviceDetail;
    private ProfessionalDetailDTO professionalDetail;
    private ClientDetailDTO clientDetail;
    private AgendaDetailDTO agendaDetail;
}
