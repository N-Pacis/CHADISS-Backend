package rw.chadiss.backend_service.serviceImpls;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import rw.chadiss.backend_service.dtos.CreateIssueDTO;
import rw.chadiss.backend_service.enums.EIssueStatus;
import rw.chadiss.backend_service.enums.ELocationType;
import rw.chadiss.backend_service.exceptions.ResourceNotFoundException;
import rw.chadiss.backend_service.models.*;
import rw.chadiss.backend_service.repositories.IIssueDocumentsRepository;
import rw.chadiss.backend_service.repositories.IIssueRepository;
import rw.chadiss.backend_service.services.*;

import java.util.List;
import java.util.UUID;

@Service
public class IssueServiceImpl implements IIssueService {

    private final IUserService userService;

    private final IIssueRepository issueRepository;

    private final IFileService fileService;

    private final IIssueDocumentsRepository issueDocumentsRepository;

    private final IDeputyService deputyService;

    private final IGovernmentAgencyService governmentAgencyService;

    @Value("${uploads.directory.issues}")
    private String issuesDirectory;

    public IssueServiceImpl(IUserService userService, IIssueRepository issueRepository, IFileService fileService, IIssueDocumentsRepository issueDocumentsRepository, IDeputyService deputyService, IGovernmentAgencyService governmentAgencyService) {
        this.userService = userService;
        this.issueRepository = issueRepository;
        this.fileService = fileService;
        this.issueDocumentsRepository = issueDocumentsRepository;
        this.deputyService = deputyService;
        this.governmentAgencyService = governmentAgencyService;
    }

    @Override
    public Issue findById(UUID issueId){
        return issueRepository.findById(issueId).orElseThrow(()->new ResourceNotFoundException("Issue","id",issueId.toString()));
    }

    @Override
    public Issue create(CreateIssueDTO dto) {
        Citizen citizen = userService.getLoggedInProfile().asCitizen();

        Issue issue = new Issue(dto,citizen);
        issue = issueRepository.save(issue);
        assignIssueToDeputy(citizen,issue);
        return issue;
    }

    public void assignIssueToDeputy(Citizen citizen, Issue issue) {
        LocationAddress citizenLocation = citizen.getLocationAddress();
        LocationAddress citizenDistrict = getDistrictOrProvince(citizenLocation);

        Deputy suitableDeputy = findSuitableDeputy(citizenDistrict);

        if (suitableDeputy != null) {
            issue.setAssignedDeputy(suitableDeputy);
            issueRepository.save(issue);
        }
    }

    private LocationAddress getDistrictOrProvince(LocationAddress location) {
        LocationAddress parent = location.getParentId();
        while (parent != null && parent.getLocationType() != ELocationType.DISTRICT
                && parent.getLocationType() != ELocationType.PROVINCE) {
            parent = parent.getParentId();
        }
        return parent;
    }

    private Deputy findSuitableDeputy(LocationAddress district) {
        List<Deputy> deputies = deputyService.findAll();

        // Find a deputy from the same district with fewer than 5 active issues
        Deputy suitableDeputy = deputies.stream()
                .filter(d -> isSameDistrictOrProvince(d.getLocationAddress(), district))
                .filter(d -> countActiveIssues(d) < 5)
                .findFirst()
                .orElse(null);

        // If no suitable deputy found, find a deputy from the same province or country
        if (suitableDeputy == null) {
            suitableDeputy = deputies.stream()
                    .filter(d -> isSameProvinceOrCountry(d.getLocationAddress(), district))
                    .filter(d -> countActiveIssues(d) < 5)
                    .findFirst()
                    .orElse(null);
        }

        return suitableDeputy;
    }

    private boolean isSameDistrictOrProvince(LocationAddress location, LocationAddress target) {
        return isSameLocationHierarchy(location, target, ELocationType.DISTRICT)
                || isSameLocationHierarchy(location, target, ELocationType.PROVINCE);
    }

    private boolean isSameProvinceOrCountry(LocationAddress location, LocationAddress target) {
        return isSameLocationHierarchy(location, target, ELocationType.PROVINCE)
                || isSameLocationHierarchy(location, target, ELocationType.COUNTRY);
    }

    private boolean isSameLocationHierarchy(LocationAddress location, LocationAddress target, ELocationType type) {
        LocationAddress districtOrProvince = getDistrictOrProvince(location);
        LocationAddress targetDistrictOrProvince = getDistrictOrProvince(target);

        return districtOrProvince != null && districtOrProvince.equals(targetDistrictOrProvince)
                && districtOrProvince.getLocationType() == type;
    }

    private int countActiveIssues(Deputy deputy) {
        return issueRepository.findByAssignedDeputyAndStatus(deputy, EIssueStatus.ACTIVE).size();
    }


    @Override
    public void addDocumentToIssue(UUID issueId, MultipartFile document){
        Issue issue = findById(issueId);
        File file = fileService.create(document, issuesDirectory);

        IssueDocuments issueDocuments = new IssueDocuments(file,issue);

        issueDocumentsRepository.save(issueDocuments);
    }

    @Override
    public Page<Issue> searchMyIssues(EIssueStatus status, String title, Pageable pageable) {
        Citizen citizen = userService.getLoggedInProfile().asCitizen();

        return issueRepository.searchMyIssues(status,title,citizen,pageable);
    }

    @Override
    public Page<Issue> searchAssignedIssues(EIssueStatus status, String title, Pageable pageable) {
        Deputy deputy = userService.getLoggedInProfile().asDeputy();
        return issueRepository.searchAssignedIssues(status,title,deputy,pageable);
    }

    @Override
    public Page<Issue> searchRelatedIssues(EIssueStatus status, String title, Pageable pageable) {
        GovernmentAgency governmentAgency = userService.getLoggedInProfile().asGovernmentAgency();
        return issueRepository.searchRelatedIssues(status,title, governmentAgency, pageable);
    }

    public void assignGovernmentAgencyToIssue(UUID issueId, UUID governmentAgencyId){
        Issue issue = findById(issueId);
        GovernmentAgency governmentAgency = governmentAgencyService.findById(governmentAgencyId);

        issue.setGovernmentAgency(governmentAgency);

        issueRepository.save(issue);
    }
}
