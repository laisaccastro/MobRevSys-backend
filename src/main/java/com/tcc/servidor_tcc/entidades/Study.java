package com.tcc.servidor_tcc.entidades;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
class Study {    
    
    @Id
    @GeneratedValue
    private long Id;
    private String title;
    private String studyAbstract;
    
}
