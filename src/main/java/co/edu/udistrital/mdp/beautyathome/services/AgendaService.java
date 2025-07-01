package co.edu.udistrital.mdp.beautyathome.services;

import co.edu.udistrital.mdp.beautyathome.dto.AgendaRequestDTO;
import co.edu.udistrital.mdp.beautyathome.dto.AgendaResponseDTO;
import co.edu.udistrital.mdp.beautyathome.entities.AgendaEntity;
import co.edu.udistrital.mdp.beautyathome.repositories.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    public AgendaResponseDTO createAgenda(AgendaRequestDTO requestDTO) {
        AgendaEntity entity = new AgendaEntity();
        entity.setProfessionalId(requestDTO.getProfessionalId());
        entity.setAvailableDates(requestDTO.getAvailableDates());
        entity = agendaRepository.save(entity);

        return new AgendaResponseDTO(entity.getId(), entity.getProfessionalId(), entity.getAvailableDates());
    }

    public List<AgendaResponseDTO> getAllAgendas() {
        return agendaRepository.findAll().stream()
                .map(a -> new AgendaResponseDTO(a.getId(), a.getProfessionalId(), a.getAvailableDates()))
                .collect(Collectors.toList());
    }
}

