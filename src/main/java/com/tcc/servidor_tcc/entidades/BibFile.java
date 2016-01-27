package com.tcc.servidor_tcc.entidades;

import java.net.URL;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class BibFile {
    
    @Id
    @GeneratedValue
    private long Id;
    private String name;
    private URL url;
    private List<Study> studies;
}
