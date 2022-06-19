package com.dev.pc.auth.filter;

import com.dev.pc.auth.service.JWTService;
import com.dev.pc.auth.service.JWTServiceImpl;
import com.dev.pc.models.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    private JWTService jwtService;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;

        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/api/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = obtainUsername(request);
        logger.warn("User Request --> "+username);
//        username = (username != null) ? username.trim() : "";
        String password = obtainPassword(request);
//        password = (password != null) ? password : "";

        if(username != null && password != null){
            logger.info("Username desde request parameter (form-data) "+username);
            logger.info("Password desde request parameter (form-data) "+password);
        }else{
//            convirtiendo los datos de la peticion en JSON
            Usuario us = null;
            try {
//                convirtiendo JSON a objeto java
                us = new ObjectMapper().readValue(request.getInputStream(), Usuario.class) ;

                username = us.getUsername();
                password = us.getPassword();

                logger.info("Username desde request parameter (JSON) "+username);
                logger.info("Password desde request parameter (JSON) "+password);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password);

        // Allow subclasses to set the "details" property
//        setDetails(request, authToken);
        return authenticationManager.authenticate(authToken);
    }

//    este metodo se actica una vez que hay exito en la autenticacion
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String token = this.jwtService.create(authResult);

        response.addHeader(JWTServiceImpl.HEADER_STRING, JWTServiceImpl.TOKEN_PREFIX + token);

        Map<String, Object> body = new HashMap<String, Object>();

        body.put("token", token);
        body.put("user", (User) authResult.getPrincipal());
        body.put("msg", String.format("Hola %s, iniciaste sesion con exito", ((User) authResult.getPrincipal()).getUsername()));

//        la instancia ObjectMapper nos permite convertir cualquier objeto de java en un JSON
        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(200);
        response.setContentType("application/json");

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        Map<String, Object> body = new HashMap<String, Object>();

        body.put("msg", String.format("Error de autenticación, username o password incorrecto") );
        body.put("error", failed.getMessage() );

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setStatus(401);
        response.setContentType("application/json");
    }
}
