package rw.chadiss.backend_service.services;

import org.springframework.stereotype.Service;
import rw.chadiss.backend_service.dtos.CitizenSignUpDTO;
import rw.chadiss.backend_service.models.Citizen;
import rw.chadiss.backend_service.models.User;

import java.util.List;
import java.util.UUID;

public interface ICitizenService {

    Citizen findById(UUID id);

    Citizen findByUser(User User);
    List<Citizen> findAll();

    Citizen create(CitizenSignUpDTO dto);
}
