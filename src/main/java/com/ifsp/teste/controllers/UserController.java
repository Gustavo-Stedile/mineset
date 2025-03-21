package com.ifsp.teste.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.ifsp.teste.models.User;
import com.ifsp.teste.repositories.UserRepository;
import com.ifsp.teste.utils.BodyParser;



@Controller
public class UserController {   

    @Autowired
    public UserRepository userRepo;

    @GetMapping("/signup")
    public String viewSignup() {
        return "signup";
    }    

    @PostMapping("/signup")
    public String signup(@RequestBody String body) {
        Map<String, String> bodyParsed = BodyParser.parse(body);

        User u = new User();
        u.setUsername(bodyParsed.get("username"));
        u.setPassword(bodyParsed.get("password"));
        userRepo.save(u);
        return "index";
    }
    
}
