package co.edu.udistrital.mdp.beautyathome.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import co.edu.udistrital.mdp.beautyathome.entities.CoverageAreaEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.repositories.CoverageAreaRepository;
import lombok.extern.slf4j.Slf4j;
@Slf4j

@Service
public class CoverageAreaService{
    @Autowired
    private CoverageAreaRepository coverageAreaRepository;

    @Transactional
    public CoverageAreaEntity createCoverageArea(CoverageAreaEntity coverageAreaEntity) throws IllegalOperationException{
        if(coverageAreaEntity.getName() == null || coverageAreaEntity.getName().isEmpty()){
            throw new IllegalOperationException("The name is not valid");
        }
        if(coverageAreaEntity.getProfessionals() == null){
            coverageAreaEntity.setProfessionals(new ArrayList<>());
        }
        return coverageAreaRepository.save(coverageAreaEntity);
    }

    @Transactional
    public List<CoverageAreaEntity> getCoverageAreas(){
        return coverageAreaRepository.findAll();
    }

    @Transactional
    public CoverageAreaEntity getCArea(Long id) throws EntityNotFoundException{
        Optional<CoverageAreaEntity> coverageAreaEntity = coverageAreaRepository.findById(id);
        return coverageAreaEntity.orElseThrow(()-> new EntityNotFoundException("The coverage area with the given id was not found: " + id));
    }

    /**
     * Actualiza un área de cobertura existente.
     * 
     * @param coverageAreaId El ID del área de cobertura a actualizar.
     * @param coverageArea El área de cobertura con los datos actualizados.
     * @return El área de cobertura actualizada.
     * @throws EntityNotFoundException Si el área de cobertura no existe.
     * @throws IllegalOperationException Si el área de cobertura no es válida.
     */
    @Transactional
    public CoverageAreaEntity updateCoverageArea(Long coverageAreaId, CoverageAreaEntity coverageArea) throws IllegalOperationException, EntityNotFoundException {
        Optional<CoverageAreaEntity> optionalCoverageAreaEntity = coverageAreaRepository.findById(coverageAreaId);
        optionalCoverageAreaEntity.orElseThrow(() -> new EntityNotFoundException("The coverage area with the given id was not found: " + coverageAreaId));
        coverageArea.setId(coverageAreaId);

        if (coverageArea.getName() == null || coverageArea.getName().isEmpty()) {
            throw new IllegalOperationException("El nombre del área de cobertura no puede ser nulo o vacío");
        }
        if (coverageArea.getProfessionals() == null) {
            coverageArea.setProfessionals(new ArrayList<>());
        }
        
        CoverageAreaEntity updatedCoverageArea = coverageAreaRepository.save(coverageArea);
        return updatedCoverageArea;
    }
    /**
     * Elimina un área de cobertura por su ID.
     * @param coverageAreaId El ID del área de cobertura a eliminar.
     * @throws EntityNotFoundException Si el área de cobertura no existe.
     */
    @Transactional
    public void deleteCoverageArea(Long coverageAreaId) throws EntityNotFoundException {
        Optional<CoverageAreaEntity> optionalCoverageAreaEntity = coverageAreaRepository.findById(coverageAreaId);
        optionalCoverageAreaEntity.orElseThrow(() -> new EntityNotFoundException("The coverage area with the given id was not found: " + coverageAreaId));
        coverageAreaRepository.deleteById(coverageAreaId);
    }
}
