package com.example.quanlycv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class test {
    @GetMapping("/test")
    public String viewTest(){
        return "profile";
    }
    @GetMapping("/")
    public String viewTest1(){
        return "Menu";
    }
}
