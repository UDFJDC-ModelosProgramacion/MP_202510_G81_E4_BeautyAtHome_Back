package co.edu.udistrital.mdp.beautyathome.dto;

import java.util.List;

public class AgendaResponseDTO {
    private Long id;
    private Long professionalId;
    private List<String> availableDates;

    public AgendaResponseDTO(Long id, Long professionalId, List<String> availableDates) {
        this.id = id;
        this.professionalId = professionalId;
        this.availableDates = availableDates;
    }

    // Getters y Setters
}
