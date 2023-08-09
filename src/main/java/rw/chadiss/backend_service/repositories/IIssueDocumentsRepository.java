package rw.chadiss.backend_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.chadiss.backend_service.models.Issue;
import rw.chadiss.backend_service.models.IssueDocuments;

import java.util.UUID;

@Repository
public interface IIssueDocumentsRepository extends JpaRepository<IssueDocuments, UUID> {
}
