package com.carlos.tfm.therapy.Availability.Infrastructure.DTO.Input;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

import java.time.DayOfWeek;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AvailabilityInputDTO {

    @NotEmpty(message = "Slots are required")
    private List<SlotDTO> slots;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SlotDTO {
        private DayOfWeek dayOfWeek;
        private Integer hour;
    }
}