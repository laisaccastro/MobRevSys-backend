package com.tcc.servidor_tcc.entidades;

import com.tcc.servidor_tcc.type.PaperDivisionType;
import com.tcc.servidor_tcc.type.StageType;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@NamedQueries({
        @NamedQuery(name="SystematicReview.getAll",
                query="SELECT SR FROM SystematicReview SR WHERE SR.owner.email = :email")
//        @NamedQuery(name="SystematicReview.getAll",
//                    query="SELECT SR FROM SystematicReview SR " +
//                            "where SR.owner.email = :email " +
//                            "OR (SELECT COUNT(RR) FROM ReviewerRole RR" +
//                                "where RR.reviewer.email = :email " +
//                                "AND RR.systematicReview.id = SR.id ) > 0")
})

@Entity
public class SystematicReview {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @ManyToOne
    private Reviewer owner;

    private String title;

    private List<String> objectives;

    private List<String> researchQuestions;

    @OneToMany(cascade =CascadeType.ALL)
    private List<Criteria> criteria;

    @OneToMany(mappedBy="sysReview",cascade = CascadeType.ALL)
    private List<ReviewerRole> participatingReviewers;

    @OneToOne(cascade = CascadeType.ALL)
    private BibFile bib;

    @Enumerated(EnumType.STRING)
    private PaperDivisionType divisionType;

    @Enumerated(EnumType.STRING)
    private StageType stage;


    public long getId() {
        return id;
    }

    public void setId(long Id) {
        this.id = Id;
    }

    public Reviewer getOwner() {
        return owner;
    }

    public void setOwner(Reviewer owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getObjectives() {
        return objectives;
    }

    public void setObjectives(List<String> objectives) {
        this.objectives = objectives;
    }

    public List<String> getResearchQuestions() {
        return researchQuestions;
    }

    public void setResearchQuestions(List<String> researchQuestions) {
        this.researchQuestions = researchQuestions;
    }

    public List<Criteria> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<Criteria> criteria) {
        this.criteria = criteria;
    }

    public List<ReviewerRole> getParticipatingReviewers() {
        return participatingReviewers;
    }

    public void setParticipatingReviewers(List<ReviewerRole> participatingReviewers) {
        this.participatingReviewers = participatingReviewers;
    }

    public BibFile getBib() {
        return bib;
    }

    public void setBib(BibFile bib) {
        this.bib = bib;
    }

    public PaperDivisionType getDivisionType() {
        return divisionType;
    }

    public void setDivisionType(PaperDivisionType divisionType) {
        this.divisionType = divisionType;
    }

    public StageType getStage() {
        return stage;
    }

    public void setStage(StageType stage) {
        this.stage = stage;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SystematicReview other = (SystematicReview) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.owner, other.owner)) {
            return false;
        }
        if (!Objects.equals(this.researchQuestions, other.researchQuestions)) {
            return false;
        }
        if (!Objects.equals(this.criteria, other.criteria)) {
            return false;
        }
        if (!Objects.equals(this.participatingReviewers, other.participatingReviewers)) {
            return false;
        }
        if (!Objects.equals(this.bib, other.bib)) {
            return false;
        }
        if (!Objects.equals(this.divisionType, other.divisionType)){
            return false;
        }
        return true;
    }

}
