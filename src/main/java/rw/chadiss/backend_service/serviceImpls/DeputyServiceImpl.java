package rw.chadiss.backend_service.serviceImpls;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rw.chadiss.backend_service.dtos.DeputySignUpDTO;
import rw.chadiss.backend_service.models.Deputy;
import rw.chadiss.backend_service.models.LocationAddress;
import rw.chadiss.backend_service.repositories.IDeputyRepository;
import rw.chadiss.backend_service.services.IDeputyService;
import rw.chadiss.backend_service.services.ILocationAddressService;

import java.util.List;

@Service
public class DeputyServiceImpl implements IDeputyService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ILocationAddressService locationAddressService;

    private final IDeputyRepository deputyRepository;

    public DeputyServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, ILocationAddressService locationAddressService, IDeputyRepository deputyRepository) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.locationAddressService = locationAddressService;
        this.deputyRepository = deputyRepository;
    }

    @Override
    public List<Deputy> findAll() {
        return deputyRepository.findAll();
    }

    @Override
    public Deputy create(DeputySignUpDTO dto) {

        String encodedPassword = bCryptPasswordEncoder.encode(dto.getPassword());

        LocationAddress locationAddress = locationAddressService.findVillageById(dto.getLocationAddress());

        Deputy deputy = new Deputy(dto,encodedPassword,locationAddress);

        return deputyRepository.save(deputy);
    }
}
