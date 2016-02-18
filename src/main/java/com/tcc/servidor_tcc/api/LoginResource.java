/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tcc.servidor_tcc.api;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.tcc.servidor_tcc.DBUtil.DBConnection;
import com.tcc.servidor_tcc.entidades.Reviewer;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.crypto.MacProvider;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/login")
public class LoginResource {

    private static final String CLIENT_ID = "37163664732-b2eus3fbmke97v04mn9se394v2274njk.apps.googleusercontent.com";
    private static final NetHttpTransport transport = new NetHttpTransport();
    private static final JsonFactory jsonFactory = new GsonFactory();

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public String login(@FormParam("email") String email,@FormParam("password") String password) {
        EntityManager em = DBConnection.getEntityManager();
        Query q = em.createQuery("SELECT R FROM Reviewer R where R.email = :email");
        q.setParameter("email", email);
        List<Reviewer> reviewers = q.getResultList();
        if(reviewers.size()==1){
            Reviewer r = reviewers.get(0);
            if(r.getPassword().equals(password)){
                return "sucesso";
            }else{
                return "senha incorreta";
            }
        }
        return "email nao cadastrado";
    }

    @Path("/token")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginToken(@FormParam("tokenId") String tokenIdString) {
        System.out.println("Token:" + tokenIdString);

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Arrays.asList(CLIENT_ID))
                // If you retrieved the token on Android using the Play Services 8.3 API or newer, set
                // the issuer to "https://accounts.google.com". Otherwise, set the issuer to 
                // "accounts.google.com". If you need to verify tokens from multiple sources, build
                // a GoogleIdTokenVerifier for each issuer and try them both.
                .setIssuer("https://accounts.google.com")
                .build();

        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(tokenIdString);
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(LoginResource.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LoginResource.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (idToken != null) {
            Payload payload = idToken.getPayload();

            // Print user identifier
            String userId = payload.getSubject();
            System.out.println("User ID: " + userId);

            // Get profile information from payload
            String email = payload.getEmail();
            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            String name = (String) payload.get("name");
            String pictureUrl = (String) payload.get("picture");
            String locale = (String) payload.get("locale");
            String familyName = (String) payload.get("family_name");
            String givenName = (String) payload.get("given_name");

            EntityManager em = DBConnection.getEntityManager();
            Query q = em.createQuery("SELECT R FROM Reviewer R where R.email = :email");
            q.setParameter("email", email);
            List<Reviewer> reviewers = q.getResultList();
            if(reviewers.size()==1){
                Config conf = ConfigFactory.load();
                String key = conf.getString("jwt-key");
                String s = Jwts.builder().setSubject(email).signWith(SignatureAlgorithm.HS512, key).compact();
                return Response.ok().entity(s).build();
            }else {
                Reviewer rev = new Reviewer();
                rev.setEmail(email);
                rev.setName(name);
                return Response.status(Response.Status.CREATED).entity(rev).build();
            }

        } else {
            System.out.println("Invalid ID token.");
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
    }

}
