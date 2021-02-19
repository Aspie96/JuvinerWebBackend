package com.juviner.juvinerwebbackend.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@SpringBootApplication
public class WebApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}
        
        @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebApplication.class);
	}
        
        @Bean
        public RouteLocator routeLocator(RouteLocatorBuilder builder) {
            String test = "lb://api";
            return builder.routes()
                    .route(r -> r
                        .path("/api/**")
                        .filters(f->f.rewritePath("/api/(?<segment>.*)","/${segment}"))
                        .uri(test)
                    )
                    .build();
        }
        
        @Bean
        public RouterFunction<ServerResponse> imgRouter() {
            return RouterFunctions
              .resources("/templates/**", new ClassPathResource("templates/"));
        }
}
