package com.carlos.tfm.therapy.Availability.Domain.Entity;

import com.carlos.tfm.therapy.User.Domain.Entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;

@Entity
@Table(name = "availability",
        uniqueConstraints = @UniqueConstraint(columnNames = {"therapist_id", "day_of_week", "hour"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Availability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "day_of_week", nullable = false)
    private DayOfWeek dayOfWeek;

    @Column(name = "hour_slot", nullable = false)
    private Integer hour;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "therapist_id", nullable = false)
    private User therapist;
}