package rw.chadiss.backend_service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.chadiss.backend_service.dtos.DeputySignUpDTO;
import rw.chadiss.backend_service.dtos.OmbudsmanSignUpDTO;
import rw.chadiss.backend_service.enums.EGender;
import rw.chadiss.backend_service.enums.EMaritalStatus;
import rw.chadiss.backend_service.enums.ERole;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "ombudsmans")
@Inheritance(strategy = InheritanceType.JOINED)
@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
public class Ombudsman extends User {

    public Ombudsman(OmbudsmanSignUpDTO dto, String password) {
        super(dto.getFirstName(),dto.getLastName(),dto.getMobile(),dto.getEmail(),dto.getNationalId(), ERole.OMBUDSMAN,password);

    }



}

