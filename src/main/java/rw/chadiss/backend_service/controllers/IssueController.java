package rw.chadiss.backend_service.controllers;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rw.chadiss.backend_service.dtos.CreateIssueDTO;
import rw.chadiss.backend_service.dtos.GovernmentAgencySignUpDTO;
import rw.chadiss.backend_service.enums.EIssueStatus;
import rw.chadiss.backend_service.enums.ERole;
import rw.chadiss.backend_service.enums.EUserStatus;
import rw.chadiss.backend_service.models.GovernmentAgency;
import rw.chadiss.backend_service.models.Issue;
import rw.chadiss.backend_service.payload.ApiResponse;
import rw.chadiss.backend_service.services.IIssueService;
import rw.chadiss.backend_service.utils.Constants;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/issues")
@CrossOrigin
public class IssueController {

    private final IIssueService issueService;

    public IssueController(IIssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/my-issues")
    @PreAuthorize("hasAnyAuthority('CITIZEN')")
    public ResponseEntity<ApiResponse> searchMyIssues(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "limit", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit,
            @RequestParam(value = "title", required = false, defaultValue = "") String title,
            @RequestParam(value = "status",required = false) EIssueStatus status

    ){
        Pageable pageable = PageRequest.of(page, limit, Sort.Direction.ASC, "id");
        return ResponseEntity.ok(ApiResponse.success(issueService.searchMyIssues(status, title,pageable)));
    }

    @GetMapping("/deputy/assigned-issues")
    @PreAuthorize("hasAnyAuthority('DEPUTY')")
    public ResponseEntity<ApiResponse> searchAssignedIssues(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "limit", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit,
            @RequestParam(value = "title", required = false, defaultValue = "") String title,
            @RequestParam(value = "status",required = false) EIssueStatus status

    ){
        Pageable pageable = PageRequest.of(page, limit, Sort.Direction.ASC, "id");
        return ResponseEntity.ok(ApiResponse.success(issueService.searchAssignedIssues(status, title,pageable)));
    }

    @GetMapping("/government-agency/related-issues")
    @PreAuthorize("hasAnyAuthority('GOVERNMENT_AGENCY')")
    public ResponseEntity<ApiResponse> searchRelatedIssues(
            @RequestParam(value = "page", defaultValue = Constants.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(value = "limit", defaultValue = Constants.DEFAULT_PAGE_SIZE) int limit,
            @RequestParam(value = "title", required = false, defaultValue = "") String title,
            @RequestParam(value = "status",required = false) EIssueStatus status

    ){
        Pageable pageable = PageRequest.of(page, limit, Sort.Direction.ASC, "id");
        return ResponseEntity.ok(ApiResponse.success(issueService.searchRelatedIssues(status, title,pageable)));
    }

    @PostMapping(path = "/create")
    @PreAuthorize("hasAnyAuthority('CITIZEN')")
    public ResponseEntity<ApiResponse> create(@Valid @RequestBody CreateIssueDTO dto) {
        Issue issue = issueService.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, issue));
    }

    @PutMapping("/update/{issueId}/add-document")
    @PreAuthorize("hasAnyAuthority('CITIZEN')")
    public ResponseEntity<?> addDocumentToIssue(@PathVariable("issueId") UUID issueId, @RequestParam("document") MultipartFile document) {
        issueService.addDocumentToIssue(issueId, document);
        return ResponseEntity.ok(null);
    }
}
