package com.metain.web.controller;

import com.metain.web.domain.Emp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MemberController {

    @GetMapping("/")
    public String login(Emp emp){
        return "member/login-form";

    }
}
