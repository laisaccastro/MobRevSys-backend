package com.tcc.servidor_tcc.entidades;

import javax.persistence.*;

@Entity
public class ReviewedStudyCriteria {

    @Id
    @GeneratedValue
    private long id;
    @OneToOne(cascade = CascadeType.ALL)
    private Criteria criteria;
    private boolean satisfied;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    public boolean isSatisfied() {
        return satisfied;
    }

    public void setSatisfied(boolean satisfied) {
        this.satisfied = satisfied;
    }
}
