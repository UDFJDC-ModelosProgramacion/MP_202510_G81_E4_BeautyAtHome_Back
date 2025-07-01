package co.edu.udistrital.mdp.beautyathome.controllers;

import co.edu.udistrital.mdp.beautyathome.dto.AgendaDetailDTO;
import co.edu.udistrital.mdp.beautyathome.dto.ReviewDetailDTO;
import co.edu.udistrital.mdp.beautyathome.entities.AgendaEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.dto.AgendaDTO;
import co.edu.udistrital.mdp.beautyathome.services.AgendaService;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
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
    public AgendaDTO createAgenda(@RequestBody AgendaDetailDTO requestDTO) throws IllegalOperationException {
        AgendaEntity agendaEntity = agendaService.createAgenda(modelMapper.map(requestDTO, AgendaEntity.class));
        return modelMapper.map(agendaEntity, AgendaDTO.class);
    }

    @GetMapping
    public List<AgendaDetailDTO> getAllAgendas() {
        List<AgendaEntity> agendas = agendaService.getAgendas();
        return modelMapper.map(agendas, new TypeToken<List<AgendaDetailDTO>>() {
	    }.getType());
    }
}
