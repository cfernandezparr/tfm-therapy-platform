package com.carlos.tfm.therapy.Availability.Domain.Mapper;

import com.carlos.tfm.therapy.Availability.Domain.Entity.Availability;
import com.carlos.tfm.therapy.Availability.Infrastructure.DTO.Output.AvailabilityOutputDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AvailabilityMapper {

    public AvailabilityOutputDTO toOutputDTO(Availability availability) {
        return AvailabilityOutputDTO.builder()
                .dayOfWeek(availability.getDayOfWeek())
                .hour(availability.getHour())
                .build();
    }

    public List<AvailabilityOutputDTO> toOutputList(List<Availability> list) {
        return list.stream()
                .map(this::toOutputDTO)
                .toList();
    }
}