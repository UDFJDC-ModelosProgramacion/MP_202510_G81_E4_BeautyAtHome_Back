package co.edu.udistrital.mdp.beautyathome.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity

public class ClientEntity extends BaseEntity {

    String name,addres,email,phoneNumber;

    @PodamExclude
    @OneToMany(mappedBy = "client")
    private java.util.List<ReviewEntity> reviews = new java.util.ArrayList<>();

}
