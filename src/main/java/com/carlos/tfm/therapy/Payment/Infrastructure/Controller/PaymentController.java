package com.carlos.tfm.therapy.Payment.Infrastructure.Controller;

import com.carlos.tfm.therapy.Payment.Application.Service.PaymentService;
import com.carlos.tfm.therapy.Payment.Infrastructure.DTO.Input.PaymentInputDTO;
import com.carlos.tfm.therapy.Payment.Infrastructure.DTO.Output.PaymentOutputDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentOutputDTO create(@RequestBody @Valid PaymentInputDTO paymentInputDTO) {
        return paymentService.create(paymentInputDTO);
    }

    @PostMapping("/intent/{appointmentId}")
    public PaymentOutputDTO createIntent(@PathVariable Long appointmentId) {
        return paymentService.createIntent(appointmentId);
    }

    @PostMapping("/confirm/{appointmentId}")
    public PaymentOutputDTO confirmPayment(@PathVariable Long appointmentId) {
        return paymentService.confirmPayment(appointmentId);
    }

    @GetMapping("/{id}")
    public PaymentOutputDTO getById(@PathVariable Long id) {
        return paymentService.getById(id);
    }

    @GetMapping
    public List<PaymentOutputDTO> getAll() {
        return paymentService.getAll();
    }
}