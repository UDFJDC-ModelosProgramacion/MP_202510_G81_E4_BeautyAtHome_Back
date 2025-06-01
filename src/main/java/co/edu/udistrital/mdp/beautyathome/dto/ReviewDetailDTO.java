package co.edu.udistrital.mdp.beautyathome.dto;

import lombok.Data;

@Data
public class ReviewDetailDTO extends ReviewDTO{
    private ServiceDTO service;
    private ClientDTO client;
}
