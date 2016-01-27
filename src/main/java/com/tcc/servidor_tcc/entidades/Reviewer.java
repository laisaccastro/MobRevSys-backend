package com.tcc.servidor_tcc.entidades;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Reviewer {
    
    @Id
    @GeneratedValue
    private long Id;
    private String name;
    private String email;
    private String password;
    private String affiliatedUniversity;
    private String country;
    private List<SystematicReview> reviews;
}
