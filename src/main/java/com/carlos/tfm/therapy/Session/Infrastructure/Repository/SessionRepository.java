package com.carlos.tfm.therapy.Session.Infrastructure.Repository;

import com.carlos.tfm.therapy.Session.Domain.Entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SessionRepository extends JpaRepository<Session, Long> {

    boolean existsByAppointmentId(Long appointmentId);
}