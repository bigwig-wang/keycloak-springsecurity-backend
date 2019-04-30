package com.thoughtworks.service;

import org.springframework.stereotype.Service;

@Service
public class HelloWorldService {
    public String getHelloWorld() {
        return "hello world!";
    }
}
