package com.tcc.servidor_tcc.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * Created by pantera on 13/03/16.
 */
public abstract class DaoJpa {

    protected static EntityManagerFactory emf=null;

    protected synchronized EntityManager getEntityManager(){
        if(emf == null){
            emf = Persistence.createEntityManagerFactory("tcc_unit");
        }
        EntityManager em = emf.createEntityManager();
        return em;
    }
}
