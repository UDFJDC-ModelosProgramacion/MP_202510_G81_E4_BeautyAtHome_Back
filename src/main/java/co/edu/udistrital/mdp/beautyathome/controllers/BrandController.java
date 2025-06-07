package co.edu.udistrital.mdp.beautyathome.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.udistrital.mdp.beautyathome.dto.BrandDTO;
import co.edu.udistrital.mdp.beautyathome.entities.BrandEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.services.BrandService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    private BrandDTO toDTO(BrandEntity e) {
        BrandDTO dto = new BrandDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setPhotograph(e.getPhotograph());
        dto.setPrice(e.getPrice());
        dto.setAvailable(e.getAvailable());
        dto.setCategory(e.getCategory());
        dto.setDescription(e.getDescription());
        dto.setProduct(e.getProduct());
        return dto;
    }

    private BrandEntity toEntity(BrandDTO dto) {
        BrandEntity e = new BrandEntity();
        e.setName(dto.getName());
        e.setPhotograph(dto.getPhotograph());
        e.setPrice(dto.getPrice());
        e.setAvailable(dto.getAvailable());
        e.setCategory(dto.getCategory());
        e.setDescription(dto.getDescription());
        e.setProduct(dto.getProduct());
        return e;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BrandDTO create(@RequestBody BrandDTO dto) throws IllegalOperationException {
        BrandEntity created = brandService.createBrand(toEntity(dto));
        return toDTO(created);
    }

    @GetMapping
    public List<BrandDTO> list() {
        return brandService.getBrands().stream()
                .map(this::toDTO)
                .toList();
    }

    @GetMapping("/{id}")
    public BrandDTO getOne(@PathVariable Long id) {
        BrandEntity e = brandService.getBrand(id);
        return toDTO(e);
    }

    @PutMapping("/{id}")
    public BrandDTO update(@PathVariable Long id, @RequestBody BrandDTO dto) throws IllegalOperationException {
        BrandEntity updated = brandService.updateBrand(id, toEntity(dto));
        return toDTO(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        brandService.deleteBrand(id);
    }

    @ExceptionHandler(IllegalOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleBadRequest(IllegalOperationException e) {
        return e.getMessage();
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleNotFound(EntityNotFoundException e) {
        return e.getMessage();
    }
}
