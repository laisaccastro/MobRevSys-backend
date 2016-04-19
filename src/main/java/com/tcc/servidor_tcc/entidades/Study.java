package com.tcc.servidor_tcc.entidades;
import com.tcc.servidor_tcc.type.IncludeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

@Entity
public class Study {
    
    @Id
    @GeneratedValue
    private long id;
    private List<String> authors;
    private String title;
    private IncludeType includedInitialReview = IncludeType.EXCLUDED;
    private IncludeType includedFinalReview = IncludeType.EXCLUDED;
    @Lob
    private String studyAbstract;
    @OneToMany(mappedBy = "study", cascade = CascadeType.ALL)
    private List<ReviewedStudy> reviewedStudies;

    public long getId() {
        return id;
    }


    public List<ReviewedStudy> getReviewedStudies() {
        return reviewedStudies;
    }

    public void setReviewedStudy(List<ReviewedStudy> reviewedStudies) {
        this.reviewedStudies = reviewedStudies;
    }
    
    public void addReviewedStudy(ReviewedStudy reviewedStudy){
        if(reviewedStudies==null){
            reviewedStudies= new ArrayList<>();
        }
        reviewedStudies.add(reviewedStudy);
    }

    public void setId(long Id) {
        this.id = Id;
    }

    public List<String> getAuthors() {
        return authors;
    }

    public void setAuthors(List<String> authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStudyAbstract() {
        return studyAbstract;
    }

    public void setStudyAbstract(String studyAbstract) {
        this.studyAbstract = studyAbstract;
    }


    public IncludeType getIncludedInitialReview() {
        return includedInitialReview;
    }

    public void setIncludedInitialReview(IncludeType includedInitialReview) {
        this.includedInitialReview = includedInitialReview;
    }

    public IncludeType getIncludedFinalReview() {
        return includedFinalReview;
    }

    public void setIncludedFinalReview(IncludeType includedFinalReview) {
        this.includedFinalReview = includedFinalReview;
    }

    public void setReviewedStudies(List<ReviewedStudy> reviewedStudies) {
        this.reviewedStudies = reviewedStudies;
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
        final Study other = (Study) obj;
        if (this.id != other.id) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        if (!Objects.equals(this.studyAbstract, other.studyAbstract)) {
            return false;
        }
        if (!Objects.equals(this.authors, other.authors)) {
            return false;
        }
        if (!Objects.equals(this.reviewedStudies, other.reviewedStudies)) {
            return false;
        }
        return true;
    }
    
    
    
    
}
