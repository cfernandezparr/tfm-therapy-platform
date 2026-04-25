package com.carlos.tfm.therapy.Security.Service;

import com.carlos.tfm.therapy.Security.DTO.LoginRequest;
import com.carlos.tfm.therapy.Security.DTO.RegisterRequest;
import com.carlos.tfm.therapy.User.Domain.Entity.User;
import com.carlos.tfm.therapy.User.Domain.Entity.Role;
import com.carlos.tfm.therapy.User.Infrastructure.Repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("A user with this email already exists");
        }

        if (request.getRole() != null && request.getRole() == Role.ADMIN) {
            throw new RuntimeException("Cannot register as ADMIN");
        }

        Role roleToAssign;

        if (request.getRole() == null) {
            roleToAssign = Role.USER;
        } else {
            roleToAssign = request.getRole();
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .role(roleToAssign)
                .enabled(true)
                .build();

        userRepository.save(user);

        return jwtService.generateToken(user);
    }

    public String login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        User user = (User) authentication.getPrincipal();

        return jwtService.generateToken(user);
    }
}