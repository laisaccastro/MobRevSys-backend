package com.tcc.servidor_tcc.entidades;

import com.tcc.servidor_tcc.type.PaperDivisionType;
import com.tcc.servidor_tcc.type.RoleType;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@NamedQueries({
        @NamedQuery(name="SystematicReview.getAll",
                    query="SELECT SR FROM SystematicReview SR where SR.owner.email = :email OR (SELECT COUNT(RR) FROM ReviewerRole where RR.reviewer.email = :email AND RR.systematicReview = SR ) > 0")
})

@Entity
public class SystematicReview {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    @ManyToOne
    private Reviewer owner;

    private String title;

    private String objective;

    private List<String> researchQuestions;

    @OneToMany
    private List<Criteria> criteria;

    @OneToMany(mappedBy="sysReview")
    private List<ReviewerRole> participatingReviewers;

    @OneToOne
    private BibFile bib;

    @Enumerated(EnumType.STRING)
    private PaperDivisionType divisionType;

    
    public void parseBib(){
        
    }
    
    private void separateStudies(){
        List<Reviewer> selectionReviewers = participatingReviewers
                .stream()
                .filter( r -> r.getRoles().contains(RoleType.SELECTION))
                .map( r -> r.getReviewer())
                .collect(Collectors.toList());
        
        switch(divisionType){
            case ALL:
                for(Study s:bib.getStudies()){
                    for(Reviewer r: selectionReviewers){
                        ReviewedStudy reviewedStudy = new ReviewedStudy();
                        reviewedStudy.setReviewer(r);
                        reviewedStudy.setStudy(s);
                        s.addReviewedStudy(reviewedStudy);
                    }
                }
                break;
            case SPLIT:
                List<Study> studies = bib.getStudies();
                for(int i=0;i<studies.size();i++){
                    ReviewedStudy reviewedStudy = new ReviewedStudy();
                    reviewedStudy.setReviewer(selectionReviewers.get(i % selectionReviewers.size()));
                    reviewedStudy.setStudy(studies.get(i));
                    studies.get(i).addReviewedStudy(reviewedStudy);
                }
                break;
        }
    }

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
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
        if (this.Id != other.Id) {
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
