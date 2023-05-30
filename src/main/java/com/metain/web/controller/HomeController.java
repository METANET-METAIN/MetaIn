package com.metain.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {
    @RequestMapping("/index")
    public String home() {
        return "index";
    }

    @GetMapping("/hr/{newEmp}")
    public String goPage(@PathVariable String newEmp) {
        return "/hr/" + newEmp;
    }
}