package com.tcc.servidor_tcc.dao;

import com.tcc.servidor_tcc.entidades.ReviewerRole;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ReviewerRoleDAOjpa extends DaoJpa implements ReviewerRoleDAO {

    private EntityManager em;

    public ReviewerRoleDAOjpa(){
        em = getEntityManager();

    }

    @Override
    public List<ReviewerRole> getAllByEmail(String email) {
        Query q = em.createNamedQuery("ReviewerRole.getAllByEmail");
        q.setParameter("email", email);
        List<ReviewerRole> rr = q.getResultList();
        return rr;
    }
}
