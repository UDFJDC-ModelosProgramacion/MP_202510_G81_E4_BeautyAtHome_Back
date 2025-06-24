package co.edu.udistrital.mdp.beautyathome.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class BrandDetailDTO extends BrandDTO {

    private List<ExclusiveProductDTO> exclusiveProducts = new ArrayList<>(); // Lista de productos exclusivos de la marca
}