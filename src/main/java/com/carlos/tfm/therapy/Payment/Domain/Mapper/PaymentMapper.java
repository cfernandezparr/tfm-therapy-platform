package com.carlos.tfm.therapy.Payment.Domain.Mapper;

import com.carlos.tfm.therapy.Payment.Domain.Entity.Payment;
import com.carlos.tfm.therapy.Payment.Infrastructure.DTO.Output.PaymentOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    @Mapping(target = "appointmentId", expression = "java(payment.getAppointment().getId())")
    PaymentOutputDTO toOutputDTO(Payment payment);
}