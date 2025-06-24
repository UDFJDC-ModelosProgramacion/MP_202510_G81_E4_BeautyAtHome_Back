package co.edu.udistrital.mdp.beautyathome.dto;

import lombok.Data;
import lombok.EqualsAndHashCode; // Necesario para evitar advertencias de Lombok al extender

@Data // Anotación de Lombok
@EqualsAndHashCode(callSuper = true) // Importante para que equals y hashCode consideren los campos de la superclase
public class ExclusiveProductDetailDTO extends ExclusiveProductDTO {

    private BrandDTO brand; // DTO de la marca asociada, para mostrar detalle
}