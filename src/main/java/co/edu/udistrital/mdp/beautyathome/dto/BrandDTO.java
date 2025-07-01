package co.edu.udistrital.mdp.beautyathome.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data 
public class BrandDTO {
    private Long id;
    private String name;
    private String logoURL;

    private List<Long> exclusiveProductIds = new ArrayList<>();
}