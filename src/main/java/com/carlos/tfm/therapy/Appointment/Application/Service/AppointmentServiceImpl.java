package com.carlos.tfm.therapy.Appointment.Application.Service;

import com.carlos.tfm.therapy.Appointment.Domain.Entity.Appointment;
import com.carlos.tfm.therapy.Appointment.Domain.Entity.AppointmentStatus;
import com.carlos.tfm.therapy.Appointment.Domain.Mapper.AppointmentMapper;
import com.carlos.tfm.therapy.Appointment.Infrastructure.DTO.Input.AppointmentInputDTO;
import com.carlos.tfm.therapy.Appointment.Infrastructure.DTO.Output.AppointmentOutputDTO;
import com.carlos.tfm.therapy.Appointment.Infrastructure.Repository.AppointmentRepository;
import com.carlos.tfm.therapy.Availability.Infrastructure.Repository.AvailabilityRepository;
import com.carlos.tfm.therapy.Exception.Exceptions.EntityNotFound;
import com.carlos.tfm.therapy.Exception.Exceptions.InvalidOperationException;
import com.carlos.tfm.therapy.User.Domain.Entity.Role;
import com.carlos.tfm.therapy.User.Domain.Entity.User;
import com.carlos.tfm.therapy.User.Infrastructure.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final UserRepository userRepository;
    private final AppointmentMapper appointmentMapper;
    private final AvailabilityRepository availabilityRepository;

    @Override
    public AppointmentOutputDTO create(AppointmentInputDTO appointmentInputDTO) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User patient = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFound("User not found"));

        if (patient.getRole() != Role.USER) {
            throw new InvalidOperationException("Only USER can create appointments");
        }

        User therapist = userRepository.findById(appointmentInputDTO.getTherapistId())
                .orElseThrow(() -> new EntityNotFound("Therapist not found"));

        if (therapist.getRole() != Role.THERAPIST) {
            throw new InvalidOperationException("Selected therapist is not valid");
        }

        LocalDateTime appointmentDate = appointmentInputDTO.getAppointmentDate();
        DayOfWeek dayOfWeek = appointmentDate.getDayOfWeek();
        int hour = appointmentDate.getHour();

        boolean available = availabilityRepository
                .existsByTherapistAndDayOfWeekAndHour(therapist, dayOfWeek, hour);

        if (!available) {
            throw new InvalidOperationException("Selected time is not available");
        }

        boolean alreadyBooked = appointmentRepository
                .existsByTherapistAndAppointmentDate(therapist, appointmentDate);

        if (alreadyBooked) {
            throw new InvalidOperationException("This time slot is already booked");
        }

        Appointment appointment = Appointment.builder()
                .appointmentDate(appointmentDate)
                .status(AppointmentStatus.PENDING)
                .patient(patient)
                .therapist(therapist)
                .build();

        Appointment savedAppointment = appointmentRepository.save(appointment);

        return appointmentMapper.toOutputDTO(savedAppointment);
    }

    @Override
    public AppointmentOutputDTO getById(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Appointment not found"));

        return appointmentMapper.toOutputDTO(appointment);
    }

    @Override
    public List<AppointmentOutputDTO> getAll() {
        return appointmentRepository.findAll()
                .stream()
                .map(appointmentMapper::toOutputDTO)
                .toList();
    }

    @Override
    public List<AppointmentOutputDTO> getMyAppointments() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFound("User not found"));

        if (user.getRole() == Role.USER) {
            return appointmentRepository.findByPatient(user)
                    .stream()
                    .map(appointmentMapper::toOutputDTO)
                    .toList();
        }

        if (user.getRole() == Role.THERAPIST) {
            return appointmentRepository.findByTherapist(user)
                    .stream()
                    .map(appointmentMapper::toOutputDTO)
                    .toList();
        }

        return List.of();
    }
}