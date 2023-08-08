package rw.chadiss.backend_service.dtos;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import rw.chadiss.backend_service.enums.EGender;
import rw.chadiss.backend_service.enums.EMaritalStatus;
import rw.chadiss.backend_service.security.ValidPassword;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CitizenSignUpDTO extends CreateUserDTO{

    @NotNull
    private Long locationAddress;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateOfBirth;

    @NotNull
    private Long birthPlaceAddress;

    @NotNull
    private EMaritalStatus maritalStatus;

    @NotNull
    private EGender gender;
}
