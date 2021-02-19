package com.juviner.juvinerwebbackend.web.controllers;

import com.juviner.juvinerwebbackend.web.WebClients;
import com.juviner.juvinerwebbackend.web.data.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
class RootController {
    @Autowired
    private WebClients clients;
    
    @GetMapping
    public String home(Model model) {
        Root root = clients.sectionsClient().get().uri("/root").retrieve().bodyToMono(Root.class).block();
        model.addAttribute("root", root);
        return "home_page";
    }
}