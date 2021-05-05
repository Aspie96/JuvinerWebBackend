package com.juviner.juvinerwebbackend.userservice.github;

import com.juviner.juvinerwebbackend.userservice.User;
import com.juviner.juvinerwebbackend.userservice.UserDao;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class GithubAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDao userDao;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("Demo 2");
        
        if(!supports(authentication.getClass())) {
            return null;
        }

        GithubAuthenticationToken githubIdAuthenticationToken = (GithubAuthenticationToken)authentication;

        String code = (String)githubIdAuthenticationToken.getCredentials();
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
        Optional<User> user = userDao.findByGithubId(id);
        if(user.isPresent()) {
            return new UsernamePasswordAuthenticationToken(user.get().getUsername(), "", user.get().getAuthorities());
        } else {
            throw new BadCredentialsException("User sub not found.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        System.out.println("Demo 1");
        return GithubAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
