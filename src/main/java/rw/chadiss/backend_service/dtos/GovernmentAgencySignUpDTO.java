package rw.chadiss.backend_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rw.chadiss.backend_service.security.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GovernmentAgencySignUpDTO extends CreateUserDTO {

    @NotBlank
    private String name;

    @NotNull
    private Long locationAddress;

}
