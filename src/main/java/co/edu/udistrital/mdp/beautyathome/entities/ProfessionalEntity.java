package co.edu.udistrital.mdp.beautyathome.entities;

import java.sql.Date;

import com.fasterxml.jackson.databind.JsonSerializable.Base;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;

@Data
@Entity

public class ProfessionalEntity extends BaseEntity{
    
    String name,photo,professionalSummary;
    Date birthDate;

    @PodamExclude
    @OneToMany(mappedBy = "professional")
    private java.util.List<ServiceEntity> servicios = new java.util.ArrayList<>();

    //@PodamExclude
    //@OneToOne(mappedBy = "profesional")
    //private AgendaEntity agenda;
     
}
