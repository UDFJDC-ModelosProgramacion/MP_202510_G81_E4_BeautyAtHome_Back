package co.edu.udistrital.mdp.beautyathome.services;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import co.edu.udistrital.mdp.beautyathome.entities.BrandEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.repositories.BrandRepository;
import jakarta.persistence.EntityNotFoundException;

@Service
public class BrandService {

    private static final String BRAND_NOT_FOUND_MESSAGE = "Marca con ID ";
    private static final String NOT_FOUND_SUFFIX = " no encontrada.";
    private static final String BRAND_NAME_INVALID_MESSAGE = "El nombre de la marca no es válido.";

    private final BrandRepository brandRepository;

    public BrandService(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Transactional
    public BrandEntity createBrand(BrandEntity brand) throws IllegalOperationException {
        validateBrand(brand);
        return brandRepository.save(brand);
    }

    @Transactional(readOnly = true)
    public List<BrandEntity> getBrands() {
        return brandRepository.findAll();
    }

    @Transactional(readOnly = true)
    public BrandEntity getBrand(Long brandId) {
        return brandRepository.findById(brandId)
                .orElseThrow(() -> new EntityNotFoundException(
                        BRAND_NOT_FOUND_MESSAGE + brandId + NOT_FOUND_SUFFIX));
    }

    @Transactional
    public BrandEntity updateBrand(Long brandId, BrandEntity brand)
            throws IllegalOperationException {
        BrandEntity existing = brandRepository.findById(brandId)
                .orElseThrow(() -> new EntityNotFoundException(
                        BRAND_NOT_FOUND_MESSAGE + brandId + NOT_FOUND_SUFFIX));

        validateBrand(brand);

        existing.setName(brand.getName());
        existing.setLogoURL(brand.getLogoURL());

        return brandRepository.save(existing);
    }

    @Transactional
    public void deleteBrand(Long brandId) {
        BrandEntity existing = brandRepository.findById(brandId)
                .orElseThrow(() -> new EntityNotFoundException(
                        BRAND_NOT_FOUND_MESSAGE + brandId + NOT_FOUND_SUFFIX));
        brandRepository.delete(existing);
    }

    private void validateBrand(BrandEntity brand) throws IllegalOperationException {
        if (brand.getName() == null || brand.getName().trim().isEmpty()) {
            throw new IllegalOperationException(BRAND_NAME_INVALID_MESSAGE);
        }
    }
}