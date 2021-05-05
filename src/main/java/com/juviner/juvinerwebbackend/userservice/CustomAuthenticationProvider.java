/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.juviner.juvinerwebbackend.userservice;

import com.juviner.juvinerwebbackend.userservice.google.GoogleTokenVerifierService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDao userDao;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    @Autowired
    private GoogleTokenVerifierService googleIdTokenVerifierService;
    
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
 
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        
        
        if(username.startsWith("github:")) {
            String code = username.substring("github:".length());
            LinkedMultiValueMap<String, String> body = new  LinkedMultiValueMap<>();
            body.add("code", code);
            body.add("client_id", "Iv1.f1dd9ba30d3451a9");
            body.add("client_secret", "7f570071306e3d068c9378f6364dae9e8036ef0e");
            Map res = WebClient.create("https://github.com/login/oauth/access_token")
                .post()
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(body)).retrieve().bodyToMono(Map.class).block();
            String token = (String)res.get("access_token");
            System.out.println(token);
            Map data = WebClient.create("https://api.github.com/user")
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .headers(headers -> headers.setBearerAuth(token))
                .retrieve().bodyToMono(Map.class).block();
            int id = (int)data.get("id");
            Optional<com.juviner.juvinerwebbackend.userservice.User> user = userDao.findByGithubId(id);
            if(user.isPresent()) {
                return new UsernamePasswordAuthenticationToken(user.get().getUsername(), "", user.get().getAuthorities());
            } else {
                throw new UsernameNotFoundException(username);
            }
        } else if(username.startsWith("google:")) {
            String token = username.substring("google:".length());
            
            System.out.println("Credentials: " + token);
            GoogleIdToken googleIdToken = googleIdTokenVerifierService.verify(token);
            GoogleIdToken.Payload payload = googleIdToken.getPayload();

            String sub = (String)payload.get("sub");
            System.out.println("Sub: " + sub);

            Optional<User> user = userDao.findByGoogleSub(sub);
            System.out.println("User: " + user.isPresent());
            if(user.isPresent()) {
                return new UsernamePasswordAuthenticationToken(user.get().getUsername(), "", user.get().getAuthorities());
            } else {
                throw new BadCredentialsException("User sub not found.");
            }
        }
        Optional<com.juviner.juvinerwebbackend.userservice.User> user = this.userDao.findByUsername(username);
        if(!user.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        if(encoder.matches(password, user.get().getPassword())) {
            return new UsernamePasswordAuthenticationToken(user.get().getUsername(), "", user.get().getAuthorities());
        } else {
            throw new BadCredentialsException("Bad credentials.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
