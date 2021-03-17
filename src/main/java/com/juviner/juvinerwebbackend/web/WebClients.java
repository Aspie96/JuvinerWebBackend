package com.juviner.juvinerwebbackend.web;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class WebClients {
    private final WebClient sectionsClient;
    private final WebClient threadsClient;
    private final WebClient usersClient;
    private final WebClient postsClient;

    public WebClients(WebClient.Builder webClientBuilder) {
        this.sectionsClient = webClientBuilder.baseUrl("lb://sections").build();
        this.threadsClient = webClientBuilder.baseUrl("lb://threads").build();
        this.usersClient = webClientBuilder.baseUrl("lb://users").build();
        this.postsClient = webClientBuilder.baseUrl("lb://posts").build();
    }
    
    public WebClient sectionsClient() {
        return this.sectionsClient;
    }
    
    public WebClient threadsClient() {
        return this.threadsClient;
    }
    
    public WebClient usersClient() {
        return this.usersClient;
    }
    
    public WebClient postsClient() {
        return this.postsClient;
    }
}
