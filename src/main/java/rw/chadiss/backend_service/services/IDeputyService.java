package rw.chadiss.backend_service.services;

import org.springframework.stereotype.Service;
import rw.chadiss.backend_service.dtos.DeputySignUpDTO;
import rw.chadiss.backend_service.models.Deputy;

import java.util.List;

public interface IDeputyService {

    List<Deputy> findAll();

    Deputy create(DeputySignUpDTO dto);
}
