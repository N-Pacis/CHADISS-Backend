package rw.chadiss.backend_service.utils;

import org.modelmapper.ModelMapper;
import rw.chadiss.backend_service.models.User;

public class Mapper {

    public static ModelMapper modelMapper = new ModelMapper();

    public static User getUserFromDTO(Object object) {
        return modelMapper.map(object, User.class);
    }


}
