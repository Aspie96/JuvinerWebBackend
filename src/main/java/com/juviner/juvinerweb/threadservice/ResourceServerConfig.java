package com.juviner.juvinerweb.threadservice;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
public class ResourceServerConfig extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.anonymous()
            .and()
            .authorizeRequests()
            .antMatchers(HttpMethod.POST, "/").authenticated()
            .antMatchers("/**").permitAll();
    }
    
}
