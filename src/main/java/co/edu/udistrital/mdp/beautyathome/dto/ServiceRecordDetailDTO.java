package co.edu.udistrital.mdp.beautyathome.dto;

import lombok.Data;

@Data
public class ServiceRecordDetailDTO extends ServiceDTO{
    private ServiceDTO service;
    private ReviewDTO review;
}
