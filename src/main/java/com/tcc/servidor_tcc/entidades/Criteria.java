package com.tcc.servidor_tcc.entidades;

import com.tcc.servidor_tcc.Type.CriteriaType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Criteria {
     
    @Id
    @GeneratedValue
    private long Id;
    private String description;
    private CriteriaType type;
    
}
