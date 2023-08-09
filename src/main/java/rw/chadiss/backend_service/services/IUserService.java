package rw.chadiss.backend_service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import rw.chadiss.backend_service.enums.ERole;
import rw.chadiss.backend_service.enums.EUserStatus;
import rw.chadiss.backend_service.models.User;
import rw.chadiss.backend_service.utils.Profile;


public interface IUserService {

    User create(User user);

    boolean isNotUnique(User user);

    void validateNewRegistration(User user);

    User getLoggedInUser();

    Page<User> search(EUserStatus status, String name, ERole role, Pageable pageable);

    Profile getLoggedInProfile();


}