package rw.chadiss.backend_service.services;

import org.springframework.stereotype.Service;
import rw.chadiss.backend_service.dtos.GovernmentAgencySignUpDTO;
import rw.chadiss.backend_service.models.GovernmentAgency;
import rw.chadiss.backend_service.models.User;

import java.util.List;
import java.util.UUID;

public interface IGovernmentAgencyService {

    List<GovernmentAgency> findAll();

    GovernmentAgency findById(UUID id);

    GovernmentAgency findByUser(User user);

    GovernmentAgency create(GovernmentAgencySignUpDTO dto);
}
