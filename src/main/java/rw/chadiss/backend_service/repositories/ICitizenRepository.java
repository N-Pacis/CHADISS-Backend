package rw.chadiss.backend_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.chadiss.backend_service.models.Citizen;

import java.util.UUID;

@Repository
public interface ICitizenRepository extends JpaRepository<Citizen, UUID> {

}
