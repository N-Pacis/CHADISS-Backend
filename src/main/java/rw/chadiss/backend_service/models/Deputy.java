package rw.chadiss.backend_service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.chadiss.backend_service.dtos.CitizenSignUpDTO;
import rw.chadiss.backend_service.dtos.DeputySignUpDTO;
import rw.chadiss.backend_service.enums.EGender;
import rw.chadiss.backend_service.enums.EMaritalStatus;
import rw.chadiss.backend_service.enums.ERole;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "deputies")
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
public class Deputy extends User {

    @ManyToOne
    @JoinColumn(name = "location_address")
    private LocationAddress locationAddress;


    public Deputy(DeputySignUpDTO dto, String password, LocationAddress locationAddress) {
        super(dto.getFirstName(),dto.getLastName(),dto.getMobile(),dto.getEmail(),dto.getNationalId(), ERole.DEPUTY,password);

        this.locationAddress = locationAddress;
    }


}

