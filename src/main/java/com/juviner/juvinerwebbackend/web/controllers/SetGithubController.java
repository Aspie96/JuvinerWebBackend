package com.juviner.juvinerwebbackend.web.controllers;

import com.juviner.juvinerwebbackend.web.WebClients;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
@RequestMapping("/user/self/github")
class SetGithubController {
    @GetMapping
    public String github(Model model) {
        return "set_github_page";
    }
}