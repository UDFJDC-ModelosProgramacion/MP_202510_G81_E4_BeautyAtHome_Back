package co.edu.udistrital.mdp.beautyathome.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udistrital.mdp.beautyathome.entities.BrandEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.repositories.BrandRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class BrandService {

    // Constantes para los mensajes de error, evitando duplicación de literales de cadena
    private static final String BRAND_NOT_FOUND_MESSAGE = "Brand with id ";
    private static final String NOT_FOUND_SUFFIX = " not found";
    private static final String BRAND_NAME_INVALID_MESSAGE = "The brand name is not valid";
    private static final String PRICE_NOT_VALID_MESSAGE = "The price is not valid";
    private static final String AVAILABILITY_FLAG_NOT_VALID_MESSAGE = "The availability flag is not valid";

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Transactional
    public BrandEntity createBrand(BrandEntity brand) throws IllegalOperationException {
        if (brand.getName() == null || brand.getName().isEmpty()) {
            throw new IllegalOperationException(BRAND_NAME_INVALID_MESSAGE);
        }
        if (brand.getPrice() == null || brand.getPrice() < 0) {
            throw new IllegalOperationException(PRICE_NOT_VALID_MESSAGE);
        }
        if (brand.getAvailable() == null) {
            throw new IllegalOperationException(AVAILABILITY_FLAG_NOT_VALID_MESSAGE);
        }
        return brandRepository.save(brand);
    }

    @Transactional(readOnly = true)
    public List<BrandEntity> getBrands() {
        return brandRepository.findAll();
    }

    @Transactional(readOnly = true)
    public BrandEntity getBrand(Long brandId) {
        Optional<BrandEntity> opt = brandRepository.findById(brandId);
        // Concatenamos las constantes para el mensaje de no encontrado
        return opt.orElseThrow(() -> new EntityNotFoundException(BRAND_NOT_FOUND_MESSAGE + brandId + NOT_FOUND_SUFFIX));
    }

    @Transactional
    public BrandEntity updateBrand(Long brandId, BrandEntity brand) throws IllegalOperationException {
        Optional<BrandEntity> existing = brandRepository.findById(brandId);
        existing.orElseThrow(() -> new EntityNotFoundException(BRAND_NOT_FOUND_MESSAGE + brandId + NOT_FOUND_SUFFIX));
        brand.setId(brandId);
        if (brand.getName() == null || brand.getName().isEmpty()) {
            throw new IllegalOperationException(BRAND_NAME_INVALID_MESSAGE);
        }
        if (brand.getPrice() == null || brand.getPrice() < 0) {
            throw new IllegalOperationException(PRICE_NOT_VALID_MESSAGE);
        }
        if (brand.getAvailable() == null) {
            throw new IllegalOperationException(AVAILABILITY_FLAG_NOT_VALID_MESSAGE);
        }
        return brandRepository.save(brand);
    }

    @Transactional
    public void deleteBrand(Long brandId) {
        Optional<BrandEntity> existing = brandRepository.findById(brandId);
        existing.orElseThrow(() -> new EntityNotFoundException(BRAND_NOT_FOUND_MESSAGE + brandId + NOT_FOUND_SUFFIX));
        brandRepository.deleteById(brandId);
    }
}