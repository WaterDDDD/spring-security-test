package com.kai.springsecuritytest.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }
}
