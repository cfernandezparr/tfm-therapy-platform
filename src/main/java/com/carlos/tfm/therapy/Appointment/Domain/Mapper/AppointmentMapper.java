package com.carlos.tfm.therapy.Appointment.Domain.Mapper;

import com.carlos.tfm.therapy.Appointment.Domain.Entity.Appointment;
import com.carlos.tfm.therapy.Appointment.Infrastructure.DTO.Output.AppointmentOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(target = "patientId", expression = "java(appointment.getPatient().getId())")
    @Mapping(target = "patientEmail", expression = "java(appointment.getPatient().getEmail())")
    @Mapping(target = "therapistId", expression = "java(appointment.getTherapist().getId())")
    @Mapping(target = "therapistEmail", expression = "java(appointment.getTherapist().getEmail())")
    AppointmentOutputDTO toOutputDTO(Appointment appointment);
}