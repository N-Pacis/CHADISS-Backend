package rw.chadiss.backend_service.dtos;

import lombok.Getter;
import rw.chadiss.backend_service.enums.EIssuedToType;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
public class ProposeSolutionAsAgencyDTO {

    @NotBlank
    String title;

    @NotBlank
    String description;

    @NotNull
    UUID issueId;
}
