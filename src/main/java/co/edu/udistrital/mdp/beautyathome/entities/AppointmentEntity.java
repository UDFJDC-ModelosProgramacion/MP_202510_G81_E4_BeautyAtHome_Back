package co.edu.udistrital.mdp.beautyathome.entities;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class AppointmentEntity extends BaseEntity{

    private String coverageAreaName;
    private LocalDateTime scheduledAt;

    // Servicio agendado
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "service_id")
    private ServiceEntity service;

    // Profesional al que se agenda la cita
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "professional_id")
    private ProfessionalEntity professional;

    // Cliente que agenda el servicio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")   
    private ClientEntity client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agenda_id")
    private AgendaEntity agenda;
}
