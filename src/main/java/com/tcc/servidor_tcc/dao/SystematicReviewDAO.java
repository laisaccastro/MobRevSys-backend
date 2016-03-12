package com.tcc.servidor_tcc.dao;

import com.tcc.servidor_tcc.entidades.SystematicReview;
import java.util.List;

public interface SystematicReviewDAO {
    
    public void save(SystematicReview sr);
    
    public SystematicReview get();
    
    public List<SystematicReview> getAll(String email);
    
}
