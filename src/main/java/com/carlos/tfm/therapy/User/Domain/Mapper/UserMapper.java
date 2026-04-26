package com.carlos.tfm.therapy.User.Domain.Mapper;

import com.carlos.tfm.therapy.User.Domain.Entity.User;
import com.carlos.tfm.therapy.User.Infrastructure.DTO.Input.UserInputDTO;
import com.carlos.tfm.therapy.User.Infrastructure.DTO.Output.UserOutputDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    User toEntity(UserInputDTO userInputDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "therapistRequested", source = "therapistRequested")
    UserOutputDTO toOutputDTO(User user);
}