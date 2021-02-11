package com.juviner.juvinerwebdemo.sectionservice;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class SectionsController {
    private final SectionDao sectionDao;
    private final CategoryDao categoryDao;
    
    public SectionsController(SectionDao sectionDao, CategoryDao categoryDao) {
        this.sectionDao = sectionDao;
        this.categoryDao = categoryDao;
    }

    @GetMapping("/")
    public Iterable<Section> getSections() {
        Iterable<Section> sections = this.sectionDao.findAll();
        return sections;
    }
    
    @GetMapping("/root")
    public Root getRoot() {
        Iterable<Section> sections = this.sectionDao.findAll();
        Root root = new Root("Lorem ipsum dolor sit amet, consectetur adipisci elit, sed do eiusmod tempor incidunt ut labore et dolore magna aliqua.", sections);
        return root;
    }
}
