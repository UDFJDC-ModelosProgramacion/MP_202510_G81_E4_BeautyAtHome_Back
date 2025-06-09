package co.edu.udistrital.mdp.beautyathome.dto;

import lombok.Data;

@Data
public class ClientDTO {
    private Long id;
    private String name;
    private String address;
    private String email;
    private String phoneNumber;
}
