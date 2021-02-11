package com.juvinerwebbackend.api;

import org.graalvm.compiler.lir.CompositeValue.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
//@EnableResourceServer
//@EnableWebFluxSecurity
public class ApiApplication {
    
    /*@Autowired
private TokenRelayGatewayFilterFactory filterFactory;

@Bean
public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
    return builder.routes()
            .route("resource", r -> r.path("/resource")
              .filters(f -> f.filters(filterFactory.apply())
                .removeRequestHeader("Cookie"))
            .uri("http://resource:9000")) 
            .build();
}*/
    
    
    /*@Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) {
        return http.authorizeExchange().pathMatchers("/login**", "/error**").permitAll()
    .anyExchange().authenticated().and().oauth2Login().and().build();
    }*/
    
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }

    /*@Bean
    public SecurityWebFilterChain securitygWebFilterChain(ServerHttpSecurity http) throws Exception {
        http.anonymous()
            .and()
            .authorizeExchange()
            .pathMatchers("/**").permitAll();
        return http.build();
    }*/
}
