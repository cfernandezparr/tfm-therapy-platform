package com.carlos.tfm.therapy.Availability.Infrastructure.Controller;

import com.carlos.tfm.therapy.Availability.Application.Service.AvailabilityService;
import com.carlos.tfm.therapy.Availability.Infrastructure.DTO.Input.AvailabilityInputDTO;
import com.carlos.tfm.therapy.Availability.Infrastructure.DTO.Output.AvailabilityOutputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/availability")
@RequiredArgsConstructor
public class AvailabilityController {

    private final AvailabilityService availabilityService;

    @PutMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void save(@RequestBody AvailabilityInputDTO dto) {
        availabilityService.saveAvailability(dto);
    }

    @GetMapping
    public List<AvailabilityOutputDTO> getMyAvailability() {
        return availabilityService.getMyAvailability();
    }

    @GetMapping("/{therapistId}")
    public List<AvailabilityOutputDTO> getByTherapist(@PathVariable Long therapistId) {
        return availabilityService.getByTherapistId(therapistId);
    }
}