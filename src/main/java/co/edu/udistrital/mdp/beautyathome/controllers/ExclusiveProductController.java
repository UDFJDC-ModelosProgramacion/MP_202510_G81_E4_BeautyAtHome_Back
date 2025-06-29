package co.edu.udistrital.mdp.beautyathome.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.modelmapper.ModelMapper; // Importación de ModelMapper
import org.modelmapper.TypeToken; // Importación de TypeToken
import co.edu.udistrital.mdp.beautyathome.dto.ExclusiveProductDTO;
import co.edu.udistrital.mdp.beautyathome.dto.ExclusiveProductDetailDTO; // Importación de ExclusiveProductDetailDTO
import co.edu.udistrital.mdp.beautyathome.entities.ExclusiveProductEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.services.ExclusiveProductService;
import jakarta.persistence.EntityNotFoundException;

/**
 * Controlador REST para manejar operaciones relacionadas con productos exclusivos.
 * Expone endpoints para el CRUD de productos y manejo de excepciones.
 */
@RestController // Indica que esta clase es un controlador REST
@RequestMapping("/exclusive-products") // Mapea todas las rutas de este controlador a /exclusive-products
public class ExclusiveProductController {

    // Servicio inyectado para la lógica de negocio para productos exclusivos
    private final ExclusiveProductService service;

    // ModelMapper inyectado para la conversión entre entidades y DTOs
    private final ModelMapper modelMapper;

    /**
     * Constructor para inyección de dependencias.
     * @param service Servicio de productos exclusivos.
     * @param modelMapper Instancia de ModelMapper.
     */
    @Autowired // La inyección de dependencias por constructor es la forma preferida en Spring
    public ExclusiveProductController(ExclusiveProductService service, ModelMapper modelMapper) {
        this.service = service;
        this.modelMapper = modelMapper;
    }

    /**
     * Endpoint para crear un nuevo producto exclusivo.
     * @param dto DTO con los datos del producto a crear.
     * @return DTO del producto creado.
     * @throws IllegalOperationException Si hay errores de validación.
     */
    @PostMapping // Mapea solicitudes POST a /exclusive-products
    @ResponseStatus(HttpStatus.CREATED) // Retorna código 201 (CREATED) al crear exitosamente
    public ExclusiveProductDTO create(@RequestBody ExclusiveProductDTO dto) throws IllegalOperationException {
        // Convierte el DTO a entidad (ExclusiveProductEntity)
        ExclusiveProductEntity productToCreate = modelMapper.map(dto, ExclusiveProductEntity.class);
        // Si el DTO trae un brandId, debemos asegurar que la entidad Brand esté asociada antes de crear
        // ModelMapper puede manejar esto si se configura, pero para control explícito:
        // No se necesita buscar la marca aquí; ExclusiveProductService ya la busca por ID del DTO en toEntity si se usara ese método.
        // Pero como estamos usando ModelMapper para el toEntity, necesitamos que el mapper sepa cómo mapear brandId a BrandEntity.
        // O lo hacemos manualmente aquí antes de pasar al servicio. La segunda opción es más directa sin configuración de mapper avanzada.

        // Dado que ModelMapper no puede por sí solo mapear brandId a BrandEntity sin configuración,
        // revertimos a la lógica manual de BrandService.
        // O configuramos ModelMapper. Para este ejemplo, configuraremos ModelMapper para hacerlo.
        // Esto se manejará en la configuración de ModelMapper.

        ExclusiveProductEntity created = service.createExclusiveProduct(productToCreate);
        return modelMapper.map(created, ExclusiveProductDTO.class);
    }

    /**
     * Endpoint para listar todos los productos exclusivos.
     * Retorna una lista de ExclusiveProductDetailDTO.
     * @return Lista de DTOs de productos exclusivos con detalle.
     */
    @GetMapping // Mapea solicitudes GET a /exclusive-products
    @ResponseStatus(HttpStatus.OK) // Retorna código 200 (OK)
    public List<ExclusiveProductDetailDTO> findAll() {
        List<ExclusiveProductEntity> products = service.getAll();
        // Convierte la lista de entidades a lista de ExclusiveProductDetailDTO
        return modelMapper.map(products, new TypeToken<List<ExclusiveProductDetailDTO>>() {}.getType());
    }

    /**
     * Endpoint para obtener un producto exclusivo específico por ID.
     * Retorna un ExclusiveProductDetailDTO.
     * @param id ID del producto a buscar.
     * @return DTO del producto encontrado con detalle.
     * @throws EntityNotFoundException Si no se encuentra el producto.
     */
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) // Retorna código 200 (OK)
    public ExclusiveProductDetailDTO findOne(@PathVariable Long id) throws EntityNotFoundException {
        ExclusiveProductEntity productEntity = service.getOne(id);
        // Convierte la Entity a ExclusiveProductDetailDTO
        return modelMapper.map(productEntity, ExclusiveProductDetailDTO.class);
    }

    /**
     * Endpoint para actualizar un producto exclusivo existente.
     * @param id ID del producto a actualizar.
     * @param dto DTO con los nuevos datos del producto.
     * @return DTO del producto actualizado.
     * @throws IllegalOperationException Si hay errores de validación.
     * @throws EntityNotFoundException Si no se encuentra el producto.
     */
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK) // Retorna código 200 (OK)
    public ExclusiveProductDTO update(@PathVariable Long id, @RequestBody ExclusiveProductDTO dto)
            throws IllegalOperationException, EntityNotFoundException {
        // Convierte el DTO a entidad (ExclusiveProductEntity)
        ExclusiveProductEntity productToUpdate = modelMapper.map(dto, ExclusiveProductEntity.class);
        // El servicio ya se encarga de buscar la entidad existente y actualizarla.
        ExclusiveProductEntity updated = service.updateExclusiveProduct(id, productToUpdate);
        return modelMapper.map(updated, ExclusiveProductDTO.class);
    }

    /**
     * Endpoint para eliminar un producto exclusivo.
     * @param id ID del producto a eliminar.
     * @throws EntityNotFoundException Si no se encuentra el producto.
     */
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT) // Retorna código 204 (NO_CONTENT) al eliminar exitosamente
    public void delete(@PathVariable Long id) throws EntityNotFoundException {
        service.deleteExclusiveProduct(id); // Elimina el producto con el ID proporcionado
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