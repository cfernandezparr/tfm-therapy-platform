package com.carlos.tfm.therapy.Payment.Infrastructure.Repository;

import com.carlos.tfm.therapy.Payment.Domain.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    boolean existsByAppointmentId(Long appointmentId);
    Optional<Payment> findByAppointmentId(Long appointmentId);
}