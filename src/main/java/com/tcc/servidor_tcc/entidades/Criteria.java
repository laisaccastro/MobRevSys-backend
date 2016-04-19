package com.tcc.servidor_tcc.entidades;

import com.tcc.servidor_tcc.type.CriteriaType;

import javax.persistence.*;

@Entity
public class Criteria {
     
    @Id
    @GeneratedValue
    private long id;
    private String description;
    @Enumerated(EnumType.STRING)
    private CriteriaType type;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CriteriaType getType() {
        return type;
    }

    public void setType(CriteriaType type) {
        this.type = type;
    }
}
