package rw.chadiss.backend_service.serviceImpls;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import rw.chadiss.backend_service.enums.ERole;
import rw.chadiss.backend_service.enums.EUserStatus;
import rw.chadiss.backend_service.models.User;
import rw.chadiss.backend_service.exceptions.BadRequestException;
import rw.chadiss.backend_service.exceptions.ResourceNotFoundException;
import rw.chadiss.backend_service.repositories.IUserRepository;
import rw.chadiss.backend_service.services.*;
import rw.chadiss.backend_service.utils.Profile;

import java.util.*;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    private final ICitizenService citizenService;

    private final IOmbudsmanService ombudsmanService;

    private final IGovernmentAgencyService governmentAgencyService;

    private final IDeputyService deputyService;

    @Autowired
    public UserServiceImpl(IUserRepository userRepository, ICitizenService citizenService, IOmbudsmanService ombudsmanService, IGovernmentAgencyService governmentAgencyService, IDeputyService deputyService) {
        this.userRepository = userRepository;
        this.citizenService = citizenService;
        this.ombudsmanService = ombudsmanService;
        this.governmentAgencyService = governmentAgencyService;
        this.deputyService = deputyService;
    }

    @Override
    public User create(User user) {
        validateNewRegistration(user);

        return this.userRepository.save(user);
    }

    @Override
    public boolean isNotUnique(User user) {
        Optional<User> userOptional = this.userRepository.findByEmailOrPhoneNumberOrNationalId(user.getEmail(), user.getPhoneNumber(), user.getNationalId());
        return userOptional.isPresent();
    }

    @Override
    public void validateNewRegistration(User user) {
        if (isNotUnique(user)) {
            throw new BadRequestException(String.format("User with email '%s' or phone number '%s' or national id '%s' already exists", user.getEmail(), user.getPhoneNumber(), user.getNationalId()));
        }
    }

    @Override
    public User getLoggedInUser() {
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() == "anonymousUser")
            throw new BadRequestException("You are not logged in, try to log in");

        String email;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            email = ((UserDetails) principal).getUsername();
        } else {
            email = principal.toString();
        }

        return userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User", "id", email));
    }

    @Override
    public Page<User> search(EUserStatus status, String name, ERole role, Pageable pageable) {
        return userRepository.search(status,name,role,pageable);
    }

    @Override
    public Profile getLoggedInProfile() {
        User theUser = getLoggedInUser();
        Object profile;
        ERole role = theUser.getRole();
        profile = switch (role) {
            case CITIZEN -> citizenService.findByUser(theUser);
            case DEPUTY -> deputyService.findByUser(theUser);
            case OMBUDSMAN -> ombudsmanService.findByUser(theUser);
            case GOVERNMENT_AGENCY -> governmentAgencyService.findByUser(theUser);
            default -> throw new IllegalStateException("Unexpected value: " + role);
        };
            return new Profile(profile);
    }
}
