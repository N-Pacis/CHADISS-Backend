package rw.chadiss.backend_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.chadiss.backend_service.models.SolutionDocuments;

import java.util.UUID;

@Repository
public interface ISolutionDocumentsRepository extends JpaRepository<SolutionDocuments, UUID> {
}
