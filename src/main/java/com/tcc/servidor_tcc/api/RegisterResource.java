
package com.tcc.servidor_tcc.api;

import com.tcc.servidor_tcc.DBUtil.DBConnection;
import com.tcc.servidor_tcc.entidades.Reviewer;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/register")
public class RegisterResource {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerReviewer(Reviewer reviewer){
        EntityManager em = DBConnection.getEntityManager();
        Query q = em.createQuery("SELECT R FROM Reviewer AS R WHERE R.email = :email");
        q.setParameter("email",reviewer.getEmail());
        List<Reviewer> reviewers = q.getResultList();
        if(!reviewers.isEmpty()){
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }else{
            em.getTransaction().begin();
            em.persist(reviewer);
            em.getTransaction().commit();
            return Response.ok().build();
        }
    }
    
}
