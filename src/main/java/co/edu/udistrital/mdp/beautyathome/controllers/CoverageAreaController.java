package co.edu.udistrital.mdp.beautyathome.controllers;

import co.edu.udistrital.mdp.beautyathome.dto.CoverageAreaDTO;
import co.edu.udistrital.mdp.beautyathome.dto.CoverageAreaDetailDTO;
import co.edu.udistrital.mdp.beautyathome.entities.CoverageAreaEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.services.CoverageAreaService;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coverage-areas")
public class CoverageAreaController {

    @Autowired
    private CoverageAreaService coverageAreaService;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public CoverageAreaDTO create(@RequestBody CoverageAreaDTO coverageAreaDTO) throws IllegalOperationException {
        CoverageAreaEntity coverageAreaEntity = coverageAreaService.createCoverageArea(modelMapper.map(coverageAreaDTO, CoverageAreaEntity.class));     
        return modelMapper.map(coverageAreaEntity, CoverageAreaDTO.class);
    }

    @GetMapping
    @ResponseStatus(code = HttpStatus.OK)
    public List<CoverageAreaDetailDTO> findAll() {
        List<CoverageAreaEntity> coverageAreas = coverageAreaService.getCoverageAreas();
        return modelMapper.map(coverageAreas, new TypeToken<List<CoverageAreaDetailDTO>>() {
	    }.getType());
    }
}

