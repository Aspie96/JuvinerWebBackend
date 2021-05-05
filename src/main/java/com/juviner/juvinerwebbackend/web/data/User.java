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
    private int githubId;
    private String githubUsername;
    
    public User() { }
    
    public User(int id, String username, String description, String email, String avatar, int githubId, String githubUsername) {
        this.id = id;
        this.description = description; this.username = username;
        this.email = email;
        this.githubId = githubId;
        this.githubUsername = githubUsername;
    }
    
    public User(String username, String description, String email, String avatar, int githubId, String githubUsername) {
        this.description = description; this.username = username;
        this.email = email;
        this.githubId = githubId;
        this.githubUsername = githubUsername;
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
    
    public int getGithubId() {
        return this.githubId;
    }
    
    public String getGithubUsername() {
        return this.githubUsername;
    }
}
