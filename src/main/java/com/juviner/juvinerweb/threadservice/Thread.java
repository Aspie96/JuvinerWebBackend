package com.juviner.juvinerweb.threadservice;

import java.io.Serializable;
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
