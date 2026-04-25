package com.carlos.tfm.therapy.Session.Infrastructure.DTO.Output;

import com.carlos.tfm.therapy.Session.Domain.Entity.SessionStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SessionOutputDTO {

    private Long id;
    private String videoLink;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String notes;
    private SessionStatus status;
    private Long appointmentId;
}