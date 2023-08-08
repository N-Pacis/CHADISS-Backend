package rw.chadiss.backend_service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.chadiss.backend_service.dtos.CitizenSignUpDTO;
import rw.chadiss.backend_service.enums.EGender;
import rw.chadiss.backend_service.enums.EMaritalStatus;
import rw.chadiss.backend_service.enums.ERole;

import javax.persistence.*;
import java.time.LocalDate;


@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "citizens")
@Inheritance(strategy = InheritanceType.JOINED)
@AllArgsConstructor
@PrimaryKeyJoinColumn(name = "user_id", referencedColumnName = "id")
public class Citizen extends User {

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name = "location_address")
    private LocationAddress locationAddress;

    @ManyToOne
    @JoinColumn(name = "place_of_birth_address")
    private LocationAddress placeOfBirthAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "marital_status")
    private EMaritalStatus maritalStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private EGender gender;

    public Citizen(CitizenSignUpDTO dto,String password,LocationAddress locationAddress, LocationAddress placeOfBirthAddress) {
        super(dto.getFirstName(),dto.getLastName(),dto.getMobile(),dto.getEmail(),dto.getNationalId(), ERole.CITIZEN,password);
        this.birthDate = dto.getDateOfBirth();
        this.locationAddress = locationAddress;
        this.placeOfBirthAddress = placeOfBirthAddress;
        this.maritalStatus = dto.getMaritalStatus();
        this.gender = dto.getGender();
    }
}

