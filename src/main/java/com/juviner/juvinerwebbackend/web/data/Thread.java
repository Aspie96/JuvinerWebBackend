package com.juviner.juvinerwebbackend.web.data;

import java.io.Serializable;

public class Thread implements Serializable {
    private int id;
    private int categoryId;
    private String title;
    private String username;
    private String text;

    public Thread() { }

    public Thread(String title, int categoryId, String username, String text) {
        this.title = title;
        this.categoryId = categoryId;
        this.username = username;
        this.text = text;
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
}
