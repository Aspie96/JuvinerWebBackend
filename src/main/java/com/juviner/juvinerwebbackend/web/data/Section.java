package com.juviner.juvinerwebbackend.web.data;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

public class Section implements Serializable {
    private int id;
    private String name;
    @JsonManagedReference("category-section")
    private List<Category> categories;

    public Section() { }
    
    public int getId() {
        return this.id;
    }
    
    public List<Category> getCategories() {
        return Collections.unmodifiableList(this.categories);
    }
    
    public String getName() {
        return this.name;
    }
}
