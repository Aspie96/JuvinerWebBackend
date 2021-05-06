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
import org.springframework.security.core.userdetails.UserDetails;

@Entity
@JsonInclude(Include.NON_NULL)
@Table(name="users")
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(nullable=false)
    private String username;
    @Lob
    @Column(nullable=false)
    private String description;
    @Column(nullable=false)
    private String email;
    @Column(nullable=false)
    private String password;
    @Column(nullable=false)
    private int githubId;
    @Column(nullable=true)
    private String githubUsername;
    @Column(name="google_sub", nullable=true)
    private String googleSub;
    
    public User() { }
    
    public User(int id, String username, String description, String email, String password, int githubId, String githubUsername, String googleSub) {
        this.id = id;
        this.description = description; this.username = username;
        this.email = email;
        this.password = password;
        this.githubId = githubId;
        this.githubUsername = githubUsername;
        this.googleSub = googleSub;
    }
    
    public User(String username, String description, String email, String password, int githubId, String githubUsername, String googleSub) {
        this.description = description; this.username = username;
        this.email = email;
        this.password = password;
        this.githubId = githubId;
        this.githubUsername = githubUsername;
        this.googleSub = googleSub;
    }
    
    public int getId() {
        return this.id;
    }
    
    @Override
    public String getUsername() {
        return this.username;
    }
    
    public void hideCredentials() {
        this.password = null;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    @Override
    public String getPassword() {
        return this.password;
    }
    
    public int getGithubId() {
        return this.githubId;
    }
    
    public String getGithubUsername() {
        return this.githubUsername;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    public String getGoogleSub() {
        return this.googleSub;
    }
}
