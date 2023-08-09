package rw.chadiss.backend_service.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import rw.chadiss.backend_service.enums.ERole;
import rw.chadiss.backend_service.exceptions.BadRequestException;
import rw.chadiss.backend_service.models.*;

@Data
@AllArgsConstructor
public class Profile {
    Object profile;

    public User asUser() {
        return (User) profile;
    }

    public Citizen asCitizen() {
        is(ERole.CITIZEN);
        return (Citizen) profile;
    }

    public Deputy asDeputy() {
        is(ERole.DEPUTY);
        return (Deputy) profile;
    }

    public Ombudsman asOmbudsman() {
        is(ERole.OMBUDSMAN);
        return (Ombudsman) profile;
    }

    public GovernmentAgency asGovernmentAgency() {
        is(ERole.GOVERNMENT_AGENCY);
        return (GovernmentAgency) profile;
    }
    private void is(ERole role) {
        User user = (User) profile;
        if (user.getRole() != role)
            throw new BadRequestException("You must be a " + role.toString() + " to use this resource ...");
    }
}
