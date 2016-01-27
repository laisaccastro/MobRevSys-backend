package com.tcc.servidor_tcc.entidades;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SystematicReview {
    
    @Id
    @GeneratedValue
    private long Id;
    private Reviewer owner;
    private String title;
    private String objective;
    private List<String> researchQuestions;
    private List<Criteria> criteria;
    private List<Reviewer> participatingReviewers;  
    private BibFile bib;
    
}
