// Paquete donde se encuentra la clase
package co.edu.udistrital.mdp.beautyathome.services;

// Importaciones necesarias
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.udistrital.mdp.beautyathome.entities.BrandEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.repositories.BrandRepository;
import jakarta.persistence.EntityNotFoundException;

/**
 * Servicio que contiene la lógica de negocio para operaciones con Marcas (Brands).
 * Esta clase actúa como intermediario entre los controladores y los repositorios,
 * aplicando validaciones y reglas de negocio.
 */
@Service // Indica que esta clase es un componente de servicio de Spring
public class BrandService {

    // Mensajes constantes para errores
    private static final String BRAND_NOT_FOUND_MESSAGE = "Brand with id ";
    private static final String NOT_FOUND_SUFFIX = " not found";
    private static final String BRAND_NAME_INVALID_MESSAGE = "The brand name is not valid";
    private static final String PRICE_NOT_VALID_MESSAGE = "The price is not valid";
    private static final String AVAILABILITY_FLAG_NOT_VALID_MESSAGE = "The availability flag is not valid";

    // Repositorio inyectado para acceso a datos
    private final BrandRepository brandRepository;

    /**
     * Constructor para inyección de dependencias.
     * @param brandRepository Repositorio de marcas
     */
    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    /**
     * Crea una nueva marca.
     * @param brand Entidad BrandEntity a crear
     * @return Entidad BrandEntity creada
     * @throws IllegalOperationException Si las validaciones fallan
     */
    @Transactional // Asegura que la operación se ejecute en una transacción
    public BrandEntity createBrand(BrandEntity brand) throws IllegalOperationException {
        validateBrand(brand); // Valida los datos de la marca
        return brandRepository.save(brand); // Persiste la entidad
    }

    /**
     * Obtiene todas las marcas existentes.
     * @return Lista de entidades BrandEntity
     */
    @Transactional(readOnly = true) // Transacción de solo lectura (optimización)
    public List<BrandEntity> getBrands() {
        return brandRepository.findAll(); // Obtiene todas las marcas
    }

    /**
     * Obtiene una marca por su ID.
     * @param brandId ID de la marca a buscar
     * @return Entidad BrandEntity encontrada
     * @throws EntityNotFoundException Si no se encuentra la marca
     */
    @Transactional(readOnly = true)
    public BrandEntity getBrand(Long brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new EntityNotFoundException(
                        BRAND_NOT_FOUND_MESSAGE + brandId + NOT_FOUND_SUFFIX));
    }

    /**
     * Actualiza una marca existente.
     * @param brandId ID de la marca a actualizar
     * @param brand Nuevos datos para la marca
     * @return Entidad BrandEntity actualizada
     * @throws IllegalOperationException Si las validaciones fallan
     * @throws EntityNotFoundException Si no se encuentra la marca
     */
    @Transactional
    public BrandEntity updateBrand(Long brandId, BrandEntity brand)
            throws IllegalOperationException {
        // Verifica si la marca existe
        BrandEntity existing = brandRepository.findById(brandId)
                .orElseThrow(() -> new EntityNotFoundException(
                        BRAND_NOT_FOUND_MESSAGE + brandId + NOT_FOUND_SUFFIX));

        validateBrand(brand); // Valida los nuevos datos

        // Actualiza los campos permitidos
        existing.setName(brand.getName());
        existing.setPhotograph(brand.getPhotograph());
        existing.setPrice(brand.getPrice());
        existing.setAvailable(brand.getAvailable());
        existing.setCategory(brand.getCategory());
        existing.setDescription(brand.getDescription());
        existing.setProduct(brand.getProduct());
        existing.setLogoURL(brand.getLogoURL());

        return brandRepository.save(existing); // Guarda los cambios
    }

    /**
     * Elimina una marca por su ID.
     * @param brandId ID de la marca a eliminar
     * @throws EntityNotFoundException Si no se encuentra la marca
     */
    @Transactional
    public void deleteBrand(Long brandId) {
        BrandEntity existing = brandRepository.findById(brandId)
                .orElseThrow(() -> new EntityNotFoundException(
                        BRAND_NOT_FOUND_MESSAGE + brandId + NOT_FOUND_SUFFIX));
        brandRepository.delete(existing); // Elimina la entidad
    }

    /**
     * Valida los datos de una marca.
     * @param brand Entidad BrandEntity a validar
     * @throws IllegalOperationException Si alguna validación falla
     */
    private void validateBrand(BrandEntity brand) throws IllegalOperationException {
        // Valida que el nombre no sea nulo o vacío
        if (brand.getName() == null || brand.getName().trim().isEmpty()) {
            throw new IllegalOperationException(BRAND_NAME_INVALID_MESSAGE);
        }
        // Valida que el precio no sea nulo y sea positivo
        if (brand.getPrice() == null || brand.getPrice() < 0) {
            throw new IllegalOperationException(PRICE_NOT_VALID_MESSAGE);
        }
        // Valida que el flag de disponibilidad no sea nulo
        if (brand.getAvailable() == null) {
            throw new IllegalOperationException(AVAILABILITY_FLAG_NOT_VALID_MESSAGE);
        }
    }
}