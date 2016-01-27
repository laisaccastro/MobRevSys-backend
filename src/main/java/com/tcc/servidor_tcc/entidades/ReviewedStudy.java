package com.tcc.servidor_tcc.entidades;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ReviewedStudy {
    
    @Id
    @GeneratedValue
    private long Id;
    private Study study;
    private Reviewer reviewer;
    private boolean included;
    private List<ReviewedStudyCriteria> reviewedCriteria;
}
