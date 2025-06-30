package co.edu.udistrital.mdp.beautyathome.controllers;

import co.edu.udistrital.mdp.beautyathome.dto.AgendaRequestDTO;
import co.edu.udistrital.mdp.beautyathome.dto.AgendaResponseDTO;
import co.edu.udistrital.mdp.beautyathome.services.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/agendas")
public class AgendaController {

    @Autowired
    private AgendaService agendaService;

    @PostMapping
    public AgendaResponseDTO createAgenda(@RequestBody AgendaRequestDTO requestDTO) {
        return agendaService.createAgenda(requestDTO);
    }

    @GetMapping
    public List<AgendaResponseDTO> getAllAgendas() {
        return agendaService.getAllAgendas();
    }
}
