package com.juvinerwebbackend.api;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.HttpMethod;
import org.springframework.http.codec.ServerCodecConfigurer;
/*import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;*/
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

public class JwtLoginFilter /*extends AuthenticationWebFilter*/ {
    /*public JwtLoginFilter(ReactiveAuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.setRequiresAuthenticationMatcher(
            ServerWebExchangeMatchers.anyExchange()
        );
        this.setAuthenticationSuccessHandler(new ServerAuthenticationSuccessHandler() {
            @Override
            public Mono<Void> onAuthenticationSuccess(WebFilterExchange wfe, Authentication a) {
                wfe.getExchange().getResponse().getHeaders().add("Auth", a.getName());
                return wfe.getChain().filter(wfe.getExchange().mutate().response(wfe.getExchange().getResponse()).build());
            }
        });
        this.setServerAuthenticationConverter(new AuthenticationConverter());
    }
    
    @Override
    protected Mono<Void> onAuthenticationSuccess(Authentication authentication, WebFilterExchange webFilterExchange) {
        ServerWebExchange exchange = webFilterExchange.getExchange().mutate().request(webFilterExchange.getExchange().getRequest().mutate().header("Auth", authentication.getName()).build()).build();
        return super.onAuthenticationSuccess(authentication, new WebFilterExchange(exchange, webFilterExchange.getChain()));
    }*/
}
