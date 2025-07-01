package co.edu.udistrital.mdp.beautyathome.services;

import co.edu.udistrital.mdp.beautyathome.entities.AppointmentEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Transactional
    public AppointmentEntity createAppointment(AppointmentEntity appointmentEntity) throws IllegalOperationException {
        if(appointmentEntity.getScheduledAt() == null){
            throw new IllegalOperationException("The scheuled date is not valid");
        }
        if(appointmentEntity.getService() == null){
            throw new IllegalOperationException("The client is not valid");
        }
        if(appointmentEntity.getProfessional() == null){
            throw new IllegalOperationException("The professional is not valid");
        }
        if(appointmentEntity.getClient() == null){
            throw new IllegalOperationException("The client is not valid");
        }
        if(appointmentEntity.getAgenda() == null){
            throw new IllegalOperationException("The agenda is not valid");
        }
        return appointmentRepository.save(appointmentEntity);
    }

    @Transactional
    public List<AppointmentEntity> getAppointments() {
        return appointmentRepository.findAll();
    }
}

