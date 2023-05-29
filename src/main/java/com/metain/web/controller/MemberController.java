package com.metain.web.controller;

import com.metain.web.domain.Emp;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MemberController {

    @PostMapping
    public String login(Emp emp){
        return "member/login-form";

    }
}
