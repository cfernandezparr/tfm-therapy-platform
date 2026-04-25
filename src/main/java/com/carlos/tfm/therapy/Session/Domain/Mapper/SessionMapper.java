package com.carlos.tfm.therapy.Session.Domain.Mapper;

import com.carlos.tfm.therapy.Session.Domain.Entity.Session;
import com.carlos.tfm.therapy.Session.Infrastructure.DTO.Output.SessionOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    @Mapping(target = "appointmentId", expression = "java(session.getAppointment().getId())")
    SessionOutputDTO toOutputDTO(Session session);
}