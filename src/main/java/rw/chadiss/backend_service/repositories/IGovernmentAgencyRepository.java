package rw.chadiss.backend_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.chadiss.backend_service.models.GovernmentAgency;

import java.util.UUID;

@Repository
public interface IGovernmentAgencyRepository extends JpaRepository<GovernmentAgency, UUID> {
}
