package com.juviner.juvinerwebbackend.userservice.github;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.Assert;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GithubAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final String tokenParamName = "idToken";

    public GithubAuthenticationFilter(AuthenticationManager authenticationManager, String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
        Assert.notNull(authenticationManager, "authenticationManager cannot be null");
        setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String token = request.getParameter(tokenParamName);

        System.out.println("Token: " + token);
        if(token == null) {
            return null;
        }
        System.out.println("Token: " + token);

        Object details = this.authenticationDetailsSource.buildDetails(request);
        System.out.println("Details: " + details);
        GithubAuthenticationToken authRequest = new GithubAuthenticationToken(token, details);
        System.out.println("Auth req: " + authRequest);
        Authentication a = getAuthenticationManager().authenticate(authRequest);
        System.out.println("a: " + a);
        return a;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        SecurityContextHolder.getContext().setAuthentication(authResult);
        if(this.eventPublisher != null) {
            this.eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(authResult, this.getClass()));
        }
        chain.doFilter(request, response);
    }
}
