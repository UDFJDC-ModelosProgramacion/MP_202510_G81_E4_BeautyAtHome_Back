// ExclusiveProductService.java
package co.edu.udistrital.mdp.beautyathome.services;

import java.util.List;
import java.util.Optional;

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
    public ExclusiveProductEntity createExclusiveProduct(ExclusiveProductEntity e) throws IllegalOperationException {
        if (e.getName() == null || e.getName().isEmpty()) {
            throw new IllegalOperationException("The product name is not valid");
        }
        if (e.getPrice() == null || e.getPrice() < 0) {
            throw new IllegalOperationException("The price is not valid");
        }
        if (e.getAvailable() == null) {
            throw new IllegalOperationException("The availability flag is not valid");
        }
        if (e.getBrand() == null) {
            throw new IllegalOperationException("The brand is not valid");
        }
        return repo.save(e);
    }

    @Transactional(readOnly = true)
    public List<ExclusiveProductEntity> getAll() {
        return repo.findAll();
    }

    @Transactional(readOnly = true)
    public ExclusiveProductEntity getOne(Long id) {
        Optional<ExclusiveProductEntity> opt = repo.findById(id);
        return opt.orElseThrow(() -> new EntityNotFoundException("ExclusiveProduct with id " + id + " not found"));
    }

    @Transactional
    public ExclusiveProductEntity updateExclusiveProduct(Long id, ExclusiveProductEntity e) throws IllegalOperationException {
        Optional<ExclusiveProductEntity> exists = repo.findById(id);
        exists.orElseThrow(() -> new EntityNotFoundException("ExclusiveProduct with id " + id + " not found"));
        e.setId(id);
        if (e.getName() == null || e.getName().isEmpty()) {
            throw new IllegalOperationException("The product name is not valid");
        }
        if (e.getPrice() == null || e.getPrice() < 0) {
            throw new IllegalOperationException("The price is not valid");
        }
        if (e.getAvailable() == null) {
            throw new IllegalOperationException("The availability flag is not valid");
        }
        if (e.getBrand() == null) {
            throw new IllegalOperationException("The brand is not valid");
        }
        return repo.save(e);
    }

    @Transactional
    public void deleteExclusiveProduct(Long id) {
        Optional<ExclusiveProductEntity> exists = repo.findById(id);
        exists.orElseThrow(() -> new EntityNotFoundException("ExclusiveProduct with id " + id + " not found"));
        repo.deleteById(id);
    }
}
