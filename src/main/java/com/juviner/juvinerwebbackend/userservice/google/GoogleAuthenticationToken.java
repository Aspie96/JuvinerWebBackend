package com.juviner.juvinerwebbackend.userservice.google;

import java.util.ArrayList;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class GoogleAuthenticationToken extends AbstractAuthenticationToken {
    private final String credentials;
    private Object principal;

    public GoogleAuthenticationToken(String token, Object details) {
        super(new ArrayList<>());
        this.credentials = token;
        setDetails(details);
        setAuthenticated(false);
    }

    public GoogleAuthenticationToken(String token, String principal, Collection<? extends GrantedAuthority> authorities, Object details) {
        super(authorities);
        this.credentials = token;
        this.principal = principal;
        setDetails(details);
        setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return credentials;
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
