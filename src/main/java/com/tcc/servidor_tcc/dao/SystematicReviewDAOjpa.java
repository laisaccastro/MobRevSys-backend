package com.tcc.servidor_tcc.dao;

import com.tcc.servidor_tcc.entidades.SystematicReview;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class SystematicReviewDAOjpa extends DaoJpa implements SystematicReviewDAO {

    private EntityManager em;

    public SystematicReviewDAOjpa(){
        em = getEntityManager();
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
        Query q = em.createNamedQuery("SystematicReview.getAll");
        q.setParameter("email", email);
        List<SystematicReview> sr = q.getResultList();
        return sr;
    }
    
    
}
