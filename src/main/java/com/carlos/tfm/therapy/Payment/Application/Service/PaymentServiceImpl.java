package com.carlos.tfm.therapy.Payment.Application.Service;

import com.carlos.tfm.therapy.Appointment.Domain.Entity.Appointment;
import com.carlos.tfm.therapy.Appointment.Infrastructure.Repository.AppointmentRepository;
import com.carlos.tfm.therapy.Exception.Exceptions.EntityExistsException;
import com.carlos.tfm.therapy.Exception.Exceptions.EntityNotFound;
import com.carlos.tfm.therapy.Exception.Exceptions.InvalidOperationException;
import com.carlos.tfm.therapy.Payment.Domain.Entity.Payment;
import com.carlos.tfm.therapy.Payment.Domain.Entity.PaymentStatus;
import com.carlos.tfm.therapy.Payment.Domain.Mapper.PaymentMapper;
import com.carlos.tfm.therapy.Payment.Infrastructure.DTO.Input.PaymentInputDTO;
import com.carlos.tfm.therapy.Payment.Infrastructure.DTO.Output.PaymentOutputDTO;
import com.carlos.tfm.therapy.Payment.Infrastructure.Repository.PaymentRepository;
import com.carlos.tfm.therapy.Payment.Infrastructure.Stripe.StripeService;
import com.carlos.tfm.therapy.Session.Application.Service.SessionService;
import com.carlos.tfm.therapy.Session.Infrastructure.DTO.Input.SessionInputDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final AppointmentRepository appointmentRepository;
    private final PaymentMapper paymentMapper;
    private final StripeService stripeService;

    // 👉 NUEVO
    private final SessionService sessionService;

    @Override
    public PaymentOutputDTO create(PaymentInputDTO paymentInputDTO) {
        Appointment appointment = appointmentRepository.findById(paymentInputDTO.getAppointmentId())
                .orElseThrow(() -> new EntityNotFound("Appointment not found"));

        if (paymentRepository.existsByAppointmentId(paymentInputDTO.getAppointmentId())) {
            throw new EntityExistsException("This appointment already has a payment");
        }

        Payment payment = Payment.builder()
                .amount(paymentInputDTO.getAmount())
                .status(PaymentStatus.PENDING)
                .paymentDate(LocalDateTime.now())
                .externalReference(paymentInputDTO.getExternalReference())
                .appointment(appointment)
                .build();

        Payment savedPayment = paymentRepository.save(payment);

        return paymentMapper.toOutputDTO(savedPayment);
    }

    @Override
    public PaymentOutputDTO createIntent(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new EntityNotFound("Appointment not found"));

        if (paymentRepository.existsByAppointmentId(appointmentId)) {
            throw new EntityExistsException("This appointment already has a payment");
        }

        String externalReference = stripeService.createPaymentIntent();

        Payment payment = Payment.builder()
                .amount(java.math.BigDecimal.ZERO)
                .status(PaymentStatus.PENDING)
                .paymentDate(LocalDateTime.now())
                .externalReference(externalReference)
                .appointment(appointment)
                .build();

        Payment saved = paymentRepository.save(payment);

        return paymentMapper.toOutputDTO(saved);
    }

    @Override
    public PaymentOutputDTO confirmPayment(Long appointmentId) {
        Payment payment = paymentRepository.findByAppointmentId(appointmentId)
                .orElseThrow(() -> new EntityNotFound("Payment not found"));

        if (payment.getStatus() == PaymentStatus.PAID) {
            throw new InvalidOperationException("Payment already completed");
        }

        boolean confirmed = stripeService.confirmPayment(payment.getExternalReference());

        if (!confirmed) {
            throw new InvalidOperationException("Payment failed");
        }

        payment.setStatus(PaymentStatus.PAID);
        payment.setPaymentDate(LocalDateTime.now());

        Payment savedPayment = paymentRepository.save(payment);

        SessionInputDTO sessionInputDTO = SessionInputDTO.builder()
                .appointmentId(appointmentId)
                .build();

        sessionService.create(sessionInputDTO);

        return paymentMapper.toOutputDTO(savedPayment);
    }

    @Override
    public PaymentOutputDTO getById(Long id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("Payment not found"));

        return paymentMapper.toOutputDTO(payment);
    }

    @Override
    public List<PaymentOutputDTO> getAll() {
        return paymentRepository.findAll()
                .stream()
                .map(paymentMapper::toOutputDTO)
                .toList();
    }
}