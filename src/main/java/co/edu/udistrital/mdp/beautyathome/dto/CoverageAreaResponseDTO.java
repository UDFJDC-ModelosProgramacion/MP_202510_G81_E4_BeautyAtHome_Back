package co.edu.udistrital.mdp.beautyathome.dto;

public class CoverageAreaResponseDTO {
    private Long id;
    private String zone;
    private String city;

    public CoverageAreaResponseDTO(Long id, String zone, String city) {
        this.id = id;
        this.zone = zone;
        this.city = city;
    }

}
