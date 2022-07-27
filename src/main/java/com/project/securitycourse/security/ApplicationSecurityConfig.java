package com.project.securitycourse.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import static com.project.securitycourse.security.ApplicationUserPermission.COURSE_WRITE;
import static com.project.securitycourse.security.ApplicationUserRole.*;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                .and()
                .authorizeRequests()
                .antMatchers("/", "index", "/css/*", "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();

    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        UserDetails borisUser = User.builder()
                .username("Boris")
                .password(passwordEncoder.encode("password"))
//                .roles(STUDENT.name()) // ROLE_STUDENT (internally)
                .authorities(STUDENT.getGrantedAuthorities())
                .build();

        UserDetails ivanSuperUser = User.builder()
                .username("Ivan")
                .password(passwordEncoder.encode("password"))
//                .roles(ADMIN.name()) // ROLE_ADMIN (internally)
                .authorities(ADMIN.getGrantedAuthorities())
                .build();

        UserDetails dragunovUser = User.builder()
                .username("Dragunov")
                .password(passwordEncoder.encode("password"))
//                .roles(ADMINTRAINEE.name()) // ROLE_ADMINTRAINEE (internally)
                .authorities(ADMINTRAINEE.getGrantedAuthorities())
                .build();

        return new InMemoryUserDetailsManager(
                borisUser,
                ivanSuperUser,
                dragunovUser
        );
    }



}

