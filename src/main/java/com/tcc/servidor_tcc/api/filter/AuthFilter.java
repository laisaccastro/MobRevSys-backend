package com.tcc.servidor_tcc.api.filter;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
@Authenticate
public class AuthFilter implements ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext containerRequestContext) throws IOException {

        String jwt = containerRequestContext.getHeaderString("Authorization");
        if(jwt==null||jwt.equals("")){
            throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
        Config conf = ConfigFactory.load();
        try{
            Jws<Claims> claims = Jwts.parser().setSigningKey(conf.getString("app.jwt-key")).parseClaimsJws(jwt);

        }catch (SignatureException e){
                throw new WebApplicationException(Response.Status.UNAUTHORIZED);
        }
    }
}
