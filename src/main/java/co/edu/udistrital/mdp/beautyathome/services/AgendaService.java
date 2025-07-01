package co.edu.udistrital.mdp.beautyathome.services;

import co.edu.udistrital.mdp.beautyathome.entities.AgendaEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.repositories.AgendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AgendaService {

    @Autowired
    private AgendaRepository agendaRepository;

    @Transactional
    public AgendaEntity createAgenda(AgendaEntity agendaEntity) throws IllegalOperationException {
        if(agendaEntity.getProfessional() == null){
            throw new IllegalOperationException("The professional is not valid");
        }
        if(agendaEntity.getAppointments() == null){
            throw new IllegalOperationException("The appointments are not valid");
        }
        return agendaRepository.save(agendaEntity);
    }

    @Transactional
    public List<AgendaEntity> getAgendas() {
        return agendaRepository.findAll();
    }
}

