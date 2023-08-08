package rw.chadiss.backend_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.chadiss.backend_service.models.Ombudsman;

import java.util.UUID;

@Repository
public interface IOmbudsmanRepository extends JpaRepository<Ombudsman, UUID> {
}
