// Paquete donde se encuentra la clase
package co.edu.udistrital.mdp.beautyathome.controllers;

// Importaciones necesarias
import java.util.List;
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

/**
 * Controlador REST para manejar operaciones relacionadas con productos exclusivos.
 * Todas las rutas comienzan con "/exclusive-products".
 */
@RestController
@RequestMapping("/exclusive-products")
public class ExclusiveProductController {

    // Inyección del servicio que contiene la lógica de negocio para productos exclusivos
    @Autowired
    private ExclusiveProductService service;

    // Inyección del repositorio para acceder a las marcas (brands)
    @Autowired
    private BrandRepository brandRepo;

    /**
     * Metodo auxiliar para convertir una entidad ExclusiveProductEntity a un DTO ExclusiveProductDTO.
     * @param e Entidad ExclusiveProductEntity a convertir
     * @return DTO ExclusiveProductDTO con los datos de la entidad
     */
    private ExclusiveProductDTO toDTO(ExclusiveProductEntity e) {
        ExclusiveProductDTO dto = new ExclusiveProductDTO();
        dto.setId(e.getId());               // Copia el ID del producto
        dto.setName(e.getName());           // Copia el nombre del producto
        dto.setPhoto(e.getPhoto());         // Copia la foto/URL del producto
        dto.setPrice(e.getPrice());         // Copia el precio del producto
        dto.setAvailable(e.getAvailable()); // Copia la disponibilidad del producto
        dto.setCategory(e.getCategory());   // Copia la categoría del producto
        dto.setDescription(e.getDescription()); // Copia la descripción del producto
        dto.setBrandId(e.getBrand().getId());   // Copia el ID de la marca asociada
        return dto;
    }

    /**
     * Metodo auxiliar para convertir un DTO ExclusiveProductDTO a una entidad ExclusiveProductEntity
     * @param dto DTO ExclusiveProductDTO a convertir
     * @return Entidad ExclusiveProductEntity con los datos del DTO
     * @throws EntityNotFoundException Si no se encuentra la marca asociada
     */
    private ExclusiveProductEntity toEntity(ExclusiveProductDTO dto) {
        ExclusiveProductEntity e = new ExclusiveProductEntity();
        e.setName(dto.getName());           // Establece el nombre del producto
        e.setPhoto(dto.getPhoto());         // Establece la foto/URL del producto
        e.setPrice(dto.getPrice());         // Establece el precio del producto
        e.setAvailable(dto.getAvailable()); // Establece la disponibilidad del producto
        e.setCategory(dto.getCategory());   // Establece la categoría del producto
        e.setDescription(dto.getDescription()); // Establece la descripción del producto

        // Busca y establece la marca asociada al producto
        BrandEntity brand = brandRepo.findById(dto.getBrandId())
                .orElseThrow(() -> new EntityNotFoundException("Brand with id " + dto.getBrandId() + " not found"));
        e.setBrand(brand);

        return e;
    }

    /**
     * Endpoint POST para crear un nuevo producto exclusivo.
     * @param dto DTO con los datos del producto a crear
     * @return DTO del producto creado
     * @throws IllegalOperationException Si la operación no es válida
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED) // Retorna código 201 (CREATED) al crear exitosamente
    public ExclusiveProductDTO create(@RequestBody ExclusiveProductDTO dto) throws IllegalOperationException {
        // Convierte el DTO a entidad, crea el producto y convierte el resultado a DTO
        ExclusiveProductEntity created = service.createExclusiveProduct(toEntity(dto));
        return toDTO(created);
    }

    /**
     * Endpoint GET para listar todos los productos exclusivos.
     * @return Lista de DTOs de todos los productos exclusivos
     */
    @GetMapping
    public List<ExclusiveProductDTO> list() {
        // Obtiene todos los productos, los convierte a DTO y los devuelve como lista
        return service.getAll().stream()
                .map(this::toDTO)
                .toList();
    }

    /**
     * Endpoint GET para obtener un producto exclusivo específico por su ID.
     * @param id ID del producto a buscar
     * @return DTO del producto encontrado
     */
    @GetMapping("/{id}")
    public ExclusiveProductDTO getOne(@PathVariable Long id) {
        // Obtiene el producto por ID y lo convierte a DTO
        return toDTO(service.getOne(id));
    }

    /**
     * Endpoint PUT para actualizar un producto exclusivo existente.
     * @param id ID del producto a actualizar
     * @param dto DTO con los nuevos datos del producto
     * @return DTO del producto actualizado
     * @throws IllegalOperationException Si la operación no es válida
     */
    @PutMapping("/{id}")
    public ExclusiveProductDTO update(@PathVariable Long id, @RequestBody ExclusiveProductDTO dto) throws IllegalOperationException {
        // Convierte el DTO a entidad, establece el ID y actualiza el producto
        ExclusiveProductEntity entityToUpdate = toEntity(dto);
        entityToUpdate.setId(id);
        ExclusiveProductEntity updated = service.updateExclusiveProduct(id, entityToUpdate);
        return toDTO(updated);
    }

    /**
     * Endpoint DELETE para eliminar un producto exclusivo por su ID.
     * @param id ID del producto a eliminar
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Retorna código 204 (NO_CONTENT) al eliminar exitosamente
    public void delete(@PathVariable Long id) {
        service.deleteExclusiveProduct(id); // Elimina el producto con el ID proporcionado
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