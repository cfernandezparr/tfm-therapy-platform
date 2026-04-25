package com.carlos.tfm.therapy.Availability.Infrastructure.Repository;

import com.carlos.tfm.therapy.Availability.Domain.Entity.Availability;
import com.carlos.tfm.therapy.User.Domain.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.DayOfWeek;
import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {

    List<Availability> findByTherapist(User therapist);

    List<Availability> findByTherapistAndDayOfWeek(User therapist, DayOfWeek dayOfWeek);

    boolean existsByTherapistAndDayOfWeekAndHour(User therapist, DayOfWeek dayOfWeek, Integer hour);
}