package com.tcc.servidor_tcc.dao;

import com.tcc.servidor_tcc.DBUtil.DBConnection;
import com.tcc.servidor_tcc.entidades.SystematicReview;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class SystematicReviewDAOjpa implements SystematicReviewDAO {
    
    private EntityManager em;
    
    public SystematicReviewDAOjpa(){
        em = DBConnection.getEntityManager();
    }

    @Override
    public void save(SystematicReview sr) {
        em.getTransaction().begin();
        em.persist(sr);
        em.getTransaction().commit();
    }

    @Override
    public SystematicReview get() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<SystematicReview> getAll(String email) {
        Query q = em.createQuery("SELECT SR FROM SystematicReview SR where SR.owner.email = :email OR (SELECT COUNT(RR) FROM ReviewerRole where RR.reviewer.email = :email AND RR.systematicReview = SR ) > 0");
        q.setParameter("email", email);
        List<SystematicReview> sr = q.getResultList();
        return sr;
    }
    
    
}
