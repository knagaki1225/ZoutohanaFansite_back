package com.example.zoutohanafansite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("/error-4xx")
    public String error4xx() {
        return "auth/404";
    }
}
