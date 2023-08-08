package rw.chadiss.backend_service.services;

import org.springframework.stereotype.Service;
import rw.chadiss.backend_service.dtos.OmbudsmanSignUpDTO;
import rw.chadiss.backend_service.models.Ombudsman;

import java.util.List;

public interface IOmbudsmanService {

    List<Ombudsman> getAll();

    Ombudsman create(OmbudsmanSignUpDTO dto);
}
