package com.ifsp.teste.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ifsp.teste.models.User;
import com.ifsp.teste.repository.UserRepository;



@Controller
public class UserController {   

    @Autowired
    public UserRepository userRepo;

    @GetMapping("/signup")
    public String viewSignup() {
        return "signup";
    }    

    public Map<String, String> bodyToMap(String bodyStr) {
        Map<String, String> body = new HashMap<>();
        String[] values = bodyStr.split("&");
        for (String value : values) {
            String[] pair = value.split("=");
            if (pair.length == 2) {
            body.put(pair[0], pair[1]);
            }
        }
        return body;
    }

    @PostMapping("/signup")
    public String signup(@RequestBody String body) {
        Map<String, String> bodyParsed = bodyToMap(body);

        User u = new User();
        u.setUsername(bodyParsed.get("username"));
        u.setPassword(bodyParsed.get("password"));
        userRepo.save(u);
        return "index";
    }
    
}
