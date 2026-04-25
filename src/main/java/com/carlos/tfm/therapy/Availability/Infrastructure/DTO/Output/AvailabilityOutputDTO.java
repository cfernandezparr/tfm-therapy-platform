package com.carlos.tfm.therapy.Availability.Infrastructure.DTO.Output;

import lombok.*;

import java.time.DayOfWeek;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailabilityOutputDTO {

    private DayOfWeek dayOfWeek;
    private Integer hour;
}