package rw.chadiss.backend_service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.chadiss.backend_service.dtos.DeputySignUpDTO;
import rw.chadiss.backend_service.dtos.GovernmentAgencySignUpDTO;
import rw.chadiss.backend_service.enums.EGender;
import rw.chadiss.backend_service.enums.EMaritalStatus;
import rw.chadiss.backend_service.enums.ERole;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "government_agencies")
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
public class GovernmentAgency extends User {

    @Column
    private String name;

    @ManyToOne
    @JoinColumn(name = "location_address")
    private LocationAddress locationAddress;


    public GovernmentAgency(GovernmentAgencySignUpDTO dto, String password, LocationAddress locationAddress) {
        super(dto.getFirstName(),dto.getLastName(),dto.getMobile(),dto.getEmail(),dto.getNationalId(), ERole.GOVERNMENT_AGENCY,password);

        this.name = dto.getName();
        this.locationAddress = locationAddress;
    }


}

