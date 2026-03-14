package com.smartwaste.smart_waste.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/health")

     public String health(){
        return "backend is running perfectly";
    }


}
