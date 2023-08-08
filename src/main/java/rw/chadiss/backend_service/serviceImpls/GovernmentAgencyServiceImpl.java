package rw.chadiss.backend_service.serviceImpls;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rw.chadiss.backend_service.dtos.GovernmentAgencySignUpDTO;
import rw.chadiss.backend_service.models.Deputy;
import rw.chadiss.backend_service.models.GovernmentAgency;
import rw.chadiss.backend_service.models.LocationAddress;
import rw.chadiss.backend_service.repositories.IGovernmentAgencyRepository;
import rw.chadiss.backend_service.services.IGovernmentAgencyService;
import rw.chadiss.backend_service.services.ILocationAddressService;

import java.util.List;

@Service
public class GovernmentAgencyServiceImpl implements IGovernmentAgencyService {
    private final IGovernmentAgencyRepository repository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ILocationAddressService locationAddressService;

    public GovernmentAgencyServiceImpl(IGovernmentAgencyRepository repository, BCryptPasswordEncoder bCryptPasswordEncoder, ILocationAddressService locationAddressService) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.locationAddressService = locationAddressService;
    }

    @Override
    public List<GovernmentAgency> findAll() {
        return repository.findAll();
    }

    @Override
    public GovernmentAgency create(GovernmentAgencySignUpDTO dto) {
        String encodedPassword = bCryptPasswordEncoder.encode(dto.getPassword());

        LocationAddress locationAddress = locationAddressService.findVillageById(dto.getLocationAddress());

        GovernmentAgency governmentAgency = new GovernmentAgency(dto,encodedPassword,locationAddress);

        return repository.save(governmentAgency);
    }
}
