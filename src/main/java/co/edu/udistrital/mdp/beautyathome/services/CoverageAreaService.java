package co.edu.udistrital.mdp.beautyathome.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.udistrital.mdp.beautyathome.entities.CoverageAreaEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.repositories.CoverageAreaRepository;

@Service
public class CoverageAreaService{
    @Autowired
    private CoverageAreaRepository coverageAreaRepository;

    public CoverageAreaEntity createCoverageArea(CoverageAreaEntity coverageAreaEntity) throws IllegalOperationException{
        if(coverageAreaEntity.getName() == null || coverageAreaEntity.getName().isEmpty()){
            throw new IllegalOperationException("The name is not valid");
        }
        if(coverageAreaEntity.getProfessionals() == null){
            coverageAreaEntity.setProfessionals(new ArrayList<>());
        }
        return coverageAreaRepository.save(coverageAreaEntity);
    }

    public List<CoverageAreaEntity> getCoverageAreas(){
        return coverageAreaRepository.findAll();
    }
}
