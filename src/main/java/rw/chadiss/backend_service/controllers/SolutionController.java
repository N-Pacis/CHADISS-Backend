package rw.chadiss.backend_service.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import rw.chadiss.backend_service.dtos.CreateIssueDTO;
import rw.chadiss.backend_service.dtos.ProposeSolutionAsAgencyDTO;
import rw.chadiss.backend_service.dtos.ProposeSolutionDTO;
import rw.chadiss.backend_service.models.Issue;
import rw.chadiss.backend_service.models.Solution;
import rw.chadiss.backend_service.payload.ApiResponse;
import rw.chadiss.backend_service.services.ISolutionService;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/solutions")
@CrossOrigin
public class SolutionController {

    private final ISolutionService solutionService;

    public SolutionController(ISolutionService solutionService) {
        this.solutionService = solutionService;
    }

    @PostMapping(path = "/propose/as-deputy")
    @PreAuthorize("hasAnyAuthority('DEPUTY')")
    public ResponseEntity<ApiResponse> proposeAsDeputy(@Valid @RequestBody ProposeSolutionDTO dto) {
        Solution solution = solutionService.proposeAsDeputy(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, solution));
    }

    @PostMapping(path = "/propose/as-agency")
    @PreAuthorize("hasAnyAuthority('GOVERNMENT_AGENCY')")
    public ResponseEntity<ApiResponse> proposeAsAgency(@Valid @RequestBody ProposeSolutionAsAgencyDTO dto) {
        Solution solution = solutionService.proposeAsAgency(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse(true, solution));
    }

    @PutMapping("/update/{solutionId}/add-document")
    @PreAuthorize("hasAnyAuthority('DEPUTY','GOVERNMENT_AGENCY')")
    public ResponseEntity<?> addDocumentToSolution(@PathVariable("solutionId") UUID solutionId, @RequestParam("document") MultipartFile document) {
        solutionService.addDocumentToSolution(solutionId, document);
        return ResponseEntity.ok(null);
    }
}
