package com.dev.pc.configuration;

import com.dev.pc.auth.filter.JWTAuthenticationFilter;
import com.dev.pc.auth.filter.JWTAuthorizationFilter;
import com.dev.pc.auth.service.JWTService;
import com.dev.pc.services.JPAUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JPAUserDetailsService userDetailsService;

    @Autowired
    private JWTService jwtService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder (){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/", "/css/**", "/js/**", "/images/**", "/api/marcas").permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtService))
                .addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtService))
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);//deshabilitar el uso de
        // sesion
    }

    @Override
    public void configure(AuthenticationManagerBuilder builder) throws Exception{

        PasswordEncoder encoder = passwordEncoder();

        UserBuilder users = User.builder().passwordEncoder( password -> {
            return encoder.encode(password);
        } );

        builder.userDetailsService(userDetailsService)
                .passwordEncoder( passwordEncoder() );
    }
}
