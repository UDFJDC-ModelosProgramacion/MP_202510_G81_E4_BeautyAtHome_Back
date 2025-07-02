package co.edu.udistrital.mdp.beautyathome.controllers;

import co.edu.udistrital.mdp.beautyathome.dto.AppointmentDTO;
import co.edu.udistrital.mdp.beautyathome.dto.AppointmentViewDTO;
import co.edu.udistrital.mdp.beautyathome.entities.AppointmentEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.services.AppointmentService;
import jakarta.persistence.EntityNotFoundException;

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
    public List<AppointmentViewDTO> findAll() {
        List<AppointmentEntity> appointments = appointmentService.getAppointments();
        	    return modelMapper.map(appointments, new TypeToken<List<AppointmentViewDTO>>() {
	    }.getType());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public AppointmentViewDTO findOne(@PathVariable Long id) throws EntityNotFoundException, co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException{
        return appointmentService.getAppointment(id);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public AppointmentDTO update(@PathVariable Long id, @RequestBody AppointmentDTO appointmentDTO) throws IllegalOperationException, co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException{
        AppointmentEntity appointmentEntity = appointmentService.updateAppointment(id, modelMapper.map(appointmentDTO, AppointmentEntity.class));
        return modelMapper.map(appointmentEntity, AppointmentDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws EntityNotFoundException, co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException{
        appointmentService.deleteAppointment(id);
    }
}

