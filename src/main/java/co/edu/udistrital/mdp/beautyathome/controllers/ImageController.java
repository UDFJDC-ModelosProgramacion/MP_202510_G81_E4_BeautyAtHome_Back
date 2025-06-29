package co.edu.udistrital.mdp.beautyathome.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper; // Nueva importación para ModelMapper
import org.modelmapper.TypeToken; // Nueva importación para TypeToken

import co.edu.udistrital.mdp.beautyathome.dto.ImageDTO;
import co.edu.udistrital.mdp.beautyathome.dto.ImageDetailDTO; // Nueva importación para ImageDetailDTO
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

    // ModelMapper inyectado para la conversión entre entidades y DTOs
    private final ModelMapper modelMapper;

    /**
     * Constructor para inyección de dependencias
     * @param service Servicio de imágenes
     * @param modelMapper Instancia de ModelMapper
     */
    public ImageController(ImageService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
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
        // Convierte el DTO a Entity antes de pasarlo al servicio
        ImageEntity imageEntity = modelMapper.map(dto, ImageEntity.class);
        ImageEntity created = service.createImage(imageEntity);
        // Convierte la Entity creada a DTO para retornar
        return modelMapper.map(created, ImageDTO.class);
    }

    /**
     * Endpoint para listar todas las imágenes utilizando ImageDetailDTO.
     * @return Lista de DTOs de imágenes con detalle
     */
    @GetMapping // Mapea solicitudes GET a /images
    @ResponseStatus(HttpStatus.OK) // Retorna estado 200
    public List<ImageDetailDTO> findAll() {
        List<ImageEntity> images = service.getAll();
        // Convierte la lista de entidades a lista de ImageDetailDTO
        return modelMapper.map(images, new TypeToken<List<ImageDetailDTO>>() {}.getType());
    }

    /**
     * Endpoint para obtener una imagen específica utilizando ImageDetailDTO.
     * @param id ID de la imagen a buscar
     * @return DTO de la imagen encontrada con detalle
     * @throws EntityNotFoundException Si no se encuentra la imagen
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) // Retorna estado 200
    public ImageDetailDTO findOne(@PathVariable Long id) throws EntityNotFoundException {
        ImageEntity imageEntity = service.getOne(id);
        // Convierte la Entity a ImageDetailDTO
        return modelMapper.map(imageEntity, ImageDetailDTO.class);
    }

    /**
     * Endpoint para actualizar una imagen existente
     * @param id ID de la imagen a actualizar
     * @param dto DTO con los nuevos datos de la imagen
     * @return DTO de la imagen actualizada
     * @throws IllegalOperationException Si hay errores de validación
     * @throws EntityNotFoundException Si no se encuentra la imagen
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) // Retorna estado 200
    public ImageDTO update(@PathVariable Long id, @RequestBody ImageDTO dto)
            throws IllegalOperationException, EntityNotFoundException {
        // Convierte el DTO a Entity antes de pasarlo al servicio
        ImageEntity imageEntity = modelMapper.map(dto, ImageEntity.class);
        ImageEntity updated = service.updateImage(id, imageEntity);
        // Convierte la Entity actualizada a DTO para retornar
        return modelMapper.map(updated, ImageDTO.class);
    }

    /**
     * Endpoint para eliminar una imagen
     * @param id ID de la imagen a eliminar
     * @throws EntityNotFoundException Si no se encuentra la imagen
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Retorna estado 204 al eliminar
    public void delete(@PathVariable Long id) throws EntityNotFoundException {
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