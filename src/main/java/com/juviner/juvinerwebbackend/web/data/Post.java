package com.juviner.juvinerwebbackend.web.data;

import java.io.Serializable;
import java.sql.Timestamp;

public class Post implements Serializable {
    private int id;
    private int threadId;
    private String username;
    private String text;
    private Timestamp time;

    public Post() { }
    
    public Post(int threadId, String username, String text, Timestamp time) {
        this.threadId = threadId;
        this.username = username;
        this.text = text;
        this.time = time;
    }
    
    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getText() {
        return this.text;
    }

    public Timestamp getTime() {
        return this.time;
    }
    
    public int getThreadId() {
        return this.threadId;
    }
}
