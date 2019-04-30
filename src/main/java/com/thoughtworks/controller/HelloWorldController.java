package com.thoughtworks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.thoughtworks.service.HelloWorldService;

@RestController
@RequestMapping("/api/helloworld")
public class HelloWorldController {
    @Autowired
    private HelloWorldService helloWorldService;

    @GetMapping
    public String getHelloWorld() {
        return helloWorldService.getHelloWorld();
    }
}
