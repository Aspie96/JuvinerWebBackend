package com.juviner.juvinerwebbackend.web.controllers;

import com.juviner.juvinerwebbackend.web.WebClients;
import com.juviner.juvinerwebbackend.web.data.Post;
import com.juviner.juvinerwebbackend.web.data.Thread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

@Controller
@RequestMapping("/thread/{threadid}")
class ThreadController {
    @Autowired
    private WebClients clients;
    
    @GetMapping
    public String home(Model model, @PathVariable int threadid) {
        Mono<Thread> thread = clients.threadsClient().get().uri("/{threadid}", threadid).retrieve().bodyToMono(Thread.class);
        Mono<Post[]> posts = clients.postsClient().get().uri("/thread/{threadid}", threadid).retrieve().bodyToMono(Post[].class);
        Tuple2<Thread, Post[]> data = Mono.zip(thread, posts).block();
        model.addAttribute("thread", data.getT1());
        model.addAttribute("posts", data.getT2());
        return "thread_page";
    }
}