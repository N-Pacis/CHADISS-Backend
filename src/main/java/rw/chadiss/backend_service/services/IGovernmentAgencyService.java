package rw.chadiss.backend_service.services;

import org.springframework.stereotype.Service;
import rw.chadiss.backend_service.dtos.GovernmentAgencySignUpDTO;
import rw.chadiss.backend_service.models.GovernmentAgency;

import java.util.List;

public interface IGovernmentAgencyService {

    List<GovernmentAgency> findAll();

    GovernmentAgency create(GovernmentAgencySignUpDTO dto);
}
