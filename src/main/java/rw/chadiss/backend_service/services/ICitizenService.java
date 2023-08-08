package rw.chadiss.backend_service.services;

import org.springframework.stereotype.Service;
import rw.chadiss.backend_service.dtos.CitizenSignUpDTO;
import rw.chadiss.backend_service.models.Citizen;

import java.util.List;

public interface ICitizenService {

    List<Citizen> findAll();

    Citizen create(CitizenSignUpDTO dto);
}
