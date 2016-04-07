package com.tcc.servidor_tcc.entidades;

import java.util.List;
import java.util.Objects;
import javax.persistence.*;

@Entity
public class ReviewedStudy {
    
    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    private Study study;

    @OneToOne
    private Reviewer reviewer;
    private boolean included;

    @OneToMany
    private List<ReviewedStudyCriteria> reviewedCriteria;

    public long getId() {
        return id;
    }

    public void setId(long Id) {
        this.id = Id;
    }

    public Study getStudy() {
        return study;
    }

    public void setStudy(Study study) {
        this.study = study;
    }

    public Reviewer getReviewer() {
        return reviewer;
    }

    public void setReviewer(Reviewer reviewer) {
        this.reviewer = reviewer;
    }

    public boolean isIncluded() {
        return included;
    }

    public void setIncluded(boolean included) {
        this.included = included;
    }

    public List<ReviewedStudyCriteria> getReviewedCriteria() {
        return reviewedCriteria;
    }

    public void setReviewedCriteria(List<ReviewedStudyCriteria> reviewedCriteria) {
        this.reviewedCriteria = reviewedCriteria;
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
        final ReviewedStudy other = (ReviewedStudy) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.included != other.included) {
            return false;
        }
        if (!Objects.equals(this.study, other.study)) {
            return false;
        }
        if (!Objects.equals(this.reviewer, other.reviewer)) {
            return false;
        }
        if (!Objects.equals(this.reviewedCriteria, other.reviewedCriteria)) {
            return false;
        }
        return true;
    }
    
    
}
