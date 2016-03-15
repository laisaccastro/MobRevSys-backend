package com.tcc.servidor_tcc.dao;

import com.tcc.servidor_tcc.entidades.Reviewer;
import java.util.List;
import java.util.Optional;

public interface ReviewerDAO {
    
    public Optional<Reviewer> getOne(String email);

    public void persist(Reviewer reviewer);
    
    public List<Reviewer> getAll();
}
