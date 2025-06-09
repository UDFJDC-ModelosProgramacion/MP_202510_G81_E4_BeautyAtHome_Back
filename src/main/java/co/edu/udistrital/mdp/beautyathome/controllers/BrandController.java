// Paquete donde se encuentra la clase
package co.edu.udistrital.mdp.beautyathome.controllers;

// Importaciones necesarias
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import co.edu.udistrital.mdp.beautyathome.dto.BrandDTO;
import co.edu.udistrital.mdp.beautyathome.entities.BrandEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.services.BrandService;
import jakarta.persistence.EntityNotFoundException;

/**
 * Controlador REST para manejar operaciones relacionadas con marcas (brands).
 * Todas las rutas comienzan con "/brands".
 */
@RestController
@RequestMapping("/brands")
public class BrandController {

    // Servicio que contiene la lógica de negocio para las marcas
    private final BrandService brandService;

    /**
     * Constructor que realiza la inyección de dependencias del servicio.
     * @param brandService Servicio de marcas que se inyecta automáticamente
     */
    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    /**
     * Metodo auxiliar para convertir una entidad BrandEntity a un DTO BrandDTO.
     * @param e Entidad BrandEntity a convertir
     * @return DTO BrandDTO con los datos de la entidad
     */
    private BrandDTO toDTO(BrandEntity e) {
        BrandDTO dto = new BrandDTO();
        dto.setId(e.getId());          // Copia el ID de la entidad al DTO
        dto.setName(e.getName());      // Copia el nombre de la entidad al DTO
        dto.setLogoURL(e.getLogoURL());// Copia la URL del logo de la entidad al DTO
        return dto;
    }

    /**
     * Metodo auxiliar para convertir un DTO BrandDTO a una entidad BrandEntity.
     * @param dto DTO BrandDTO a convertir
     * @return Entidad BrandEntity con los datos del DTO
     */
    private BrandEntity toEntity(BrandDTO dto) {
        BrandEntity e = new BrandEntity();
        e.setName(dto.getName());      // Copia el nombre del DTO a la entidad
        e.setLogoURL(dto.getLogoURL());// Copia la URL del logo del DTO a la entidad
        return e;
    }

    /**
     * Endpoint POST para crear una nueva marca.
     * @param dto DTO con los datos de la marca a crear
     * @return DTO de la marca creada
     * @throws IllegalOperationException Si la operación no es válida
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Retorna código 201 (CREATED) al crear exitosamente
    public BrandDTO create(@RequestBody BrandDTO dto) throws IllegalOperationException {
        // Convierte el DTO a entidad, crea la marca y convierte el resultado a DTO
        BrandEntity created = brandService.createBrand(toEntity(dto));
        return toDTO(created);
    }

    /**
     * Endpoint GET para listar todas las marcas.
     * @return Lista de DTOs de todas las marcas
     */
    @GetMapping
    public List<BrandDTO> list() {
        // Obtiene todas las marcas, las convierte a DTO y las devuelve como lista
        return brandService.getBrands().stream()
                .map(this::toDTO)
                .toList();
    }

    /**
     * Endpoint GET para obtener una marca específica por su ID.
     * @param id ID de la marca a buscar
     * @return DTO de la marca encontrada
     */
    @GetMapping("/{id}")
    public BrandDTO getOne(@PathVariable Long id) {
        // Obtiene la marca por ID y la convierte a DTO
        BrandEntity e = brandService.getBrand(id);
        return toDTO(e);
    }

    /**
     * Endpoint PUT para actualizar una marca existente.
     * @param id ID de la marca a actualizar
     * @param dto DTO con los nuevos datos de la marca
     * @return DTO de la marca actualizada
     * @throws IllegalOperationException Si la operación no es válida
     */
    @PutMapping("/{id}")
    public BrandDTO update(@PathVariable Long id, @RequestBody BrandDTO dto) throws IllegalOperationException {
        // Actualiza la marca con el ID proporcionado y los nuevos datos
        BrandEntity updated = brandService.updateBrand(id, toEntity(dto));
        return toDTO(updated);
    }

    /**
     * Endpoint DELETE para eliminar una marca por su ID.
     * @param id ID de la marca a eliminar
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Retorna código 204 (NO_CONTENT) al eliminar exitosamente
    public void delete(@PathVariable Long id) {
        brandService.deleteBrand(id); // Elimina la marca con el ID proporcionado
    }

    /**
     * Manejador de excepciones para IllegalOperationException.
     * @param e Excepción capturada
     * @return Mensaje de error de la excepción
     */
    @ExceptionHandler(IllegalOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Retorna código 400 (BAD_REQUEST)
    public String handleBadRequest(IllegalOperationException e) {
        return e.getMessage();
    }

    /**
     * Manejador de excepciones para EntityNotFoundException.
     * @param e Excepción capturada
     * @return Mensaje de error de la excepción
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // Retorna código 404 (NOT_FOUND)
    public String handleNotFound(EntityNotFoundException e) {
        return e.getMessage();
    }
}