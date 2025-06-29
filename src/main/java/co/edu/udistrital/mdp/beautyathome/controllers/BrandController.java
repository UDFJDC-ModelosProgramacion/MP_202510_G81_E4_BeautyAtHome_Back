package co.edu.udistrital.mdp.beautyathome.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired; // Nueva importación para inyección de dependencia
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper; // Importación de ModelMapper
import org.modelmapper.TypeToken; // Importación de TypeToken
import co.edu.udistrital.mdp.beautyathome.dto.BrandDTO;
import co.edu.udistrital.mdp.beautyathome.dto.BrandDetailDTO; // Importación de BrandDetailDTO
import co.edu.udistrital.mdp.beautyathome.entities.BrandEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.services.BrandService;
import jakarta.persistence.EntityNotFoundException;

/**
 * Controlador REST para manejar operaciones relacionadas con marcas.
 * Expone endpoints para el CRUD de marcas y manejo de excepciones.
 */
@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/brands") // Mapea todas las rutas de este controlador a /brands
public class BrandController {

    private final BrandService brandService;
    private final ModelMapper modelMapper; // Inyección de ModelMapper

    /**
     * Constructor para inyección de dependencias.
     * @param brandService Servicio de marcas.
     * @param modelMapper Instancia de ModelMapper.
     */
    @Autowired // La inyección de dependencias por constructor es la forma preferida en Spring
    public BrandController(BrandService brandService, ModelMapper modelMapper) {
        this.brandService = brandService;
        this.modelMapper = modelMapper;
    }

    /**
     * Endpoint para crear una nueva marca.
     * @param dto DTO con los datos de la marca a crear.
     * @return DTO de la marca creada.
     * @throws IllegalOperationException Si hay errores de validación.
     */
    @PostMapping // Mapea solicitudes POST a /brands
    @ResponseStatus(HttpStatus.CREATED) // Retorna código 201 (CREATED)
    public BrandDTO create(@RequestBody BrandDTO dto) throws IllegalOperationException {
        // Convierte el DTO a entidad, crea la marca y convierte el resultado a DTO
        BrandEntity created = brandService.createBrand(modelMapper.map(dto, BrandEntity.class));
        return modelMapper.map(created, BrandDTO.class);
    }

    /**
     * Endpoint para listar todas las marcas.
     * Retorna una lista de BrandDetailDTO.
     * @return Lista de DTOs de marcas con detalle.
     */
    @GetMapping // Mapea solicitudes GET a /brands
    @ResponseStatus(HttpStatus.OK) // Retorna código 200 (OK)
    public List<BrandDetailDTO> findAll() { // Renombrado de 'list' a 'findAll'
        List<BrandEntity> brands = brandService.getBrands();
        // Convierte la lista de entidades a lista de BrandDetailDTO
        return modelMapper.map(brands, new TypeToken<List<BrandDetailDTO>>() {}.getType());
    }

    /**
     * Endpoint para obtener una marca específica por ID.
     * Retorna un BrandDetailDTO.
     * @param id ID de la marca a buscar.
     * @return DTO de la marca encontrada con detalle.
     * @throws EntityNotFoundException Si no se encuentra la marca.
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) // Retorna código 200 (OK)
    public BrandDetailDTO findOne(@PathVariable Long id) throws EntityNotFoundException { // Renombrado de 'getOne' a 'findOne'
        BrandEntity e = brandService.getBrand(id);
        // Convierte la entidad a BrandDetailDTO
        return modelMapper.map(e, BrandDetailDTO.class);
    }

    /**
     * Endpoint para actualizar una marca existente.
     * @param id ID de la marca a actualizar.
     * @param dto DTO con los nuevos datos de la marca.
     * @return DTO de la marca actualizada.
     * @throws IllegalOperationException Si hay errores de validación.
     * @throws EntityNotFoundException Si no se encuentra la marca.
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) // Retorna código 200 (OK)
    public BrandDTO update(@PathVariable Long id, @RequestBody BrandDTO dto) throws IllegalOperationException, EntityNotFoundException {
        // Convierte el DTO a entidad, actualiza la marca y convierte el resultado a DTO
        BrandEntity updated = brandService.updateBrand(id, modelMapper.map(dto, BrandEntity.class));
        return modelMapper.map(updated, BrandDTO.class);
    }

    /**
     * Endpoint para eliminar una marca.
     * @param id ID de la marca a eliminar.
     * @throws EntityNotFoundException Si no se encuentra la marca.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Retorna código 204 (NO_CONTENT)
    public void delete(@PathVariable Long id) throws EntityNotFoundException {
        brandService.deleteBrand(id); // Elimina la marca con el ID proporcionado
    }

    /**
     * Manejador de excepciones para operaciones ilegales.
     * @param e Excepción capturada.
     * @return Mensaje de error.
     */
    @ExceptionHandler(IllegalOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Retorna código 400 (BAD_REQUEST)
    public String handleBadRequest(IllegalOperationException e) {
        return e.getMessage();
    }

    /**
     * Manejador de excepciones para entidades no encontradas.
     * @param e Excepción capturada.
     * @return Mensaje de error.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // Retorna código 404 (NOT_FOUND)
    public String handleNotFound(EntityNotFoundException e) {
        return e.getMessage();
    }
}