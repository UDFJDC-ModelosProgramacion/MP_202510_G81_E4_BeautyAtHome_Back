package co.edu.udistrital.mdp.beautyathome.dto;

import java.util.List;

import lombok.Data;

@Data
public class CoverageAreaDTO {
    private String name;
    private List<Long> professionalIds;
}
