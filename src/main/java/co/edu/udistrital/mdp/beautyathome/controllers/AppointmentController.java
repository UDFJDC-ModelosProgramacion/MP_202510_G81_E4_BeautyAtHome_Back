package co.edu.udistrital.mdp.beautyathome.controllers;

import co.edu.udistrital.mdp.beautyathome.dto.AppointmentDTO;
import co.edu.udistrital.mdp.beautyathome.dto.AppointmentDetailDTO;
import co.edu.udistrital.mdp.beautyathome.dto.ReviewDetailDTO;
import co.edu.udistrital.mdp.beautyathome.entities.AppointmentEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.services.AppointmentService;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public AppointmentDTO create(@RequestBody AppointmentDTO requestDTO) throws IllegalOperationException {
        AppointmentEntity appointmentEntity = appointmentService.createAppointment(modelMapper.map(requestDTO, AppointmentEntity.class));
        return modelMapper.map(appointmentEntity, AppointmentDTO.class);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<AppointmentDetailDTO> findAll() {
        List<AppointmentEntity> appointments = appointmentService.getAppointments();
        	    return modelMapper.map(appointments, new TypeToken<List<AppointmentDetailDTO>>() {
	    }.getType());
    }
}

