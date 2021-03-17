package com.juviner.juvinerwebbackend.web.controllers;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.juviner.juvinerwebbackend.web.WebClients;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/register")
class RegisterController {
    @GetMapping
    public String home(Model model, @RequestParam String code) {
        if(code != null && !code.equals("")) {
            LinkedMultiValueMap<String, String> body = new  LinkedMultiValueMap<>();
            body.add("code", code);
            body.add("client_id", "Iv1.f1dd9ba30d3451a9");
            body.add("client_secret", "7f570071306e3d068c9378f6364dae9e8036ef0e");
            Map res = WebClient.create("https://github.com/login/oauth/access_token")
                .post()
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromFormData(body)).retrieve().bodyToMono(Map.class).block();
            String token = (String)res.get("access_token");
            System.out.println(token);
            Map data = WebClient.create("https://api.github.com/user")
                .get()
                .accept(MediaType.APPLICATION_JSON)
                .headers(headers -> headers.setBearerAuth(token))
                .retrieve().bodyToMono(Map.class).block();
            
            System.out.println((String)data.get("login"));
        }
        return "register_page";
    }
}