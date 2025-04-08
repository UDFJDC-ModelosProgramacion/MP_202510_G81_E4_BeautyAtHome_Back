package co.edu.udistrital.mdp.beautyathome.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity

public class ServicioEntity extends BaseEntity {

    String nombre,descripcion,fechaDeRealizacion;

    Double precio;
}
