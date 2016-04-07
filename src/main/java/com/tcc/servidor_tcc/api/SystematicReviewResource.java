/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.servidor_tcc.api;

import com.tcc.servidor_tcc.api.filter.Authenticate;
import com.tcc.servidor_tcc.dao.ReviewerDAO;
import com.tcc.servidor_tcc.dao.ReviewerDAOjpa;
import com.tcc.servidor_tcc.dao.SystematicReviewDAO;
import com.tcc.servidor_tcc.dao.SystematicReviewDAOjpa;
import com.tcc.servidor_tcc.entidades.Reviewer;
import com.tcc.servidor_tcc.entidades.ReviewerRole;
import com.tcc.servidor_tcc.entidades.SystematicReview;
import com.tcc.servidor_tcc.tokenUtil.Token;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;
import org.jbibtex.*;

@Path("/systematicreview")
@Authenticate
public class SystematicReviewResource {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSR(SystematicReview sr, @HeaderParam("Authorization") String jwt) {
        try {
            String email = Token.getClientEmail(jwt);
            ReviewerDAO reviewerDAO = new ReviewerDAOjpa();
            Optional<Reviewer> rev = reviewerDAO.getOne(email);
            if (rev.isPresent()) {
                sr.setOwner(rev.get());
            } else {
                throw new RuntimeException("Trying to set as owner a reviewer that does not exist");
            }
            inviteReviewers(reviewerDAO, sr);
            SystematicReviewDAO srd = new SystematicReviewDAOjpa();
            srd.save(sr);
        }catch(Exception e){
            e.printStackTrace();
        }
        return Response.ok().build();
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSR(@HeaderParam("Authorization") String jwt){
        String email = Token.getClientEmail(jwt);
        SystematicReviewDAO srd = new SystematicReviewDAOjpa();
        List<SystematicReview> sr = srd.getAll(email);
        return Response.ok().entity(sr).build();

    }
    
    private void inviteReviewers(ReviewerDAO dao, SystematicReview sr){
        List<Reviewer> existing = dao.getAll();
        for(ReviewerRole reviewerRole: sr.getParticipatingReviewers()){
            Reviewer reviewer = reviewerRole.getReviewer();
            if(existing.contains(reviewer)){
                sendEmailNotification(reviewer, true, sr);
            }else{
                sendEmailNotification(reviewer, false, sr);
            }
        }
    }

    private void sendEmailNotification(Reviewer reviewer, boolean registered, SystematicReview sr) {
        SimpleEmail email = new SimpleEmail();
        email.setSSLOnConnect(true);
        email.setHostName("smtp.gmail.com");
        email.setSslSmtpPort("465");
        email.setAuthenticator(new DefaultAuthenticator("mobrevsys@gmail.com", "revisaosistematica"));
        try {
            email.setFrom("mobrevsys@gmail.com");

            email.setDebug(true);

            email.setSubject("MobRevSys - Invited to participate in a Systematic Review");
            if(registered){
                email.setMsg("Hello "+ reviewer.getName()+",\n"
                        + sr.getOwner().getName()+ "has invited you to participate in a "
                        + "Systematic Review with the title of \""+sr.getTitle()+"\".\n"
                + "Best Regards,\n MobRevSys");
            }else{
                email.setMsg("Hello "+ reviewer.getEmail()+",\n"
                        + sr.getOwner().getName()+ "has invited you to participate in a "
                        + "Systematic Review with the title of \""+sr.getTitle()+"\".\n"
                        + "Please download the MobRevSys app and register to contribute.\n"
                + "Best Regards,\n MobRevSys");
            }
            email.addTo(reviewer.getEmail());

            email.send();

        } catch (EmailException e) {
            e.printStackTrace();
        }
    }
}
