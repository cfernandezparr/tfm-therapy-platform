package com.carlos.tfm.therapy.Appointment.Application.Service;

import com.carlos.tfm.therapy.Appointment.Infrastructure.DTO.Input.AppointmentInputDTO;
import com.carlos.tfm.therapy.Appointment.Infrastructure.DTO.Output.AppointmentOutputDTO;

import java.util.List;

public interface AppointmentService {

    AppointmentOutputDTO create(AppointmentInputDTO appointmentInputDTO);

    AppointmentOutputDTO getById(Long id);

    List<AppointmentOutputDTO> getAll();

    List<AppointmentOutputDTO> getMyAppointments();
}