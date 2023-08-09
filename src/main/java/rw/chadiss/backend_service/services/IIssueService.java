package rw.chadiss.backend_service.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import rw.chadiss.backend_service.dtos.CreateIssueDTO;
import rw.chadiss.backend_service.enums.EIssueStatus;
import rw.chadiss.backend_service.models.Issue;

import java.util.UUID;

public interface IIssueService {

    Issue findById(UUID issueId);

    Issue create(CreateIssueDTO dto);

    void addDocumentToIssue(UUID issueId, MultipartFile document);

    Page<Issue> searchMyIssues(EIssueStatus status, String title, Pageable pageable);

    Page<Issue> searchAssignedIssues(EIssueStatus status, String title, Pageable pageable);

    Page<Issue> searchRelatedIssues(EIssueStatus status, String title, Pageable pageable);
}
