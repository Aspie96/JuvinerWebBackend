/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juviner.juvinerwebbackend.userservice;

import com.juviner.juvinerwebbackend.userservice.github.GithubAuthenticationFilter;
import com.juviner.juvinerwebbackend.userservice.google.GoogleAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.anonymous()
            .and()
            .authorizeRequests()
            .antMatchers("/self").authenticated()
            .antMatchers("/**").permitAll()
            .and()
            .addFilterBefore(
                new GoogleAuthenticationFilter(authenticationManager, "/login/google"),
                UsernamePasswordAuthenticationFilter.class
            )
            .addFilterBefore(
                new GithubAuthenticationFilter(authenticationManager, "/login/github"),
                UsernamePasswordAuthenticationFilter.class
            );
    }
}
