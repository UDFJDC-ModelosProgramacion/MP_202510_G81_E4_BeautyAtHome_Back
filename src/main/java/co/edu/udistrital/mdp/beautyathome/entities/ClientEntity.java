package co.edu.udistrital.mdp.beautyathome.entities;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity

public class ClientEntity extends BaseEntity {

    private String name;
    private String address;
    private String email;
    private String phoneNumber;


    @OneToMany(mappedBy = "client")
    private List<ReviewEntity> reviews;
    
}
