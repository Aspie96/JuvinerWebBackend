package com.juviner.juvinerwebbackend.userservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class PublicUser implements Serializable {
    private String username;
    private String description;
    private String githubUsername;
    
    public PublicUser() { }
    
    public PublicUser(User user) {
        this.username = user.getUsername();
        this.description = user.getDescription();
        this.githubUsername = user.getGithubUsername();
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getGithubUsername() {
        return this.githubUsername;
    }
}
