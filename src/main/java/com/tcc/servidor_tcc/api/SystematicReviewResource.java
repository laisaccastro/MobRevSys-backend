/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.servidor_tcc.api;

import com.google.api.client.util.Lists;
import com.tcc.servidor_tcc.api.filter.Authenticate;
import com.tcc.servidor_tcc.dao.*;
import com.tcc.servidor_tcc.entidades.*;
import com.tcc.servidor_tcc.tokenUtil.Token;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.tcc.servidor_tcc.type.IncludeType;
import com.tcc.servidor_tcc.type.PaperDivisionType;
import com.tcc.servidor_tcc.type.RoleType;
import com.tcc.servidor_tcc.type.StageType;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;


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
            for(ReviewerRole rr: sr.getParticipatingReviewers()) {
                Optional<Reviewer> participatingReviewer = reviewerDAO.getOne(rr.getReviewer().getEmail());
                if(participatingReviewer.isPresent()) {
                    rr.setReviewer(participatingReviewer.get());
                }
            }
            PaperDivisionType divisionType = sr.getDivisionType();
            if(divisionType.equals(PaperDivisionType.SPLIT)) {
                List<ReviewerRole> selectionParticipants = sr.getParticipatingReviewers()
                        .stream()
                        .filter(rr -> rr.getRoles().contains(RoleType.SELECTION))
                        .collect(Collectors.toList());
                int participantsCount = selectionParticipants.size() + 1;
                List<List<Study>> studies = com.google.common.collect.Lists
                        .partition(sr.getBib().getStudies(), (int) Math.ceil((double) sr.getBib().getStudies().size()/participantsCount));
                for (int x = 0; x < studies.size(); x++) {
                    if (x == 0) {
                        studies.get(0).stream().forEach(study -> {
                            ReviewedStudy rs = new ReviewedStudy();
                            rs.setStudy(study);
                            rs.setReviewer(sr.getOwner());
                            study.addReviewedStudy(rs);
                        });
                    } else {
                        final int pos = x - 1;
                        studies.get(x).stream().forEach(study -> {
                            ReviewedStudy rs = new ReviewedStudy();
                            rs.setStudy(study);
                            rs.setReviewer(selectionParticipants.get(pos).getReviewer());
                            study.addReviewedStudy(rs);
                        });
                    }
                }
            }else{
                List<Study> studies = sr.getBib().getStudies();
                for (int x = 0; x < sr.getParticipatingReviewers().size(); x++) {
                    if (x == 0) {
                        studies.stream().forEach(study -> {
                            ReviewedStudy rs = new ReviewedStudy();
                            rs.setStudy(study);
                            rs.setReviewer(sr.getOwner());
                            study.addReviewedStudy(rs);
                        });
                    } else {
                        final int pos = x;
                        studies.stream().forEach(study -> {
                            ReviewedStudy rs = new ReviewedStudy();
                            rs.setStudy(study);
                            rs.setReviewer(sr.getParticipatingReviewers().get(pos - 1).getReviewer());
                            study.addReviewedStudy(rs);
                        });
                    }
                }
            }
            SystematicReviewDAO srd = new SystematicReviewDAOjpa();
            srd.update(sr);
        }catch(Exception e){
            e.printStackTrace();
            return Response.serverError().build();
        }
        return Response.ok().build();
    }
    
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllSR(@HeaderParam("Authorization") String jwt){
        String email = Token.getClientEmail(jwt);
        System.out.println(email);
        SystematicReviewDAO srDao = new SystematicReviewDAOjpa();
        List<SystematicReview> sr = srDao.getAll(email);
        System.out.println(sr);
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
                        + sr.getOwner().getName()+ " has invited you to participate in a "
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

    @Path("/update")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSR(SystematicReview sr, @HeaderParam("Authorization") String jwt) {
        try {
            SystematicReviewDAO srDAO = new SystematicReviewDAOjpa();
            sr.getParticipatingReviewers().stream().map(pr -> pr.getReviewer().getEmail()).forEach(System.out::println);
            boolean isInitialSelectionFinished = sr.getBib()
                    .getStudies()
                    .parallelStream()
                    .allMatch((s) -> s.getReviewedStudies()
                            .stream()
                            .allMatch(rs -> rs.getIncludedInitialSelection() != null));
            boolean isFinalSelectionFinished = false;
            if(isInitialSelectionFinished) {
                isFinalSelectionFinished = sr.getBib()
                        .getStudies()
                        .parallelStream()
                        .allMatch((s) -> s.getReviewedStudies()
                                .stream()
                                .filter(rs -> rs.getIncludedInitialSelection() == IncludeType.INCLUDED)
                                .allMatch(rs -> rs.getIncludedFinalSelection() != null));
            }
            System.out.println("Final Selection: " + isFinalSelectionFinished);
            if(isFinalSelectionFinished) {
                sr.setStage(StageType.FINAL_REVIEW);
            } else if (isInitialSelectionFinished){
                sr.setStage(StageType.FINAL_SELECTION);
            }
//            SystematicReview existingSR = srDAO.get(sr.getId());
            srDAO.update(sr);
        }catch(Exception e){
            e.printStackTrace();
            return Response.serverError().build();
        }
        return Response.ok().build();
    }

}
