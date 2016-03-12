
package com.tcc.servidor_tcc.dao;

import com.tcc.servidor_tcc.DBUtil.DBConnection;
import com.tcc.servidor_tcc.entidades.Reviewer;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class ReviewerDAOjpa implements ReviewerDAO {
    
    EntityManager em;
    
    public ReviewerDAOjpa(){
        em = DBConnection.getEntityManager();
    }

    @Override
    public Optional<Reviewer> getOne(String email) {
        Query q = em.createQuery("SELECT R FROM Reviewer R where R.email = :email");
        q.setParameter("email", email);
        Optional<Reviewer> rev;
        try{
            rev = Optional.of((Reviewer)q.getSingleResult());
        }catch(Exception e){
            rev = Optional.empty();
        }
        return rev;
    }

    @Override
    public List<Reviewer> getAll() {
        Query q = em.createQuery("SELECT R FROM Reviewer R");
        List<Reviewer> reviewers = q.getResultList();
        return reviewers;
    }
}
