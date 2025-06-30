package co.edu.udistrital.mdp.beautyathome.services;

import co.edu.udistrital.mdp.beautyathome.dto.AppointmentRequestDTO;
import co.edu.udistrital.mdp.beautyathome.dto.AppointmentResponseDTO;
import co.edu.udistrital.mdp.beautyathome.entities.AppointmentEntity;
import co.edu.udistrital.mdp.beautyathome.repositories.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO requestDTO) {
        AppointmentEntity entity = new AppointmentEntity();
        entity.setClientId(requestDTO.getClientId());
        entity.setDate(requestDTO.getDate());
        entity.setServiceId(requestDTO.getServiceId());
        appointmentRepository.save(entity);
        return new AppointmentResponseDTO(entity.getId(), entity.getClientId(), entity.getDate(), entity.getServiceId());
    }

    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(a -> new AppointmentResponseDTO(a.getId(), a.getClientId(), a.getDate(), a.getServiceId()))
                .collect(Collectors.toList());
    }
}

