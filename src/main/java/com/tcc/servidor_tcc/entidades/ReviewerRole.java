package com.tcc.servidor_tcc.entidades;

import com.tcc.servidor_tcc.Type.RoleType;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ReviewerRole {
    
    @Id
    @GeneratedValue
    public long Id;
    private SystematicReview systematicReview;
    private Reviewer reviewer;
    private List<RoleType> roles;
    
}
