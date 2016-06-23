
package com.tcc.servidor_tcc.dao;

import com.tcc.servidor_tcc.entidades.Reviewer;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

public class ReviewerDAOjpa extends DaoJpa implements ReviewerDAO {

    private EntityManager em;

    public ReviewerDAOjpa(){
        em = getEntityManager();
    }

    @Override
    public Optional<Reviewer> getOne(String email) {
        assert(em!=null);
        Reviewer reviewer = em.find(Reviewer.class,email);
        Optional<Reviewer> rev;
        try{
            rev = Optional.of(reviewer);
        }catch(Exception e){
            rev = Optional.empty();
        }
        return rev;
    }

    @Override
    public List<Reviewer> getAll() {
        Query q = em.createNamedQuery("Reviewer.getAll");
        List<Reviewer> reviewers = q.getResultList();
        return reviewers;
    }

    @Override
    public void persist(Reviewer reviewer) {
        em.getTransaction().begin();
        em.persist(reviewer);
        em.getTransaction().commit();
    }

    @Override
    public void update(Reviewer reviewer) {
        em.getTransaction().begin();
        em.merge(reviewer);
        em.getTransaction().commit();
    }
}
