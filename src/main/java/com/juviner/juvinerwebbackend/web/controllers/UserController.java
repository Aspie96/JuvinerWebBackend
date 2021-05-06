package com.juviner.juvinerwebbackend.web.controllers;

import com.juviner.juvinerwebbackend.web.WebClients;
import com.juviner.juvinerwebbackend.web.data.Root;
import com.juviner.juvinerwebbackend.web.data.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user/{username}")
class UserController {
    @Autowired
    private WebClients clients;
    
    @GetMapping
    public String home(Model model, @PathVariable String username) {
        if(username.equals("self")) {
            return "user_self_page";
        }
        User user = clients.usersClient().get().uri("/{username}", username).retrieve().bodyToMono(User.class).block();
        model.addAttribute("user", user);
        return "user_page";
    }
}