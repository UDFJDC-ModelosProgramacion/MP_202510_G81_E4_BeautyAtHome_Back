package co.edu.udistrital.mdp.beautyathome.entities;

import java.sql.Date;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity

public class ServicioEntity extends BaseEntity {

    String nombre,descripcion;
    Date fechaDeRealizacion;
    Double precio;
}
