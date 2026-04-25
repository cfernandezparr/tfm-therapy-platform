package com.carlos.tfm.therapy.Appointment.Domain.Entity;

import com.carlos.tfm.therapy.Payment.Domain.Entity.Payment;
import com.carlos.tfm.therapy.Session.Domain.Entity.Session;
import com.carlos.tfm.therapy.User.Domain.Entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime appointmentDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AppointmentStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private User patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "therapist_id", nullable = false)
    private User therapist;

    @OneToOne(mappedBy = "appointment", fetch = FetchType.LAZY)
    private Payment payment;

    @OneToOne(mappedBy = "appointment", fetch = FetchType.LAZY)
    private Session session;
}