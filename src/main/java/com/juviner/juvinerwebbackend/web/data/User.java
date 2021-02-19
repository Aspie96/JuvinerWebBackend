package com.juviner.juvinerwebbackend.web.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class User implements Serializable {
    private int id;
    private String username;
    private String description;
    private String email;
    private String password;
    private String avatar;
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
    
    public String getUsername() {
        return this.username;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getEmail() {
        return this.email;
    }
    
    public String getPassword() {
        return this.password;
    }
    
    public String getAvatar() {
        return this.avatar;
    }
    
    public String getGithub() {
        return this.github;
    }
}
