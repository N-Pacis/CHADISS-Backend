package rw.chadiss.backend_service.services;

import org.springframework.web.multipart.MultipartFile;
import rw.chadiss.backend_service.dtos.ProposeSolutionAsAgencyDTO;
import rw.chadiss.backend_service.dtos.ProposeSolutionDTO;
import rw.chadiss.backend_service.models.Solution;

import java.util.UUID;

public interface ISolutionService {

    Solution proposeAsDeputy(ProposeSolutionDTO dto);

    Solution proposeAsAgency(ProposeSolutionAsAgencyDTO dto);

    Solution findById(UUID solutionId);

    void addDocumentToSolution(UUID solutionId, MultipartFile document);
}
