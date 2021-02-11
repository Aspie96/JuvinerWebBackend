/*package com.juvinerwebbackend.api;

import com.google.common.net.HttpHeaders;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;*/

public class AuthenticationConverter /*implements ServerAuthenticationConverter*/ {
    /*private Mono<String> resolveToken(ServerWebExchange exchange) {
        System.out.println("demoC");
        return Mono.justOrEmpty(exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION))
            .filter(t -> t.startsWith("Bearer "))
            .map(t -> t.substring(7));
    }

    public boolean validateToken(String authToken) {
        System.out.println("demoB");
        Jwts.parser().setSigningKey("demoyugijhuijkbkhuyhbvgyuhjihubgvyjihubvgyjiokpdemoyugijhuijkbkhuyhbvgyuhjihubgvyjihubvgyjiokp").parseClaimsJws(authToken);
        return true;
    }
    
    public Authentication getAuthentication(String token) {
        System.out.println("demoA");
        Claims claims = Jwts.parser()
            .setSigningKey("demoyugijhuijkbkhuyhbvgyuhjihubgvyjihubvgyjiokpdemoyugijhuijkbkhuyhbvgyuhjihubgvyjihubvgyjiokp")
            .parseClaimsJws(token)
            .getBody();
*/
        /*Collection<? extends GrantedAuthority> authorities =
            Arrays.stream(claims..toString().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());*/
/*
        List<GrantedAuthority> l = new ArrayList<>();
        l.add(new SimpleGrantedAuthority("READ_AUTHORITY"));
        UsernamePasswordAuthenticationToken a = new UsernamePasswordAuthenticationToken(claims.getSubject(), "p", l);
        return a;
    }
    */
    /*@Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
    return null/*resolveToken(exchange)
            .filter(this::validateToken)
            .map(this::getAuthentication)*//*;
    }*/
}
