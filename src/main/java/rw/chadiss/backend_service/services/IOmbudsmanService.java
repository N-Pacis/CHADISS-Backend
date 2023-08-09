package rw.chadiss.backend_service.services;

import org.springframework.stereotype.Service;
import rw.chadiss.backend_service.dtos.OmbudsmanSignUpDTO;
import rw.chadiss.backend_service.models.Ombudsman;
import rw.chadiss.backend_service.models.User;

import java.util.List;
import java.util.UUID;

public interface IOmbudsmanService {

    List<Ombudsman> getAll();

    Ombudsman findById(UUID id);

    Ombudsman findByUser(User user);

    Ombudsman create(OmbudsmanSignUpDTO dto);
}
