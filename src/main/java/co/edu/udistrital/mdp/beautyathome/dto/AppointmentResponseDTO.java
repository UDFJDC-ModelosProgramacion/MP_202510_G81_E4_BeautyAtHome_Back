package co.edu.udistrital.mdp.beautyathome.dto;

public class AppointmentResponseDTO {
    private Long id;
    private Long clientId;
    private String date;
    private Long serviceId;

    public AppointmentResponseDTO(Long id, Long clientId, String date, Long serviceId) {
        this.id = id;
        this.clientId = clientId;
        this.date = date;
        this.serviceId = serviceId;
    }

    // Getters y Setters
}
