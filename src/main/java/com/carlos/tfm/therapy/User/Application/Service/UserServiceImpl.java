package com.carlos.tfm.therapy.User.Application.Service;

import com.carlos.tfm.therapy.Exception.Exceptions.EntityExistsException;
import com.carlos.tfm.therapy.Exception.Exceptions.EntityNotFound;
import com.carlos.tfm.therapy.User.Domain.Entity.Role;
import com.carlos.tfm.therapy.User.Domain.Entity.User;
import com.carlos.tfm.therapy.User.Domain.Mapper.UserMapper;
import com.carlos.tfm.therapy.User.Infrastructure.DTO.Input.UserInputDTO;
import com.carlos.tfm.therapy.User.Infrastructure.DTO.Output.UserOutputDTO;
import com.carlos.tfm.therapy.User.Infrastructure.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserOutputDTO create(UserInputDTO userInputDTO) {
        if (userRepository.existsByEmail(userInputDTO.getEmail())) {
            throw new EntityExistsException("A user with this email already exists");
        }

        User user = userMapper.toEntity(userInputDTO);
        User savedUser = userRepository.save(user);

        return userMapper.toOutputDTO(savedUser);
    }

    @Override
    public UserOutputDTO getById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("User not found"));

        return userMapper.toOutputDTO(user);
    }

    @Override
    public List<UserOutputDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toOutputDTO)
                .toList();
    }

    @Override
    public UserOutputDTO getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFound("User not found"));

        return userMapper.toOutputDTO(user);
    }

    @Override
    public void requestTherapist() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFound("User not found"));

        user.setTherapistRequested(true);
        userRepository.save(user);
    }

    @Override
    public void makeTherapist(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("User not found"));

        user.setRole(Role.THERAPIST);
        user.setTherapistRequested(false);

        userRepository.save(user);
    }

    @Override
    public void rejectTherapist(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFound("User not found"));

        user.setTherapistRequested(false);

        userRepository.save(user);
    }
}