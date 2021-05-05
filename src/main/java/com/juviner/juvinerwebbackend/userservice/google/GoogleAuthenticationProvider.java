package com.juviner.juvinerwebbackend.userservice.google;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.juviner.juvinerwebbackend.userservice.User;
import com.juviner.juvinerwebbackend.userservice.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class GoogleAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDao userDao;

    @Autowired
    private GoogleTokenVerifierService googleIdTokenVerifierService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("Demo 2");
        
        if(!supports(authentication.getClass())) {
            return null;
        }

        GoogleAuthenticationToken googleIdAuthenticationToken = (GoogleAuthenticationToken)authentication;

        System.out.println("Credentials: " + googleIdAuthenticationToken.getCredentials());
        GoogleIdToken googleIdToken = googleIdTokenVerifierService.verify((String)googleIdAuthenticationToken.getCredentials());
        GoogleIdToken.Payload payload = googleIdToken.getPayload();

        String sub = (String)payload.get("sub");
        System.out.println("Sub: " + sub);

        Optional<User> user = userDao.findByGoogleSub(sub);
        System.out.println("User: " + user.isPresent());
        if(user.isPresent()) {
            return new GoogleAuthenticationToken((String)googleIdAuthenticationToken.getCredentials(), user.get().getUsername(), user.get().getAuthorities(), authentication.getDetails());
        } else {
            throw new BadCredentialsException("User sub not found.");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        System.out.println("Demo 1");
        return GoogleAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
