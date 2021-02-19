package com.juviner.juvinerwebbackend.web.data;

public class Root {
    private String description;
    private Iterable<Section> sections;
    
    public Root() { }
    
    public Root(String description, Iterable<Section> sections) {
        this.sections = sections;
        this.description = description;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public Iterable<Section> getSections() {
        return this.sections;
    }
}
