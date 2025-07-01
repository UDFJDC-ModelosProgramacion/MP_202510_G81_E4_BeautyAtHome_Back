package co.edu.udistrital.mdp.beautyathome.dto;

import lombok.Data;
import lombok.EqualsAndHashCode; 

@Data 
@EqualsAndHashCode(callSuper = true) 
public class ExclusiveProductDetailDTO extends ExclusiveProductDTO {
    private BrandDetailDTO brandDetail; 
}