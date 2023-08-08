package rw.chadiss.backend_service.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import rw.chadiss.backend_service.enums.ERole;
import rw.chadiss.backend_service.enums.EUserStatus;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
@OnDelete(action = OnDeleteAction.CASCADE)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone_number",unique = true)
    private String phoneNumber;

    @Column(name = "email",unique = true)
    private String email;

    @Column(name = "national_id",unique = true)
    private String nationalId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private ERole role = ERole.ADMIN;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private EUserStatus status = EUserStatus.ACTIVE;

    @JsonIgnore
    @NotBlank
    @Column(name = "password")
    private String password;

    public User(String firstName, String lastName, String phoneNumber, String email, String nationalId, ERole role, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.nationalId = nationalId;
        this.role = role;
        this.password = password;
    }


}
