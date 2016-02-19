package com.tcc.servidor_tcc.tokenUtil;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class Token {
    
        public static String createClientToken(String email){
            Config conf = ConfigFactory.load();
            String key = conf.getString("jwt-key");
            String s = Jwts.builder().setSubject(email).signWith(SignatureAlgorithm.HS512, key).compact();
            return s;
        }
    
}