package com.carlos.tfm.therapy.Session.Infrastructure.DTO.Input;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionInputDTO {

    @NotNull(message = "Appointment id is required")
    private Long appointmentId;
}