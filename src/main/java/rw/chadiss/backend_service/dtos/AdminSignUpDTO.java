package rw.chadiss.backend_service.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rw.chadiss.backend_service.enums.EGender;
import rw.chadiss.backend_service.security.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AdminSignUpDTO extends CreateUserDTO{

    @NotBlank
    private String key;
}
