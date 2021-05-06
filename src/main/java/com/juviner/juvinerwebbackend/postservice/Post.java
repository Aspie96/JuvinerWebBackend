package com.juviner.juvinerwebbackend.postservice;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="posts")
public class Post implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Column(nullable=false)
    private int threadId;
    @Column(nullable=false)
    private String username;
    @Column(nullable=false)
    private String text;
    @Column(nullable=false)
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
    
    public String getStringTime() {
        return new SimpleDateFormat("dd/MM/yyyy hh:mm").format(this.getTime());
    }
}
