package com.tcc.servidor_tcc.dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pantera on 13/03/16.
 */
public abstract class DaoJpa {

    protected static EntityManagerFactory emf=null;
    private final String DATABASE_URL = "DATABASE_URL";

    protected synchronized EntityManager getEntityManager(){
        if(emf == null){
            Map<String, String> env = System.getenv();
            Map<String, String> configOverrides = new HashMap<>();
            if(env.containsKey(DATABASE_URL)){
                configOverrides.put("javax.persistence.jdbc.url", env.get(DATABASE_URL) + ";create=true");
            }
            emf = Persistence.createEntityManagerFactory("tcc_unit", configOverrides);
        }
        EntityManager em = emf.createEntityManager();
        return em;
    }
}
