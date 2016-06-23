package com.tcc.servidor_tcc.dao;

import com.tcc.servidor_tcc.entidades.ReviewerRole;

import java.util.List;

public interface ReviewerRoleDAO {

    public List<ReviewerRole> getAllByEmail(String email);

}
