// Paquete donde se encuentra la clase
package co.edu.udistrital.mdp.beautyathome.controllers;

// Importaciones necesarias
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import co.edu.udistrital.mdp.beautyathome.dto.ImageDTO;
import co.edu.udistrital.mdp.beautyathome.entities.ImageEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.services.ImageService;
import jakarta.persistence.EntityNotFoundException;

/**
 * Controlador REST para manejar operaciones relacionadas con imágenes.
 * Expone endpoints para el CRUD de imágenes y manejo de excepciones.
 */
@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/images") // Mapea todas las rutas de este controlador a /images
public class ImageController {

    // Servicio inyectado para la lógica de negocio
    private final ImageService service;

    /**
     * Constructor para inyección de dependencias
     * @param service Servicio de imágenes
     */
    public ImageController(ImageService service) {
        this.service = service;
    }

    /**
     * Convierte una entidad ImageEntity a un DTO ImageDTO
     * @param e Entidad de imagen
     * @return DTO de imagen
     */
    private ImageDTO toDTO(ImageEntity e) {
        ImageDTO dto = new ImageDTO();
        dto.setId(e.getId()); // Mapea el ID
        dto.setUrl(e.getUrl()); // Mapea la URL
        return dto;
    }

    /**
     * Convierte un DTO ImageDTO a una entidad ImageEntity
     * @param dto DTO de imagen
     * @return Entidad de imagen
     */
    private ImageEntity toEntity(ImageDTO dto) {
        ImageEntity e = new ImageEntity();
        e.setUrl(dto.getUrl()); // Mapea la URL
        return e;
    }

    /**
     * Endpoint para crear una nueva imagen
     * @param dto DTO con los datos de la imagen a crear
     * @return DTO de la imagen creada
     * @throws IllegalOperationException Si hay errores de validación
     */
    @PostMapping // Mapea solicitudes POST a /images
    @ResponseStatus(HttpStatus.CREATED) // Retorna estado 201 al crear
    public ImageDTO create(@RequestBody ImageDTO dto) throws IllegalOperationException {
        ImageEntity created = service.createImage(toEntity(dto)); // Crea la imagen
        return toDTO(created); // Retorna el DTO de la imagen creada
    }

    /**
     * Endpoint para listar todas las imágenes
     * @return Lista de DTOs de imágenes
     */
    @GetMapping // Mapea solicitudes GET a /images
    public List<ImageDTO> list() {
        return service.getAll().stream() // Obtiene todas las imágenes
                .map(this::toDTO) // Convierte cada entidad a DTO
                .toList(); // Retorna como lista
    }

    /**
     * Endpoint para obtener una imagen específica
     * @param id ID de la imagen a buscar
     * @return DTO de la imagen encontrada
     */
    @GetMapping("/{id}")
    public ImageDTO getOne(@PathVariable Long id) {
        return toDTO(service.getOne(id)); // Busca y retorna la imagen
    }

    /**
     * Endpoint para actualizar una imagen existente
     * @param id ID de la imagen a actualizar
     * @param dto DTO con los nuevos datos de la imagen
     * @return DTO de la imagen actualizada
     * @throws IllegalOperationException Si hay errores de validación
     */
    @PutMapping("/{id}")
    public ImageDTO update(@PathVariable Long id, @RequestBody ImageDTO dto) throws IllegalOperationException {
        ImageEntity updated = service.updateImage(id, toEntity(dto)); // Actualiza la imagen
        return toDTO(updated); // Retorna el DTO actualizado
    }

    /**
     * Endpoint para eliminar una imagen
     * @param id ID de la imagen a eliminar
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Retorna estado 204 al eliminar
    public void delete(@PathVariable Long id) {
        service.deleteImage(id); // Elimina la imagen
    }

    /**
     * Manejador de excepciones para operaciones ilegales
     * @param e Excepción capturada
     * @return Mensaje de error
     */
    @ExceptionHandler(IllegalOperationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) // Retorna estado 400
    public String handleBadRequest(IllegalOperationException e) {
        return e.getMessage(); // Retorna el mensaje de error
    }

    /**
     * Manejador de excepciones para entidades no encontradas
     * @param e Excepción capturada
     * @return Mensaje de error
     */
    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND) // Retorna estado 404
    public String handleNotFound(EntityNotFoundException e) {
        return e.getMessage(); // Retorna el mensaje de error
    }
}