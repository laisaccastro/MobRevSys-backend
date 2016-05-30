
package com.tcc.servidor_tcc.api;

import com.tcc.servidor_tcc.dao.ReviewerDAO;
import com.tcc.servidor_tcc.dao.ReviewerDAOjpa;
import com.tcc.servidor_tcc.entidades.BibFile;
import com.tcc.servidor_tcc.entidades.Reviewer;
import com.tcc.servidor_tcc.entidades.Study;
import com.tcc.servidor_tcc.tokenUtil.Token;
import org.jbibtex.*;

import java.io.*;
import java.util.*;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/register")
public class RegisterResource {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerReviewer(Reviewer reviewer){
        ReviewerDAO dao = new ReviewerDAOjpa();
        Optional<Reviewer> rev = dao.getOne(reviewer.getEmail());

        if(!rev.isPresent()){
            dao.persist(reviewer);
            String result = Token.createClientToken(reviewer.getEmail());
            return Response.ok().entity(result).build();

        }else{
            Reviewer r = rev.get();
            if(r.getPassword()==null){
                r.setPassword(reviewer.getPassword());
                r.setAffiliatedUniversity(reviewer.getAffiliatedUniversity());
                r.setCountry(reviewer.getCountry());
                r.setName(reviewer.getName());
                dao.persist(r);
                String result = Token.createClientToken(reviewer.getEmail());
                return Response.ok().entity(result).build();
            }
            return Response.status(Response.Status.NOT_ACCEPTABLE).build();
        }
    }
    
}
