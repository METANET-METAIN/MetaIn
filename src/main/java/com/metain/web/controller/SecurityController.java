package com.metain.web.controller;


import com.metain.web.dto.PrincipalDetails;
import com.metain.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/login-form")

public class SecurityController {

    private final MemberService memberService;

    @GetMapping("/")
    public String home(ModelMap model)throws Exception{

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();

        model.addAttribute("name", principalDetails.getUsername());
        model.addAttribute("auth", principalDetails.getAuthorities());

//        if(auth != null) {
//            Emp loginEmp = memberService.login(auth.getName());
//            if(loginEmp != null) {
//                model.addAttribute("loginSabun", loginEmp.getEmpSabun());
//            }
//        }
        return "redirect:/index";
    }

    @GetMapping("/access-denied")
    public String loadAccessdeniedPage() throws Exception{
        return "/index";
    }
}
