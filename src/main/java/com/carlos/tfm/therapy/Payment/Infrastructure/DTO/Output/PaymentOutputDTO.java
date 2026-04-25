package com.carlos.tfm.therapy.Payment.Infrastructure.DTO.Output;

import com.carlos.tfm.therapy.Payment.Domain.Entity.PaymentStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentOutputDTO {

    private Long id;
    private BigDecimal amount;
    private PaymentStatus status;
    private LocalDateTime paymentDate;
    private String externalReference;
    private Long appointmentId;
}