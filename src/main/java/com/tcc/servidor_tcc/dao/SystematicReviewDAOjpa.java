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
    public SystematicReview get(long id) {
        return em.find(SystematicReview.class,id);
    }

    @Override
    public List<SystematicReview> getAll(String email) {
        Query q = em.createNamedQuery("SystematicReview.getAll");
        q.setParameter("email", email);
        List<SystematicReview> sr = q.getResultList();
        return sr;
    }

    @Override
    public void update(SystematicReview sr) {
        em.getTransaction().begin();
        em.merge(sr);
        em.getTransaction().commit();
    }
}
