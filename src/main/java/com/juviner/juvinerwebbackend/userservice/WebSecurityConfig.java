/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juviner.juvinerwebbackend.userservice;

import com.juviner.juvinerwebbackend.userservice.github.GithubAuthenticationProvider;
import com.juviner.juvinerwebbackend.userservice.google.GoogleAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
 
    @Autowired
    private CustomAuthenticationProvider authenticationProvider;
    @Autowired
    private GoogleAuthenticationProvider googleAuthenticationProvider;
    @Autowired
    private GithubAuthenticationProvider githubAuthenticationProvider;
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(myUserDetalsService);
        //daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);*/
        auth.authenticationProvider(authenticationProvider).authenticationProvider(googleAuthenticationProvider).authenticationProvider(githubAuthenticationProvider);
    }
    /*
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/prova").authenticated()
            .antMatchers("/**").permitAll();
    }*/
    
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
