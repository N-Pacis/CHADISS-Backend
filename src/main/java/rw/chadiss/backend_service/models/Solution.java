package rw.chadiss.backend_service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import rw.chadiss.backend_service.dtos.ProposeSolutionAsAgencyDTO;
import rw.chadiss.backend_service.dtos.ProposeSolutionDTO;
import rw.chadiss.backend_service.enums.EIssuedToType;
import rw.chadiss.backend_service.enums.ESolutionType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "solutions")
public class Solution {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column
    private String title;

    @Column
    private String description;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @Column
    @Enumerated(EnumType.STRING)
    private EIssuedToType issuedToType;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SolutionDocuments> documents = new ArrayList<>();

    @Column
    @Enumerated(EnumType.STRING)
    private ESolutionType type;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "proposed_by_id")
    private User proposedBy;


    public Solution(ProposeSolutionDTO dto, Issue issue, ESolutionType solutionType,User user) {
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.issuedToType = dto.getIssuedToType();
        this.issue = issue;
        this.type = solutionType;
        this.proposedBy = user;
    }

    public Solution(ProposeSolutionAsAgencyDTO dto, Issue issue,EIssuedToType type, ESolutionType solutionType,User user) {
        this.title = dto.getTitle();
        this.description = dto.getDescription();
        this.issuedToType = type;
        this.issue = issue;
        this.type = solutionType;
        this.proposedBy = user;
    }


}
