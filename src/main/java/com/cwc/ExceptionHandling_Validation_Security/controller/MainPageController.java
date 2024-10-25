package com.cwc.ExceptionHandling_Validation_Security.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainPageController {
    @GetMapping
    public String welcomeMessage(HttpServletRequest request)
    {
        return "Welocem. Please Enter a correct URL. Session ID: "+ request.getSession().getId();
    }
}
