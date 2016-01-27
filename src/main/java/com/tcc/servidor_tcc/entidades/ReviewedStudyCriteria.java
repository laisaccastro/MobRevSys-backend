package com.tcc.servidor_tcc.entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ReviewedStudyCriteria {

    @Id
    @GeneratedValue
    private long id;
    private Criteria criteria;
    private boolean satisfied;
}
