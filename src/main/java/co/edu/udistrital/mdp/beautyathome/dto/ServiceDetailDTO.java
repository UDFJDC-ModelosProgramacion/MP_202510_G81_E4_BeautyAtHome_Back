package co.edu.udistrital.mdp.beautyathome.dto;

import java.util.List;

import lombok.Data;

@Data

public class ServiceDetailDTO extends ServiceDTO{

    List<ReviewDetailDTO> reviews = new java.util.ArrayList<>();
}
