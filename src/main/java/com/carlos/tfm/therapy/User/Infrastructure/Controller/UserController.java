package com.carlos.tfm.therapy.User.Infrastructure.Controller;

import com.carlos.tfm.therapy.User.Application.Service.UserService;
import com.carlos.tfm.therapy.User.Infrastructure.DTO.Input.UserInputDTO;
import com.carlos.tfm.therapy.User.Infrastructure.DTO.Output.UserOutputDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserOutputDTO create(@RequestBody @Valid UserInputDTO userInputDTO) {
        return userService.create(userInputDTO);
    }

    @GetMapping("/{id}")
    public UserOutputDTO getById(@PathVariable Long id) {
        return userService.getById(id);
    }

    @GetMapping
    public List<UserOutputDTO> getAll() {
        return userService.getAll();
    }

    @GetMapping("/me")
    public UserOutputDTO getCurrentUser() {
        return userService.getCurrentUser();
    }

    @PostMapping("/request-therapist")
    @ResponseStatus(HttpStatus.OK)
    public void requestTherapist() {
        userService.requestTherapist();
    }

    @PutMapping("/{id}/make-therapist")
    @ResponseStatus(HttpStatus.OK)
    public void makeTherapist(@PathVariable Long id) {
        userService.makeTherapist(id);
    }

    @PutMapping("/{id}/reject-therapist")
    @ResponseStatus(HttpStatus.OK)
    public void rejectTherapist(@PathVariable Long id) {
        userService.rejectTherapist(id);
    }
}