package com.carlos.tfm.therapy.Session.Application.Service;

import com.carlos.tfm.therapy.Appointment.Domain.Entity.Appointment;
import com.carlos.tfm.therapy.Appointment.Infrastructure.Repository.AppointmentRepository;
import com.carlos.tfm.therapy.Exception.Exceptions.EntityExistsException;
import com.carlos.tfm.therapy.Exception.Exceptions.EntityNotFound;
import com.carlos.tfm.therapy.Exception.Exceptions.InvalidOperationException;
import com.carlos.tfm.therapy.Payment.Domain.Entity.PaymentStatus;
import com.carlos.tfm.therapy.Session.Domain.Entity.Session;
import com.carlos.tfm.therapy.Session.Domain.Entity.SessionStatus;
import com.carlos.tfm.therapy.Session.Domain.Mapper.SessionMapper;
import com.carlos.tfm.therapy.Session.Infrastructure.DTO.Input.SessionInputDTO;
import com.carlos.tfm.therapy.Session.Infrastructure.DTO.Output.SessionOutputDTO;
import com.carlos.tfm.therapy.Session.Infrastructure.Repository.SessionRepository;
import com.carlos.tfm.therapy.User.Domain.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionServiceImpl implements SessionService {

    private final SessionRepository sessionRepository;
    private final AppointmentRepository appointmentRepository;
    private final SessionMapper sessionMapper;

    @Value("${app.session.duration:45}")
    private int sessionDuration;

    @Value("${app.session.cancel.limit.hours:24}")
    private int cancelLimitHours;

    @Override
    public SessionOutputDTO create(SessionInputDTO sessionInputDTO) {

        Appointment appointment = appointmentRepository.findById(sessionInputDTO.getAppointmentId())
                .orElseThrow(() -> new EntityNotFound("Appointment not found"));

        if (sessionRepository.existsByAppointmentId(sessionInputDTO.getAppointmentId())) {
            throw new EntityExistsException("This appointment already has a session");
        }

        if (appointment.getPayment() == null ||
                appointment.getPayment().getStatus() != PaymentStatus.PAID) {
            throw new InvalidOperationException("Payment must be completed before creating session");
        }

        LocalDateTime startTime = appointment.getAppointmentDate();
        LocalDateTime endTime = startTime.plusMinutes(sessionDuration);

        String videoLink = "https://meet.jit.si/therapy-" + appointment.getId();

        Session session = Session.builder()
                .videoLink(videoLink)
                .startTime(startTime)
                .endTime(endTime)
                .therapistNotes(null)
                .status(SessionStatus.SCHEDULED)
                .appointment(appointment)
                .build();

        Session savedSession = sessionRepository.save(session);

        return sessionMapper.toOutputDTO(savedSession);
    }

    @Override
    public void cancelSession(Long sessionId) {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new EntityNotFound("Session not found"));

        if (session.getStatus() == SessionStatus.CANCELLED) {
            throw new InvalidOperationException("Session already cancelled");
        }

        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        boolean isPatient = session.getAppointment().getPatient().getId().equals(user.getId());
        boolean isTherapist = session.getAppointment().getTherapist().getId().equals(user.getId());

        if (!isPatient && !isTherapist) {
            throw new InvalidOperationException("You cannot cancel this session");
        }

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime start = session.getStartTime();

        if (now.isAfter(start.minusHours(cancelLimitHours))) {
            throw new InvalidOperationException("Cancellation period expired");
        }

        session.setStatus(SessionStatus.CANCELLED);

        if (session.getAppointment().getPayment() != null) {
            session.getAppointment().getPayment().setStatus(PaymentStatus.REFUNDED);
        }

        sessionRepository.save(session);
    }

    @Override
    public SessionOutputDTO getById(Long id) {
        Session session = sessionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Session not found"));

        return sessionMapper.toOutputDTO(session);
    }

    @Override
    public List<SessionOutputDTO> getAll() {
        return sessionRepository.findAll()
                .stream()
                .map(sessionMapper::toOutputDTO)
                .toList();
    }
}