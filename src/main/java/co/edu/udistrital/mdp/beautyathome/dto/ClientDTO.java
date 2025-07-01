package co.edu.udistrital.mdp.beautyathome.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Clase DTO que representa un cliente
 */
@Data
public class ClientDTO {
    private Long id;
    private String fullName;
    private String address;
    private String email;
    private String phoneNumber;
}
