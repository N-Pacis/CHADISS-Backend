package rw.chadiss.backend_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.chadiss.backend_service.models.Deputy;

import java.util.UUID;

@Repository
public interface IDeputyRepository extends JpaRepository<Deputy, UUID> {
}
