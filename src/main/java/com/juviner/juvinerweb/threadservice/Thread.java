package com.juviner.juvinerweb.threadservice;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="threads")
public class Thread implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(nullable=false)
    private int categoryId;
    @Column(nullable=false)
    private String title;
    @Column(nullable=false)
    private String username;
    @Column(nullable=false)
    private String text;
    @Column(nullable=false)
    private int replies;
    @Column(nullable=false)
    private Timestamp updatetime;

    public Thread() { }

    public Thread(int id, String title, int categoryId, String username, String text, int replies, Timestamp updatetime) {
        this.id = id;
        this.title = title;
        this.categoryId = categoryId;
        this.username = username;
        this.text = text;
        this.replies = replies;
        this.updatetime = updatetime;
    }
    
    public Thread(String title, int categoryId, String username, String text, int replies, Timestamp updatetime) {
        this.title = title;
        this.categoryId = categoryId;
        this.username = username;
        this.text = text;
        this.replies = replies;
        this.updatetime = updatetime;
    }
    
    public Thread(String title, int categoryId, String username, String text, Timestamp updatetime) {
        this.title = title;
        this.categoryId = categoryId;
        this.username = username;
        this.text = text;
        this.replies = 0;
        this.updatetime = updatetime;
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
    
    public Timestamp getUpdatetime() {
        return this.updatetime;
    }
}
