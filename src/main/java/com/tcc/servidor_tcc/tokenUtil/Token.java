package com.tcc.servidor_tcc.tokenUtil;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Token {
    
        public static String createClientToken(String email){
            String key = getServerToken();
            String s = Jwts.builder().setSubject(email).signWith(SignatureAlgorithm.HS512, key).compact();
            return s;
        }
        
        public static String getClientEmail(String jwt)
        {
            String key = getServerToken();
            String email = Jwts.parser().setSigningKey(key).parseClaimsJws(jwt).getBody().getSubject();
            return email;
        }
        
        public static String getServerToken(){
             Config conf = ConfigFactory.load();
             return conf.getString("app.jwt-key");
        }
    
}