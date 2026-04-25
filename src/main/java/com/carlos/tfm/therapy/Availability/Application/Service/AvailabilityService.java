package com.carlos.tfm.therapy.Availability.Application.Service;

import com.carlos.tfm.therapy.Availability.Infrastructure.DTO.Input.AvailabilityInputDTO;
import com.carlos.tfm.therapy.Availability.Infrastructure.DTO.Output.AvailabilityOutputDTO;

import java.util.List;

public interface AvailabilityService {

    void saveAvailability(AvailabilityInputDTO dto);

    List<AvailabilityOutputDTO> getMyAvailability();

    List<AvailabilityOutputDTO> getByTherapistId(Long therapistId);
}