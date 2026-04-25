package com.carlos.tfm.therapy.Payment.Application.Service;

import com.carlos.tfm.therapy.Payment.Infrastructure.DTO.Input.PaymentInputDTO;
import com.carlos.tfm.therapy.Payment.Infrastructure.DTO.Output.PaymentOutputDTO;

import java.util.List;

public interface PaymentService {

    PaymentOutputDTO create(PaymentInputDTO paymentInputDTO);

    PaymentOutputDTO getById(Long id);

    List<PaymentOutputDTO> getAll();

    PaymentOutputDTO createIntent(Long appointmentId);

    PaymentOutputDTO confirmPayment(Long appointmentId);
}