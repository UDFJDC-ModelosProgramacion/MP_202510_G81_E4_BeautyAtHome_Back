// Paquete donde se encuentra la clase DTO
package co.edu.udistrital.mdp.beautyathome.dto;

// Importación de la anotación Lombok
import lombok.Data;

@Data
public class ImageDTO {
    private Long id;
    private String url;
}