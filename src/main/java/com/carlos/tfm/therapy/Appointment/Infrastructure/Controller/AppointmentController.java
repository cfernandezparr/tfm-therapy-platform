package com.carlos.tfm.therapy.Appointment.Infrastructure.Controller;

import com.carlos.tfm.therapy.Appointment.Application.Service.AppointmentService;
import com.carlos.tfm.therapy.Appointment.Infrastructure.DTO.Input.AppointmentInputDTO;
import com.carlos.tfm.therapy.Appointment.Infrastructure.DTO.Output.AppointmentOutputDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AppointmentOutputDTO create(@RequestBody @Valid AppointmentInputDTO appointmentInputDTO) {
        return appointmentService.create(appointmentInputDTO);
    }

    @GetMapping("/{id}")
    public AppointmentOutputDTO getById(@PathVariable Long id) {
        return appointmentService.getById(id);
    }

    @GetMapping
    public List<AppointmentOutputDTO> getAll() {
        return appointmentService.getAll();
    }
}