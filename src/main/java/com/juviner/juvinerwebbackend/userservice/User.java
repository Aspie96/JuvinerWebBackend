package com.juviner.juvinerwebbackend.userservice;

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
    @Column(nullable=true)
    private String avatar;
    @Column(nullable=true)
    private String github;
    
    public User() { }
    
    public User(int id, String username, String description, String email, String password, String avatar, String github) {
        this.id = id;
        this.description = description; this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.github = github;
    }
    
    public User(String username, String description, String email, String password, String avatar, String github) {
        this.description = description; this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.github = github;
    }
    
    public int getId() {
        return this.id;
    }
    
    @Override
    public String getUsername() {
        return this.username;
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
    
    public String getAvatar() {
        return this.avatar;
    }
    
    public String getGithub() {
        return this.github;
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
}
