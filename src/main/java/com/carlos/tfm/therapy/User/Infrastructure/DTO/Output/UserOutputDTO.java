package com.carlos.tfm.therapy.User.Infrastructure.DTO.Output;

import com.carlos.tfm.therapy.User.Domain.Entity.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserOutputDTO {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private Role role;
    private Boolean enabled;
    private Boolean therapistRequested;
}