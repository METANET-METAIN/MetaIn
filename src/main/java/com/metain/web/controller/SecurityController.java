//package com.metain.web.controller;
//
//
//import com.metain.web.domain.Emp;
//import com.metain.web.service.MemberService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//@Controller
//@RequiredArgsConstructor
//@RequestMapping("/member/login-form")
//
//public class SecurityController {
//
//    private final MemberService memberService;
//
//    @GetMapping("/")
//    public String home(Model model, Authentication auth) {
//        model.addAttribute("loginType", "login-form");
//        model.addAttribute("pageName", "Security login");
//
//        if(auth != null) {
//            Emp loginEmp = memberService.login(auth.getName());
//            if(loginEmp != null) {
//                model.addAttribute("loginSabun", loginEmp.getEmpSabun());
//            }
//        }
//        return "/index";
//    }
//}
