package co.edu.udistrital.mdp.beautyathome.controllers;

import co.edu.udistrital.mdp.beautyathome.dto.AgendaDetailDTO;
import co.edu.udistrital.mdp.beautyathome.entities.AgendaEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.dto.AgendaDTO;
import co.edu.udistrital.mdp.beautyathome.services.AgendaService;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendas")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public AgendaDTO create(@RequestBody AgendaDetailDTO requestDTO) throws IllegalOperationException {
        AgendaEntity agendaEntity = agendaService.createAgenda(modelMapper.map(requestDTO, AgendaEntity.class));
        return modelMapper.map(agendaEntity, AgendaDTO.class);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<AgendaDetailDTO> findAll() {
        List<AgendaEntity> agendas = agendaService.getAgendas();
        return modelMapper.map(agendas, new TypeToken<List<AgendaDetailDTO>>() {
	    }.getType());
    }

    @GetMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public AgendaDTO findOne(@RequestBody AgendaDTO agendaDTO) throws EntityNotFoundException{
        AgendaEntity agendaEntity = agendaService.getAgenda(agendaDTO.getId());
        return modelMapper.map(agendaEntity, AgendaDTO.class);
    }
}
