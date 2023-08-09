package rw.chadiss.backend_service.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.chadiss.backend_service.audits.InitiatorAudit;
import rw.chadiss.backend_service.dtos.CreateIssueDTO;
import rw.chadiss.backend_service.enums.EIssueStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "issues")
public class Issue extends InitiatorAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column
    private String title;

    @Column
    private String description;

    @Column
    private String institutionName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column
    private LocalDate startDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "issued_by_id")
    private Citizen issuedBy;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "assigned_deputy_id")
    private Deputy assignedDeputy;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "government_agency_id")
    private GovernmentAgency governmentAgency;

    @Column
    @Enumerated(EnumType.STRING)
    private EIssueStatus status = EIssueStatus.ACTIVE;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<IssueDocuments> documents = new ArrayList<>();

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Column
    private LocalDate endDate;

    public Issue(CreateIssueDTO dto, Citizen citizen){
        this.issuedBy = citizen;
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.institutionName = dto.getInstitutionName();
        this.startDate = dto.getStartDate();
    }

}
