package com.metain.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mypage")
public class MyPageController {

    @GetMapping("/my-cert-list")
    public String selectIssueAll() {
        return "/mypage/my-cert-list";
    }

    @GetMapping("/my-vac-list")
    public String selectMyVacList() {
        return "/mypage/my-vac-list";
    }
}
