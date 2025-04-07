package co.edu.udistrital.mdp.beautyathome.entities;

import com.fasterxml.jackson.databind.JsonSerializable.Base;

import jakarta.persistence.Entity;
import lombok.Data;

@Data

@Entity

public class ProfesionalEntity extends BaseEntity{
    
    String fotografia,resumenExperencia,fechaDeNacimiento;

}
