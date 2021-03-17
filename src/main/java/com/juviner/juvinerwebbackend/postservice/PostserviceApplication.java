package com.juviner.juvinerwebbackend.postservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

@SpringBootApplication
@EnableResourceServer
public class PostserviceApplication {
	public static void main(String[] args) {
		SpringApplication.run(PostserviceApplication.class, args);
	}
}
