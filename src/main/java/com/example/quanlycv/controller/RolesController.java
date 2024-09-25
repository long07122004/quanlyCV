package com.example.quanlycv.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RolesController {
    @GetMapping("/role")
    public String viewRole(Model model){
        return  "roles/index-role";
    }
}
