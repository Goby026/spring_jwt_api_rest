package com.dev.pc.auth.service;

import com.dev.pc.auth.SimpleGrantedAuthorityMixin;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@Component
public class JWTServiceImpl implements JWTService {

    public static final String SECRET = "Alguna.Clave.Secreta.123456";

    public static final long EXPIRATION_DATE = 14000000L;

    public static final String TOKEN_PREFIX = "Bearer ";

    public static final String HEADER_STRING = "Authorization";

    @Override
    public String create(Authentication auth) throws JsonProcessingException {

        // en esta implementacion se debe crear el token que se devuelve al cliente
        String username = ((User) auth.getPrincipal()).getUsername();

        // obteniendo los roles
        Collection<? extends GrantedAuthority> roles = auth.getAuthorities();

        Claims claims = Jwts.claims();
        claims.put("authorities", new ObjectMapper().writeValueAsString(roles));

        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .signWith(SignatureAlgorithm.HS512, SECRET.getBytes())
                .setIssuedAt(new Date())
                .setExpiration(new Date( System.currentTimeMillis() + EXPIRATION_DATE ))
                .compact();

        return token;
    }

    @Override
    public boolean validate(String token) {
        try {
            this.getClaims(token);
            return true;
        }catch (JwtException | IllegalArgumentException ex){
            ex.printStackTrace();
            return false;
        }
    }

    @Override
    public Claims getClaims(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(this.resolve(token)).getBody();
        return claims;
    }

    @Override
    public String getUsername(String token) {
        return this.getClaims(token).getSubject();
    }

    @Override
    public Collection<? extends GrantedAuthority> getRoles(String token) throws IOException {
        Object roles = this.getClaims(token).get("authorities");
        Collection<GrantedAuthority> authorities = Arrays.asList( new ObjectMapper()
                .addMixIn(SimpleGrantedAuthority.class, SimpleGrantedAuthorityMixin.class)
                .readValue(roles.toString().getBytes(), SimpleGrantedAuthority[].class) );
        return authorities;
    }

    @Override
    public String resolve(String token) {
        if (token != null && token.startsWith(TOKEN_PREFIX)){
            return token.replace(TOKEN_PREFIX, "");
        }

        return null;
    }
}
