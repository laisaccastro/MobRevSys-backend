package com.tcc.servidor_tcc.entidades;

import com.tcc.servidor_tcc.type.PaperDivisionType;

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

    private String objective;

    private List<String> researchQuestions;

    private List<Criteria> criteria;

    @OneToMany(mappedBy="sysReview")
    private List<ReviewerRole> participatingReviewers;

    @OneToOne(cascade = CascadeType.PERSIST)
    private BibFile bib;

    @Enumerated(EnumType.STRING)
    private PaperDivisionType divisionType;


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

    public String getObjective() {
        return objective;
    }

    public void setObjective(String objective) {
        this.objective = objective;
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
        if (!Objects.equals(this.objective, other.objective)) {
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
