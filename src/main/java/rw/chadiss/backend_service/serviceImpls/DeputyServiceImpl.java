package rw.chadiss.backend_service.serviceImpls;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rw.chadiss.backend_service.dtos.DeputySignUpDTO;
import rw.chadiss.backend_service.exceptions.ResourceNotFoundException;
import rw.chadiss.backend_service.models.Deputy;
import rw.chadiss.backend_service.models.LocationAddress;
import rw.chadiss.backend_service.models.User;
import rw.chadiss.backend_service.repositories.IDeputyRepository;
import rw.chadiss.backend_service.services.IDeputyService;
import rw.chadiss.backend_service.services.ILocationAddressService;

import java.util.List;
import java.util.UUID;

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
    public Deputy findById(UUID id){
        return deputyRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Deputy","id",id.toString()));
    }

    @Override
    public Deputy findByUser(User user){
        return findById(user.getId());
    }

    @Override
    public Deputy create(DeputySignUpDTO dto) {

        String encodedPassword = bCryptPasswordEncoder.encode(dto.getPassword());

        LocationAddress locationAddress = locationAddressService.findVillageById(dto.getLocationAddress());

        Deputy deputy = new Deputy(dto,encodedPassword,locationAddress);

        return deputyRepository.save(deputy);
    }
}
