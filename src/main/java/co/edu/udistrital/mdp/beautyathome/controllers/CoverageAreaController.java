package co.edu.udistrital.mdp.beautyathome.controllers;

import co.edu.udistrital.mdp.beautyathome.dto.CoverageAreaRequestDTO;
import co.edu.udistrital.mdp.beautyathome.dto.CoverageAreaResponseDTO;
import co.edu.udistrital.mdp.beautyathome.services.CoverageAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coverage-areas")
public class CoverageAreaController {

    @Autowired
    private CoverageAreaService coverageAreaService;

    @PostMapping
    public CoverageAreaResponseDTO create(@RequestBody CoverageAreaRequestDTO requestDTO) {
        return coverageAreaService.create(requestDTO);
    }

    @GetMapping
    public List<CoverageAreaResponseDTO> getAll() {
        return coverageAreaService.getAll();
    }
}

