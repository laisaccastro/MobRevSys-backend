package com.tcc.servidor_tcc.entidades;

import com.tcc.servidor_tcc.type.CriteriaType;

import javax.persistence.*;

@Entity
public class Criteria {
     
    @Id
    @GeneratedValue
    private long Id;
    private String description;
    @Enumerated(EnumType.STRING)
    private CriteriaType type;
    
}
