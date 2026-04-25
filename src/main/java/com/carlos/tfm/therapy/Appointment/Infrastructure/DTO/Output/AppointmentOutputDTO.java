package com.carlos.tfm.therapy.Appointment.Infrastructure.DTO.Output;

import com.carlos.tfm.therapy.Appointment.Domain.Entity.AppointmentStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppointmentOutputDTO {

    private Long id;
    private LocalDateTime appointmentDate;
    private AppointmentStatus status;
    private Long patientId;
    private String patientEmail;
    private Long therapistId;
    private String therapistEmail;
}