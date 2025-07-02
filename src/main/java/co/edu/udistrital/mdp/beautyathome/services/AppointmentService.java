package co.edu.udistrital.mdp.beautyathome.services;

import co.edu.udistrital.mdp.beautyathome.dto.AppointmentViewDTO;
import co.edu.udistrital.mdp.beautyathome.entities.AppointmentEntity;
<<<<<<< HEAD
=======
import co.edu.udistrital.mdp.beautyathome.entities.ClientEntity;
>>>>>>> bb3985135ae4ac0249b7691d83b801a74dc0283a
import co.edu.udistrital.mdp.beautyathome.exceptions.EntityNotFoundException;
import co.edu.udistrital.mdp.beautyathome.exceptions.IllegalOperationException;
import co.edu.udistrital.mdp.beautyathome.repositories.AppointmentRepository;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
<<<<<<< HEAD
=======

@Slf4j
>>>>>>> bb3985135ae4ac0249b7691d83b801a74dc0283a

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    @Transactional
    public AppointmentEntity createAppointment(AppointmentEntity appointmentEntity) throws IllegalOperationException {
        if(appointmentEntity.getScheduledAt() == null){
            throw new IllegalOperationException("The scheuled date is not valid");
        }
        if(appointmentEntity.getService() == null){
            throw new IllegalOperationException("The client is not valid");
        }
        if(appointmentEntity.getProfessional() == null){
            throw new IllegalOperationException("The professional is not valid");
        }
        if(appointmentEntity.getClient() == null){
            throw new IllegalOperationException("The client is not valid");
        }
        if(appointmentEntity.getAgenda() == null){
            throw new IllegalOperationException("The agenda is not valid");
        }
        if(appointmentEntity.getCoverageAreaName() == null){
            throw new IllegalOperationException("The coverage area is not valid");
        }
        return appointmentRepository.save(appointmentEntity);
    }

    @Transactional
    public List<AppointmentEntity> getAppointments() {
        return appointmentRepository.findAll();
    }
<<<<<<< HEAD
=======
    
    /**
     * Obtiene un appointment por su ID.
     * 
     * @param id El ID del appointment a buscar.
     * @return El appointment encontrado.
     * @throws EntityNotFoundException Si el appointment no existe.
     */
    @Transactional
    public AppointmentEntity getAppointment(Long appointmentID) throws EntityNotFoundException{
        Optional<AppointmentEntity> appointmentEntity = appointmentRepository.findById(appointmentID);
        return appointmentEntity.orElseThrow(() -> new EntityNotFoundException("The appointment with the given id was not found: " + appointmentID));
    }

    /**
     * Actualiza un appointment existente.
     * 
     * @param appointmentId El ID del appointment a actualizar.
     * @param appointment El appointment con los datos actualizados.
     * @return El appointment actualizado.
     * @throws EntityNotFoundException Si el appointment no existe.
     * @throws IllegalOperationException Si el appointment no es válido.
     */
    @Transactional
    public AppointmentEntity updateAppointment(Long appointmentId, AppointmentEntity appointment) throws IllegalOperationException,EntityNotFoundException {
        log.info("Iniciando el proceso de actualización del appointment con id: {}", appointmentId);
        Optional<AppointmentEntity> optionalAppointmentEntity = appointmentRepository.findById(appointmentId);
        optionalAppointmentEntity.orElseThrow(() -> new EntityNotFoundException("The appointment with the given id was not found: " + appointmentId));
        appointment.setId(appointmentId);

        if (appointment.getScheduledAt() == null) {
            throw new IllegalOperationException("La fecha programada no puede ser nula");
        }
        if (appointment.getService() == null) {
            throw new IllegalOperationException("El servicio no puede ser nulo");
        }
        if (appointment.getProfessional() == null) {
            throw new IllegalOperationException("El profesional no puede ser nulo");
        }
        if (appointment.getClient() == null) {
            throw new IllegalOperationException("El cliente no puede ser nulo");
        }
        if (appointment.getAgenda() == null) {
            throw new IllegalOperationException("La agenda no puede ser nula");
        }
        
        AppointmentEntity updatedAppointment = appointmentRepository.save(appointment);
        log.info("Appointment actualizado con éxito: {}", updatedAppointment);
        return updatedAppointment;
    }
    /**
     * Elimina un appointment por su ID.
     * @param appointmentId El ID del appointment a eliminar.
     * @throws EntityNotFoundException Si el appointment no existe.
     */
    @Transactional
    public void deleteAppointment(Long appointmentId) throws EntityNotFoundException {
        Optional<AppointmentEntity> optionalAppointmentEntity = appointmentRepository.findById(appointmentId);
        optionalAppointmentEntity.orElseThrow(() -> new EntityNotFoundException("The appointment with the given id was not found: " + appointmentId));
        log.info("Iniciando el proceso de eliminación del appointment con id: {}", appointmentId);
        appointmentRepository.deleteById(appointmentId);
    }
}
>>>>>>> bb3985135ae4ac0249b7691d83b801a74dc0283a

    /**
     * Obtiene un appointment por su ID.
     * @param id El ID del appointment a buscar.
     * @return El appointment encontrado.
     * @throws EntityNotFoundException Si el appointment no existe.
     */
    @Transactional
    public AppointmentViewDTO getAppointment(Long appointmentID) throws EntityNotFoundException{
        AppointmentEntity appointmentEntity = appointmentRepository.findById(appointmentID).orElseThrow(() -> 
        new EntityNotFoundException("The appointment with the given id was not found: " + appointmentID));

        AppointmentViewDTO appointmentViewDTO = new AppointmentViewDTO();
        appointmentViewDTO.setClientName(appointmentEntity.getClient().getFullName());
        appointmentViewDTO.setCoverageAreaName(appointmentEntity.getCoverageAreaName());
        appointmentViewDTO.setServiceName(appointmentEntity.getService().getName());
        appointmentViewDTO.setProfessionalName(appointmentEntity.getProfessional().getName());
        appointmentViewDTO.setDate(appointmentEntity.getScheduledAt());
        return appointmentViewDTO;
    }

    /**
     * Actualiza un appointment existente.
     * 
     * @param appointmentId El ID del appointment a actualizar.
     * @param appointment El appointment con los datos actualizados.
     * @return El appointment actualizado.
     * @throws EntityNotFoundException Si el appointment no existe.
     * @throws IllegalOperationException Si el appointment no es válido.
     */
    @Transactional
    public AppointmentEntity updateAppointment(Long appointmentId, AppointmentEntity appointment) throws IllegalOperationException,EntityNotFoundException {
        Optional<AppointmentEntity> optionalAppointmentEntity = appointmentRepository.findById(appointmentId);
        optionalAppointmentEntity.orElseThrow(() -> new EntityNotFoundException("The appointment with the given id was not found: " + appointmentId));
        appointment.setId(appointmentId);

        if (appointment.getScheduledAt() == null) {
            throw new IllegalOperationException("La fecha programada no puede ser nula");
        }
        if (appointment.getService() == null) {
            throw new IllegalOperationException("El servicio no puede ser nulo");
        }
        if (appointment.getProfessional() == null) {
            throw new IllegalOperationException("El profesional no puede ser nulo");
        }
        if (appointment.getClient() == null) {
            throw new IllegalOperationException("El cliente no puede ser nulo");
        }
        if (appointment.getAgenda() == null) {
            throw new IllegalOperationException("La agenda no puede ser nula");
        }
        
        AppointmentEntity updatedAppointment = appointmentRepository.save(appointment);
        return updatedAppointment;
    }

    /**
     * Elimina un appointment por su ID.
     * @param appointmentId El ID del appointment a eliminar.
     * @throws EntityNotFoundException Si el appointment no existe.
     */
    @Transactional
    public void deleteAppointment(Long appointmentId) throws EntityNotFoundException {
        Optional<AppointmentEntity> optionalAppointmentEntity = appointmentRepository.findById(appointmentId);
        optionalAppointmentEntity.orElseThrow(() -> new EntityNotFoundException("The appointment with the given id was not found: " + appointmentId));
        appointmentRepository.deleteById(appointmentId);
    }
}