package com.carlos.tfm.therapy.Session.Application.Service;

import com.carlos.tfm.therapy.Session.Infrastructure.DTO.Input.SessionInputDTO;
import com.carlos.tfm.therapy.Session.Infrastructure.DTO.Output.SessionOutputDTO;

import java.util.List;

public interface SessionService {

    SessionOutputDTO create(SessionInputDTO sessionInputDTO);

    SessionOutputDTO getById(Long id);

    List<SessionOutputDTO> getAll();

    void cancelSession(Long sessionId);
}