package com.juviner.juvinerwebdemo.sectionservice;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="sections")
public class Section implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;
    @Lob
    @Column(nullable=false)
    private String name;
    @OneToMany(mappedBy="section")
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
