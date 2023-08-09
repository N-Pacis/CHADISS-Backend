package rw.chadiss.backend_service.serviceImpls;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import rw.chadiss.backend_service.dtos.OmbudsmanSignUpDTO;
import rw.chadiss.backend_service.exceptions.ResourceNotFoundException;
import rw.chadiss.backend_service.models.Ombudsman;
import rw.chadiss.backend_service.models.User;
import rw.chadiss.backend_service.repositories.IOmbudsmanRepository;
import rw.chadiss.backend_service.services.IOmbudsmanService;

import java.util.List;
import java.util.UUID;

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
    public Ombudsman findById(UUID id){
        return ombudsmanRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Ombudsman","id",id.toString()));
    }

    @Override
    public Ombudsman findByUser(User user){
        return findById(user.getId());
    }


    @Override
    public Ombudsman create(OmbudsmanSignUpDTO dto) {

        String encodedPassword = bCryptPasswordEncoder.encode(dto.getPassword());

        Ombudsman ombudsman = new Ombudsman(dto,encodedPassword);

        return ombudsmanRepository.save(ombudsman);
    }
}
