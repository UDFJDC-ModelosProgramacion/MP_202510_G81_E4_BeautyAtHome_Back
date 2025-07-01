package co.edu.udistrital.mdp.beautyathome.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ClientDetailDTO extends ClientDTO{
    List<ReviewDetailDTO> reviews = new ArrayList<>();
}
