package rw.chadiss.backend_service.serviceImpls;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rw.chadiss.backend_service.dtos.ProposeSolutionAsAgencyDTO;
import rw.chadiss.backend_service.dtos.ProposeSolutionDTO;
import rw.chadiss.backend_service.enums.EIssuedToType;
import rw.chadiss.backend_service.enums.ESolutionType;
import rw.chadiss.backend_service.exceptions.ResourceNotFoundException;
import rw.chadiss.backend_service.models.*;
import rw.chadiss.backend_service.repositories.ISolutionDocumentsRepository;
import rw.chadiss.backend_service.repositories.ISolutionRepository;
import rw.chadiss.backend_service.services.IFileService;
import rw.chadiss.backend_service.services.IIssueService;
import rw.chadiss.backend_service.services.ISolutionService;
import rw.chadiss.backend_service.services.IUserService;

import java.util.UUID;

@Service
public class SolutionServiceImpl implements ISolutionService {

    private final IIssueService issueService;

    private final ISolutionRepository solutionRepository;

    private final IUserService userService;

    private final IFileService fileService;

    private final ISolutionDocumentsRepository solutionDocumentsRepository;

    @Value("${uploads.directory.solutions}")
    private String solutionsDirectory;

    public SolutionServiceImpl(IIssueService issueService, ISolutionRepository solutionRepository, IUserService userService, IFileService fileService, ISolutionDocumentsRepository solutionDocumentsRepository) {
        this.issueService = issueService;
        this.solutionRepository = solutionRepository;
        this.userService = userService;
        this.fileService = fileService;
        this.solutionDocumentsRepository = solutionDocumentsRepository;
    }

    @Override
    public Solution proposeAsDeputy(ProposeSolutionDTO dto) {

        Deputy deputy = userService.getLoggedInProfile().asDeputy();
        Issue issue = issueService.findById(dto.getIssueId());

        Solution solution =  new Solution(dto,issue, ESolutionType.DEPUTY_SOLUTION,deputy);
        return solutionRepository.save(solution);
    }

    @Override
    public Solution proposeAsAgency(ProposeSolutionAsAgencyDTO dto) {

        GovernmentAgency governmentAgency = userService.getLoggedInProfile().asGovernmentAgency();

        Issue issue = issueService.findById(dto.getIssueId());

        Solution solution =  new Solution(dto,issue, EIssuedToType.CITIZEN, ESolutionType.AGENCY_SOLUTION,governmentAgency);
        return solutionRepository.save(solution);
    }

    @Override
    public Solution findById(UUID solutionId){
        return solutionRepository.findById(solutionId).orElseThrow(()->new ResourceNotFoundException("Solution","id",solutionId.toString()));
    }

    @Override
    public void addDocumentToSolution(UUID solutionId, MultipartFile document){
        Solution solution = findById(solutionId);
        File file = fileService.create(document, solutionsDirectory);

        SolutionDocuments solutionDocuments = new SolutionDocuments(file,solution);

        solutionDocumentsRepository.save(solutionDocuments);
    }
}
