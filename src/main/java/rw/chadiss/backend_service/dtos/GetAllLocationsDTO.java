package rw.chadiss.backend_service.dtos;

import lombok.Getter;
import lombok.Setter;
import rw.chadiss.backend_service.models.LocationAddress;

@Getter
@Setter
public class GetAllLocationsDTO {

    private LocationAddress province;

    private LocationAddress district;

    private LocationAddress sector;

    private LocationAddress cell;

    private LocationAddress village;
}
