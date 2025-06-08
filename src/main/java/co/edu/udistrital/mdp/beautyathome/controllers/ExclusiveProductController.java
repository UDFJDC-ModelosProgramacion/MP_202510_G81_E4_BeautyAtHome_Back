// ExclusiveProductController.java
package co.edu.udistrital.mdp.beautyathome.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import co.edu.udistrital.mdp.beautyathome.dto.ExclusiveProductDTO;
import co.edu.udistrital.mdp.beautyathome.entities.BrandEntity;
import co.edu.udistrital.mdp.beautyathome.entities.ExclusiveProductEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.repositories.BrandRepository;
import co.edu.udistrital.mdp.beautyathome.services.ExclusiveProductService;
import jakarta.persistence.EntityNotFoundException;

@RestController
@RequestMapping("/exclusive-products")
public class ExclusiveProductController {

    @Autowired
    private ExclusiveProductService service;

    @Autowired
    private BrandRepository brandRepo;

    private ExclusiveProductDTO toDTO(ExclusiveProductEntity e) {
        ExclusiveProductDTO dto = new ExclusiveProductDTO();
        dto.setId(e.getId());
        dto.setName(e.getName());
        dto.setPhoto(e.getPhoto());
        dto.setPrice(e.getPrice());
        dto.setAvailable(e.getAvailable());
        dto.setCategory(e.getCategory());
        dto.setDescription(e.getDescription());
        dto.setBrandId(e.getBrand().getId());
        return dto;
    }

    private ExclusiveProductEntity toEntity(ExclusiveProductDTO dto) {
        ExclusiveProductEntity e = new ExclusiveProductEntity() {};
        e.setName(dto.getName());
        e.setPhoto(dto.getPhoto());
        e.setPrice(dto.getPrice());
        e.setAvailable(dto.getAvailable());
        e.setCategory(dto.getCategory());
        e.setDescription(dto.getDescription());
        BrandEntity b = brandRepo.findById(dto.getBrandId())
                .orElseThrow(() -> new EntityNotFoundException("Brand with id " + dto.getBrandId() + " not found"));
        e.setBrand(b);
        return e;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ExclusiveProductDTO create(@RequestBody ExclusiveProductDTO dto) throws IllegalOperationException {
        ExclusiveProductEntity created = service.createExclusiveProduct(toEntity(dto));
        return toDTO(created);
    }

    @GetMapping
    public List<ExclusiveProductDTO> list() {
        return service.getAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ExclusiveProductDTO getOne(@PathVariable Long id) {
        return toDTO(service.getOne(id));
    }

    @PutMapping("/{id}")
    public ExclusiveProductDTO update(@PathVariable Long id, @RequestBody ExclusiveProductDTO dto) throws IllegalOperationException {
        ExclusiveProductEntity updated = service.updateExclusiveProduct(id, toEntity(dto));
        return toDTO(updated);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.deleteExclusiveProduct(id);
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
