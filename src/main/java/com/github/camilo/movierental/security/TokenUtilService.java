package com.github.camilo.movierental.security;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenUtilService {

    public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
    public static final String TOKEN_BEARER_PREFIX = "Bearer ";
    private static final String ISSUER_INFO = "Camilo Pimienta";
    
    @Value("${spring.security.secret.key}")
    private String secretKey;
    
    public String generateToken(Authentication auth) {
        return Jwts.builder().setIssuedAt(new Date()).setIssuer(ISSUER_INFO)
                .setSubject(((User) auth.getPrincipal()).getUsername())
                .signWith(Keys.hmacShaKeyFor(secretKey.getBytes())).compact();
    }
    
    public String getUserFromToken(String token) {
        return Jwts.parserBuilder().setSigningKey(secretKey).build()
        .parseClaimsJws(token.replace(TOKEN_BEARER_PREFIX, StringUtils.EMPTY)).getBody().getSubject();
    }
    
}
