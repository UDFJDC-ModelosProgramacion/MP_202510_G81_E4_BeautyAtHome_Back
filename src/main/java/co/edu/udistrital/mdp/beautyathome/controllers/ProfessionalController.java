package co.edu.udistrital.mdp.beautyathome.controllers;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.udistrital.mdp.beautyathome.dto.ProfessionalDTO;
import co.edu.udistrital.mdp.beautyathome.entities.ProfessionalEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.beautyathome.services.ProfessionalService;

@RestController
@RequestMapping("/professionals")

public class ProfessionalController {

    @Autowired
    private ProfessionalService professionalService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Método que utiliza ProfessionalService para crear un profesional
     * @param professionalDTO profesional a crear
     * @return una instancia de ProfessionalDTO
     * @throws EntityNotFoundException
     */
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public ProfessionalDTO create(@RequestBody ProfessionalDTO professionalDTO) throws EntityNotFoundException {
        ProfessionalEntity professionalEntity = professionalService.createProfessional(modelMapper.map(professionalDTO, ProfessionalEntity.class));
        return modelMapper.map(professionalEntity, ProfessionalDTO.class);
    }
    /**
     * Método que utiliza ProfessionalService para obtener todos los profesionales
     * @return Lista con todos los profesionalesDTO
     */
    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<ProfessionalDTO> findAll() {
        List<ProfessionalEntity> professionals = professionalService.getProfessionals();
        return modelMapper.map(professionals, new TypeToken<List<ProfessionalDTO>>() {}.getType());
    }
    
    /**
     * Metodo que utiliza ProfessionalService para obtener un profesional
     * @param id identificador del profesional
     * @return una instancia de ProfessionalDTO
     * @throws EntityNotFoundException
     */
    @GetMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ProfessionalDTO findById(@PathVariable("id") Long id) throws EntityNotFoundException {
        ProfessionalEntity professionalEntity = professionalService.getProfessional(id);
        return modelMapper.map(professionalEntity, ProfessionalDTO.class);
    }
    /**
     * Metodo que utiliza ProfessionalService para actualizar un profesional
     * @param id identificador del profesional
     * @return una instancia de ProfessionalDTO
     * @throws EntityNotFoundException
     */
    @PutMapping("/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ProfessionalDTO update(@PathVariable("id") Long id, @RequestBody ProfessionalDTO professionalDTO) throws EntityNotFoundException {
        ProfessionalEntity professionalEntity = professionalService.updateProfessional(id, modelMapper.map(professionalDTO, ProfessionalEntity.class));
        return modelMapper.map(professionalEntity, ProfessionalDTO.class);
    }
    /**
     * Método que utiliza ProfessionalService para eliminar un profesional
     * @param id identificador del profesional a eliminar
     * @throws EntityNotFoundException
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) throws EntityNotFoundException {
        professionalService.deleteProfessional(id);
    }
    
}
