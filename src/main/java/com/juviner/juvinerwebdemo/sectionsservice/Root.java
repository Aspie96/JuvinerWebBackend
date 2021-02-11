package com.juviner.juvinerwebdemo.sectionsservice;

public class Root {
    private final String description;
    private final Iterable<Section> sections;
    
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
