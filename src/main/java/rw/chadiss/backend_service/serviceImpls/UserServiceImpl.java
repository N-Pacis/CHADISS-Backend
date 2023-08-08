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
import rw.chadiss.backend_service.services.IUserService;

import java.util.*;

@Service
public class UserServiceImpl implements IUserService {

    private final IUserRepository userRepository;

    @Autowired
    public UserServiceImpl( IUserRepository userRepository) {
        this.userRepository = userRepository;
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
}
