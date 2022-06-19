package com.dev.pc.services;

import com.dev.pc.models.Role;
import com.dev.pc.models.Usuario;
import com.dev.pc.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class JPAUserDetailsService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(JPAUserDetailsService.class);

//    repo
    @Autowired
    private UsuarioRepository repository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.warn("UserDetailsService --> "+username);
        Usuario usuario = repository.findByUsername(username);
        logger.warn("Usuario --> "+usuario);
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        for ( Role role: usuario.getRoles() ) {
            authorities.add(new SimpleGrantedAuthority(role.getAuthority()));
        }
        return new User(usuario.getUsername(),usuario.getPassword(), usuario.getEnabled(), true, true,true, authorities);
    }
}
