package co.edu.udistrital.mdp.beautyathome.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.udistrital.mdp.beautyathome.entities.ExclusiveProductEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.repositories.ExclusiveProductRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ExclusiveProductService {

    @Autowired
    private ExclusiveProductRepository repo;

    @Transactional
    public ExclusiveProductEntity createExclusiveProduct(ExclusiveProductEntity exclusiveProductEntity) throws IllegalOperationException {
        validate(exclusiveProductEntity);
        return repo.save(exclusiveProductEntity);
    }

    @Transactional(readOnly = true)
    public List<ExclusiveProductEntity> getAll() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public ExclusiveProductEntity getOne(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ExclusiveProduct with id " + id + " not found"));

    }

    @Transactional
    public ExclusiveProductEntity updateExclusiveProduct(Long id, ExclusiveProductEntity exclusiveProductEntity) throws IllegalOperationException {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("ExclusiveProduct with id " + id + " not found");
        }
        validate(exclusiveProductEntity);
        exclusiveProductEntity.setId(id);
        return repo.save(exclusiveProductEntity);
    }

    @Transactional
    public void deleteExclusiveProduct(Long id) {
        if (!repo.existsById(id)) {
            throw new EntityNotFoundException("ExclusiveProduct with id " + id + " not found");
        }
        repo.deleteById(id);
    }

    private void validate(ExclusiveProductEntity exclusiveProductEntity) throws IllegalOperationException {

        if (exclusiveProductEntity.getName() == null || exclusiveProductEntity.getName().isEmpty()) {
            throw new IllegalOperationException("El nombre del producto no es válido.");
        }

        if (exclusiveProductEntity.getBrand() == null) {
            throw new IllegalOperationException("La marca del producto no es válida.");
        }
    }
}