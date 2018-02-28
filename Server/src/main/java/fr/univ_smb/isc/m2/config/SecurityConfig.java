package fr.univ_smb.isc.m2.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import static org.springframework.http.HttpMethod.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobalSecurity(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("steve").password("jobs").roles("ADMIN");
        auth.inMemoryAuthentication().withUser("bill").password("gates").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers(GET, "/api/**").anonymous()
            .antMatchers(POST, "/api/**").anonymous()
            .antMatchers(PUT, "/api/**").anonymous()
            .antMatchers(DELETE, "/api/**").anonymous()
            //.antMatchers(POST, "/api/**").authenticated()
            //.antMatchers(PUT, "/api/**").authenticated()
            //.antMatchers(DELETE, "/api/**").authenticated()
            .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
            .and()
            .csrf().disable()
            .formLogin().usernameParameter("username").passwordParameter("password");
    }

}