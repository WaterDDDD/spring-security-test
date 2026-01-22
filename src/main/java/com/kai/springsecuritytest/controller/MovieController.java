package com.kai.springsecuritytest.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MovieController {

    @GetMapping("/freeMovie")
    public String freeMovie() {

        return "觀看免費電影";
    }

    @GetMapping("/vipMovie")
    public String vipMovie() {

        return "觀看付費電影";
    }

}
