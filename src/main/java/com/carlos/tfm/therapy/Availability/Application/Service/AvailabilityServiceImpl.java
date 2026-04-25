package com.carlos.tfm.therapy.Availability.Application.Service;

import com.carlos.tfm.therapy.Availability.Domain.Entity.Availability;
import com.carlos.tfm.therapy.Availability.Domain.Mapper.AvailabilityMapper;
import com.carlos.tfm.therapy.Availability.Infrastructure.DTO.Input.AvailabilityInputDTO;
import com.carlos.tfm.therapy.Availability.Infrastructure.DTO.Output.AvailabilityOutputDTO;
import com.carlos.tfm.therapy.Availability.Infrastructure.Repository.AvailabilityRepository;
import com.carlos.tfm.therapy.Exception.Exceptions.EntityNotFound;
import com.carlos.tfm.therapy.Exception.Exceptions.InvalidOperationException;
import com.carlos.tfm.therapy.User.Domain.Entity.Role;
import com.carlos.tfm.therapy.User.Domain.Entity.User;
import com.carlos.tfm.therapy.User.Infrastructure.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AvailabilityServiceImpl implements AvailabilityService {

    private final AvailabilityRepository availabilityRepository;
    private final AvailabilityMapper availabilityMapper;
    private final UserRepository userRepository;

    @Override
    public void saveAvailability(AvailabilityInputDTO dto) {

        User therapist = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (therapist.getRole() != Role.THERAPIST) {
            throw new InvalidOperationException("Only therapists can define availability");
        }

        availabilityRepository.deleteAll(
                availabilityRepository.findByTherapist(therapist)
        );

        dto.getSlots().forEach(slot -> {
            Availability availability = Availability.builder()
                    .therapist(therapist)
                    .dayOfWeek(slot.getDayOfWeek())
                    .hour(slot.getHour())
                    .build();

            availabilityRepository.save(availability);
        });
    }

    @Override
    public List<AvailabilityOutputDTO> getMyAvailability() {

        User therapist = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Availability> availabilityList = availabilityRepository.findByTherapist(therapist);

        return availabilityMapper.toOutputList(availabilityList);
    }

    @Override
    public List<AvailabilityOutputDTO> getByTherapistId(Long therapistId) {

        User therapist = userRepository.findById(therapistId)
                .orElseThrow(() -> new EntityNotFound("Therapist not found"));

        List<Availability> availabilityList = availabilityRepository.findByTherapist(therapist);

        return availabilityMapper.toOutputList(availabilityList);
    }
}