package com.carlos.tfm.therapy.Appointment.Infrastructure.Repository;

import com.carlos.tfm.therapy.Appointment.Domain.Entity.Appointment;
import com.carlos.tfm.therapy.User.Domain.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    boolean existsByTherapistAndAppointmentDate(User therapist, LocalDateTime appointmentDate);
    List<Appointment> findByPatient(User patient);
    List<Appointment> findByTherapist(User therapist);
}