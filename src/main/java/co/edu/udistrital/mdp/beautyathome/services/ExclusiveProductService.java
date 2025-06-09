// Paquete donde se encuentra la clase
package co.edu.udistrital.mdp.beautyathome.services;

// Importaciones necesarias
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.udistrital.mdp.beautyathome.entities.ExclusiveProductEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.repositories.ExclusiveProductRepository;
import jakarta.persistence.EntityNotFoundException;

/**
 * Servicio para manejar operaciones relacionadas con productos exclusivos.
 * Esta clase proporciona métodos CRUD y validaciones para productos exclusivos.
 */
@Service // Anotación que indica que esta clase es un servicio de Spring
public class ExclusiveProductService {

    // Inyección del repositorio para acceder a la base de datos
    @Autowired
    private ExclusiveProductRepository repo;

    /**
     * Crea un nuevo producto exclusivo en la base de datos.
     * @param e Entidad del producto exclusivo a crear
     * @return La entidad del producto exclusivo guardada
     * @throws IllegalOperationException Si la validación del producto falla
     */
    @Transactional // Anotación que indica que este metodo es una transacción
    public ExclusiveProductEntity createExclusiveProduct(ExclusiveProductEntity e) throws IllegalOperationException {
        validate(e); // Valida los datos del producto antes de guardar
        return repo.save(e); // Guarda el producto en la base de datos
    }

    /**
     * Obtiene todos los productos exclusivos de la base de datos.
     * @return Lista de todos los productos exclusivos
     */
    @Transactional(readOnly = true) // Transacción de solo lectura
    public List<ExclusiveProductEntity> getAll() {
        return repo.findAll(); // Retorna todos los productos
    }

    /**
     * Obtiene un producto exclusivo por su ID.
     * @param id ID del producto a buscar
     * @return La entidad del producto encontrado
     * @throws EntityNotFoundException Si no se encuentra el producto con el ID especificado
     */
    @Transactional(readOnly = true) // Transacción de solo lectura
    public ExclusiveProductEntity getOne(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ExclusiveProduct with id " + id + " not found"));
        // Busca el producto por ID o lanza excepción si no existe
    }

    /**
     * Actualiza un producto exclusivo existente.
     * @param id ID del producto a actualizar
     * @param e Entidad del producto con los nuevos datos
     * @return La entidad del producto actualizada
     * @throws EntityNotFoundException Si no se encuentra el producto con el ID especificado
     * @throws IllegalOperationException Si la validación del producto falla
     */
    @Transactional
    public ExclusiveProductEntity updateExclusiveProduct(Long id, ExclusiveProductEntity e) throws IllegalOperationException {
        // Verifica si el producto existe antes de actualizar
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("ExclusiveProduct with id " + id + " not found");
        }
        validate(e); // Valida los nuevos datos del producto
        e.setId(id); // Asegura que el ID sea el correcto
        return repo.save(e); // Guarda los cambios en la base de datos
    }

    /**
     * Elimina un producto exclusivo por su ID.
     * @param id ID del producto a eliminar
     * @throws EntityNotFoundException Si no se encuentra el producto con el ID especificado
     */
    @Transactional
    public void deleteExclusiveProduct(Long id) {
        // Verifica si el producto existe antes de eliminar
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("ExclusiveProduct with id " + id + " not found");
        }
        repo.deleteById(id); // Elimina el producto de la base de datos
    }

    /**
     * Valida los datos de un producto exclusivo antes de guardarlo o actualizarlo.
     * @param e Entidad del producto a validar
     * @throws IllegalOperationException Si algún campo no cumple con las validaciones
     */
    private void validate(ExclusiveProductEntity e) throws IllegalOperationException {
        // Valida que el nombre no sea nulo o vacío
        if (e.getName() == null || e.getName().isEmpty()) {
            throw new IllegalOperationException("The product name is not valid");
        }
        // Valida que el precio no sea nulo y sea mayor o igual a cero
        if (e.getPrice() == null || e.getPrice() < 0) {
            throw new IllegalOperationException("The price is not valid");
        }
        // Valida que el flag de disponibilidad no sea nulo
        if (e.getAvailable() == null) {
            throw new IllegalOperationException("The availability flag is not valid");
        }
        // Valida que la marca no sea nula
        if (e.getBrand() == null) {
            throw new IllegalOperationException("The brand is not valid");
        }
    }
}