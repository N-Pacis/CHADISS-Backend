package rw.chadiss.backend_service.serviceImpls;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rw.chadiss.backend_service.dtos.DeputySignUpDTO;
import rw.chadiss.backend_service.dtos.OmbudsmanSignUpDTO;
import rw.chadiss.backend_service.models.Deputy;
import rw.chadiss.backend_service.models.LocationAddress;
import rw.chadiss.backend_service.models.Ombudsman;
import rw.chadiss.backend_service.repositories.IDeputyRepository;
import rw.chadiss.backend_service.repositories.IOmbudsmanRepository;
import rw.chadiss.backend_service.services.IDeputyService;
import rw.chadiss.backend_service.services.ILocationAddressService;
import rw.chadiss.backend_service.services.IOmbudsmanService;

import java.util.List;

@Service
public class OmbudsmanServiceImpl implements IOmbudsmanService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final IOmbudsmanRepository ombudsmanRepository;

    public OmbudsmanServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, IOmbudsmanRepository ombudsmanRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.ombudsmanRepository = ombudsmanRepository;
    }

    @Override
    public List<Ombudsman> getAll() {
        return ombudsmanRepository.findAll();
    }

    @Override
    public Ombudsman create(OmbudsmanSignUpDTO dto) {

        String encodedPassword = bCryptPasswordEncoder.encode(dto.getPassword());

        Ombudsman ombudsman = new Ombudsman(dto,encodedPassword);

        return ombudsmanRepository.save(ombudsman);
    }
}
