package com.juviner.juvinerwebbackend.web.controllers;

import com.juviner.juvinerwebbackend.web.WebClients;
import com.juviner.juvinerwebbackend.web.data.Category;
import com.juviner.juvinerwebbackend.web.data.Root;
import com.juviner.juvinerwebbackend.web.data.Thread;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;

@Controller
@RequestMapping("/category/{id}")
class CategoryController {
    @Autowired
    private WebClients clients;
    
    @GetMapping
    public String home(Model model, @PathVariable int id) {
        Mono<Category> category = clients.sectionsClient().get().uri("/category/{id}", id).retrieve().bodyToMono(Category.class).subscribeOn(Schedulers.boundedElastic());
        Mono<Thread[]> threads = clients.threadsClient().get().uri("/category/{id}", id).retrieve().bodyToMono(Thread[].class).subscribeOn(Schedulers.boundedElastic());
        Tuple2<Category, Thread[]> data = Mono.zip(category, threads).block();
        model.addAttribute("category", data.getT1());
        model.addAttribute("threads", data.getT2());
        return "category_page";
    }
}