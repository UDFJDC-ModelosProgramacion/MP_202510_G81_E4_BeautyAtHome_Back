// Paquete donde se encuentra el servicio
package co.edu.udistrital.mdp.beautyathome.services;

// Importaciones necesarias
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.udistrital.mdp.beautyathome.entities.ImageEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.repositories.ImageRepository;
import jakarta.persistence.EntityNotFoundException;

/**
 * Servicio que contiene la lógica de negocio para operaciones con imágenes.
 * Maneja validaciones, transacciones y excepciones relacionadas con imágenes.
 */
@Service
public class ImageService {

    // Repositorio inyectado para operaciones con la base de datos
    private final ImageRepository repo;

    // Constantes para evitar duplicación de cadenas
    private static final String INVALID_URL_MESSAGE = "The image URL is not valid";
    private static final String NOT_FOUND_MESSAGE_TEMPLATE = "Image with id %d not found";

    /**
     * Constructor para inyección de dependencias
     * @param repo Repositorio de imágenes
     */
    public ImageService(ImageRepository repo) {
        this.repo = repo;
    }

    /**
     * Crea una nueva imagen en el sistema
     * @param image Entidad de imagen a crear
     * @return Imagen creada con ID generado
     * @throws IllegalOperationException Si la URL de la imagen no es válida
     */
    @Transactional
    public ImageEntity createImage(ImageEntity image) throws IllegalOperationException {
        // Valida que la URL no sea nula o vacía
        if (image.getUrl() == null || image.getUrl().trim().isEmpty()) {
            throw new IllegalOperationException(INVALID_URL_MESSAGE);
        }
        return repo.save(image);
    }

    /**
     * Obtiene todas las imágenes del sistema
     * @return Lista de todas las imágenes
     */
    @Transactional(readOnly = true)
    public List<ImageEntity> getAll() {
        return repo.findAll();
    }

    /**
     * Obtiene una imagen específica por su ID
     * @param id Identificador de la imagen
     * @return Entidad de la imagen encontrada
     * @throws EntityNotFoundException Si no se encuentra la imagen
     */
    @Transactional(readOnly = true)
    public ImageEntity getOne(Long id) {
        String notFoundMessage = String.format(NOT_FOUND_MESSAGE_TEMPLATE, id);
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(notFoundMessage));
    }

    /**
     * Actualiza una imagen existente
     * @param id ID de la imagen a actualizar
     * @param image Datos actualizados de la imagen
     * @return Imagen actualizada
     * @throws EntityNotFoundException Si no se encuentra la imagen
     * @throws IllegalOperationException Si la URL no es válida
     */
    @Transactional
    public ImageEntity updateImage(Long id, ImageEntity image) throws IllegalOperationException {
        String notFoundMessage = String.format(NOT_FOUND_MESSAGE_TEMPLATE, id);

        // Verifica existencia de la imagen
        Optional<ImageEntity> existing = repo.findById(id);
        existing.orElseThrow(() -> new EntityNotFoundException(notFoundMessage));

        // Valida la URL
        if (image.getUrl() == null || image.getUrl().trim().isEmpty()) {
            throw new IllegalOperationException(INVALID_URL_MESSAGE);
        }

        // Asegura que el ID sea correcto y actualiza la imagen
        image.setId(id);
        return repo.save(image);
    }

    /**
     * Elimina una imagen del sistema
     * @param id ID de la imagen a eliminar
     * @throws EntityNotFoundException Si no se encuentra la imagen
     */
    @Transactional
    public void deleteImage(Long id) {
        String notFoundMessage = String.format(NOT_FOUND_MESSAGE_TEMPLATE, id);

        // Verifica existencia antes de eliminar
        Optional<ImageEntity> existing = repo.findById(id);
        existing.orElseThrow(() -> new EntityNotFoundException(notFoundMessage));

        repo.deleteById(id);
    }
}
