package rw.chadiss.backend_service.serviceImpls;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rw.chadiss.backend_service.dtos.CitizenSignUpDTO;
import rw.chadiss.backend_service.models.Citizen;
import rw.chadiss.backend_service.models.LocationAddress;
import rw.chadiss.backend_service.repositories.ICitizenRepository;
import rw.chadiss.backend_service.services.ICitizenService;
import rw.chadiss.backend_service.services.ILocationAddressService;
import rw.chadiss.backend_service.services.IUserService;

import java.util.List;

@Service
public class CitizenServiceImpl implements ICitizenService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final IUserService userService;

    private final ICitizenRepository citizenRepository;

    private final ILocationAddressService locationAddressService;

    public CitizenServiceImpl(BCryptPasswordEncoder bCryptPasswordEncoder, IUserService userService, ICitizenRepository citizenRepository, ILocationAddressService locationAddressService) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.citizenRepository = citizenRepository;
        this.locationAddressService = locationAddressService;
    }


    @Override
    public List<Citizen> findAll() {
        return citizenRepository.findAll();
    }

    @Override
    public Citizen create(CitizenSignUpDTO dto) {

        String encodedPassword = bCryptPasswordEncoder.encode(dto.getPassword());

        LocationAddress locationAddress = locationAddressService.findVillageById(dto.getLocationAddress());
        LocationAddress birthOfPlaceAddress = locationAddressService.findVillageById(dto.getBirthPlaceAddress());

        Citizen citizen = new Citizen(dto,encodedPassword,locationAddress,birthOfPlaceAddress);

        userService.validateNewRegistration(citizen);

        return citizenRepository.save(citizen);
    }
}
