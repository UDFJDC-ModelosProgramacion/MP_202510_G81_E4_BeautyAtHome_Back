package co.edu.udistrital.mdp.beautyathome.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import co.edu.udistrital.mdp.beautyathome.dto.ReviewDTO;
import co.edu.udistrital.mdp.beautyathome.dto.ServiceRecordDetailDTO;
import co.edu.udistrital.mdp.beautyathome.entities.ReviewEntity;
import co.edu.udistrital.mdp.beautyathome.entities.ServiceRecordEntity;
import co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.beautyathome.services.ServiceRecordService;

@RestController
@RequestMapping("/services_records")
public class ServiceRecordController {

    @Autowired
    ServiceRecordService serviceRecordService;

    @Autowired
    ModelMapper modelMapper;

    @PostMapping
    @ResponseStatus(code = HttpStatus.OK)
    public ServiceRecordDetailDTO create(@RequestBody ServiceRecordDetailDTO serviceRecordDetailDTO){
        ServiceRecordEntity serviceRecordEntity = serviceRecordService.createServiceRecord(modelMapper.map
        (serviceRecordDetailDTO,ServiceRecordEntity.class));
        return modelMapper.map(serviceRecordEntity, ServiceRecordDetailDTO.class);
    }

    @PutMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public ServiceRecordDetailDTO update(@PathVariable Long id, @RequestBody ReviewDTO reviewDTO) throws EntityNotFoundException{
        ServiceRecordEntity serviceRecordEntity = serviceRecordService.addReviewToRecord(id, modelMapper.map
        (reviewDTO,ReviewEntity.class));
        return modelMapper.map(serviceRecordEntity, ServiceRecordDetailDTO.class);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) throws EntityNotFoundException, co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException{
       serviceRecordService.deleteServiceRecord(id);
    }
}
