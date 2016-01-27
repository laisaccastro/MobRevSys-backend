package com.tcc.servidor_tcc.DBUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DBConnection {
    
    private static EntityManager em=null;
    
    public static EntityManager getEntityManager(){
        if(em==null){
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("tcc_unit");
            em = emf.createEntityManager();
        }
        return em;
    } 
}
