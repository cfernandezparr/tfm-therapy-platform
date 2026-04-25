package com.carlos.tfm.therapy.Security.Controller;

import com.carlos.tfm.therapy.Security.DTO.JwtResponse;
import com.carlos.tfm.therapy.Security.DTO.LoginRequest;
import com.carlos.tfm.therapy.Security.DTO.RegisterRequest;
import com.carlos.tfm.therapy.Security.Service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<JwtResponse> register(@Valid @RequestBody RegisterRequest request) {
        String token = authService.register(request);
        return new ResponseEntity<>(new JwtResponse(token), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody LoginRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok(new JwtResponse(token));
    }
}