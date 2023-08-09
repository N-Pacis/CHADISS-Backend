package rw.chadiss.backend_service.dtos;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
public class CreateIssueDTO {

    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotBlank
    private String institutionName;

    @NotNull
    private LocalDate startDate;
}
