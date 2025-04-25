package co.edu.udistrital.mdp.beautyathome.entities;

import java.sql.Date;

import jakarta.persistence.Entity;
import lombok.Data;

@Data
@Entity

public class ProfesionalEntity extends BaseEntity{
    
    String photo;
    String summaryExperience;
    Date birthDate;
}
