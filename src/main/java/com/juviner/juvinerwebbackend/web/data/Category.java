package com.juviner.juvinerwebbackend.web.data;

import com.fasterxml.jackson.annotation.JsonBackReference;
import java.io.Serializable;

public class Category implements Serializable {
    private int id;
    @JsonBackReference("category-section")
    private Section section;
    private String name;
    private String description;
    
    public Category() { }
    
    public int getId() {
        return this.id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Section getSection() {
        return this.section;
    }
    
    public String getDescription() {
        return this.description;
    }
}
