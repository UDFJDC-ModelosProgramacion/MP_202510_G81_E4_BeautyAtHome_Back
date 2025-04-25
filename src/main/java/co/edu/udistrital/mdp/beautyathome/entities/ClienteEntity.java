package co.edu.udistrital.mdp.beautyathome.entities;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity

public class ClienteEntity extends BaseEntity {

    String direccion,email,telefono;

}
