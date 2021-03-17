package com.juviner.juvinerwebbackend.web.data;

import java.sql.Timestamp;

public class Thread {
    private int id;
    private int categoryId;
    private String title;
    private String username;
    private String text;
    private int replies;
    private Timestamp updateTime;

    public Thread() { }

    public Thread(int id, String title, int categoryId, String username, String text, int replies, Timestamp updateTime) {
        this.id = id;
        this.title = title;
        this.categoryId = categoryId;
        this.username = username;
        this.text = text;
        this.replies = replies;
        this.updateTime = updateTime;
    }
    
    public Thread(String title, int categoryId, String username, String text, Timestamp updateTime) {
        this.title = title;
        this.categoryId = categoryId;
        this.username = username;
        this.text = text;
        this.replies = 0;
        this.updateTime = updateTime;
    }

    public int getId() {
        return this.id;
    }

    public int getCategoryId() {
        return this.categoryId;
    }
    
    public String getTitle() {
        return this.title;
    }
    
    public String getUsername() {
        return this.username;
    }
    
    public String getText() {
        return this.text;
    }
    
    public int getReplies() {
        return this.replies;
    }
    
    public Timestamp getUpdateTime() {
        return this.updateTime;
    }
}
