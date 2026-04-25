package com.carlos.tfm.therapy.User.Application.Service;

import com.carlos.tfm.therapy.User.Infrastructure.DTO.Input.UserInputDTO;
import com.carlos.tfm.therapy.User.Infrastructure.DTO.Output.UserOutputDTO;

import java.util.List;

public interface UserService {

    UserOutputDTO create(UserInputDTO userInputDTO);

    UserOutputDTO getById(Long id);

    List<UserOutputDTO> getAll();

    UserOutputDTO getCurrentUser();

    void requestTherapist();

    void makeTherapist(Long id);
}