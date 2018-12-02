package com.gilvam.cursomc.security;

import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;

import java.util.Date;

@Component
public class JWTUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long expiration;

    public String generateToken(String userName){
        return Jwts.builder()
                .setSubject(userName) // user
                .setExpiration(new Date(System.currentTimeMillis() + expiration)) // tempo de expiração
                .signWith(SignatureAlgorithm.HS512, secret) // como vai assinar o token
                .compact();
    }

}
