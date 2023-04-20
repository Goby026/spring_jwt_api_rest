package com.dev.pc.configuration;

import com.dev.pc.auth.filter.JWTAuthenticationFilter;
import com.dev.pc.auth.filter.JWTAuthorizationFilter;
import com.dev.pc.auth.service.JWTService;
import com.dev.pc.services.JPAUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

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
            http.authorizeRequests()
                    .antMatchers("/", "/api/login", "/api/v1/usuarios", "/api/v1/roles").permitAll()
//                    .antMatchers(HttpMethod.GET,"/api/v1/**").permitAll()
                    .antMatchers(HttpMethod.GET,"/api/v1/clientes/**").permitAll()
                    .antMatchers(HttpMethod.GET,"/api/v1/uploads/img/**").permitAll()
                    .antMatchers(HttpMethod.GET,"/api/v1/deudas/**").permitAll()
                    .antMatchers(HttpMethod.PUT,"/api/v1/deudas/service").permitAll()
                    .antMatchers(HttpMethod.GET,"/api/v1/pagos-servicio/**").permitAll()
                    .antMatchers(HttpMethod.POST,"/api/v1/vouchers/save").permitAll()
                    .antMatchers(HttpMethod.POST,"/api/v1/vouchers/upload").permitAll()
                    .antMatchers(HttpMethod.POST,"/api/v1/vouchers").permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .cors().configurationSource(corsConfigurationSource())
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

    @Bean
    public CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200/"));
//        configuration.setAllowedOrigins(Arrays.asList("http://154.12.238.230/"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter(){
        FilterRegistrationBean<CorsFilter> bean =
                new FilterRegistrationBean<CorsFilter>(new CorsFilter(corsConfigurationSource()));
        bean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return bean;
    }
}

