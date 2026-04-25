package com.carlos.tfm.therapy.Session.Infrastructure.Controller;

import com.carlos.tfm.therapy.Session.Application.Service.SessionService;
import com.carlos.tfm.therapy.Session.Infrastructure.DTO.Input.SessionInputDTO;
import com.carlos.tfm.therapy.Session.Infrastructure.DTO.Output.SessionOutputDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@RequiredArgsConstructor
public class SessionController {

    private final SessionService sessionService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SessionOutputDTO create(@RequestBody @Valid SessionInputDTO sessionInputDTO) {
        return sessionService.create(sessionInputDTO);
    }

    @GetMapping("/{id}")
    public SessionOutputDTO getById(@PathVariable Long id) {
        return sessionService.getById(id);
    }

    @GetMapping
    public List<SessionOutputDTO> getAll() {
        return sessionService.getAll();
    }

    @PutMapping("/{id}/cancel")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancel(@PathVariable Long id) {
        sessionService.cancelSession(id);
    }
}