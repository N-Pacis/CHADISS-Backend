package rw.chadiss.backend_service.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "issued_to_id")
    private User issuedTo;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<SolutionDocuments> documents = new ArrayList<>();

    @Column
    @Enumerated(EnumType.STRING)
    private ESolutionType type;
}
