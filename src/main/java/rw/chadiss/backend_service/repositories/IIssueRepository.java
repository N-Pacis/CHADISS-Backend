package rw.chadiss.backend_service.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.chadiss.backend_service.enums.EIssueStatus;
import rw.chadiss.backend_service.models.Citizen;
import rw.chadiss.backend_service.models.Deputy;
import rw.chadiss.backend_service.models.GovernmentAgency;
import rw.chadiss.backend_service.models.Issue;

import java.util.List;
import java.util.UUID;

@Repository
public interface IIssueRepository extends JpaRepository<Issue, UUID> {

    List<Issue> findByAssignedDeputyAndStatus(Deputy deputy, EIssueStatus status);

    @Query("SELECT i FROM Issue i WHERE (:status IS NULL OR i.status = :status) AND (i.issuedBy = :citizen)  AND LOWER(i.title) LIKE CONCAT('%', LOWER(:title), '%')")
    Page<Issue> searchMyIssues(EIssueStatus status, String title, Citizen citizen, Pageable pageable);

    @Query("SELECT i FROM Issue i WHERE (:status IS NULL OR i.status = :status) AND (i.assignedDeputy = :deputy)  AND LOWER(i.title) LIKE CONCAT('%', LOWER(:title), '%')")
    Page<Issue> searchAssignedIssues(EIssueStatus status, String title, Deputy deputy, Pageable pageable);

    @Query("SELECT i FROM Issue i WHERE (:status IS NULL OR i.status = :status) AND (i.governmentAgency = :governmentAgency)  AND LOWER(i.title) LIKE CONCAT('%', LOWER(:title), '%')")
    Page<Issue> searchRelatedIssues(EIssueStatus status, String title, GovernmentAgency governmentAgency, Pageable pageable);
}
