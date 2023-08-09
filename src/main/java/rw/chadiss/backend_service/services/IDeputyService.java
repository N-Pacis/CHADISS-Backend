package rw.chadiss.backend_service.services;

import org.springframework.stereotype.Service;
import rw.chadiss.backend_service.dtos.DeputySignUpDTO;
import rw.chadiss.backend_service.models.Deputy;
import rw.chadiss.backend_service.models.User;

import java.util.List;
import java.util.UUID;

public interface IDeputyService {

    List<Deputy> findAll();

    Deputy findById(UUID id);

    Deputy findByUser(User user);

    Deputy create(DeputySignUpDTO dto);
}
