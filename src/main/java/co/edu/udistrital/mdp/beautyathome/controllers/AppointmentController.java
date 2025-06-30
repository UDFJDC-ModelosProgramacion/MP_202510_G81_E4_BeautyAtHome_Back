package co.edu.udistrital.mdp.beautyathome.controllers;

import co.edu.udistrital.mdp.beautyathome.dto.AppointmentRequestDTO;
import co.edu.udistrital.mdp.beautyathome.dto.AppointmentResponseDTO;
import co.edu.udistrital.mdp.beautyathome.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @PostMapping
    public AppointmentResponseDTO createAppointment(@RequestBody AppointmentRequestDTO requestDTO) {
        return appointmentService.createAppointment(requestDTO);
    }

    @GetMapping
    public List<AppointmentResponseDTO> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }
}

