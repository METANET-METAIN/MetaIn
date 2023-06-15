package com.metain.web.controller;


import com.metain.web.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member/login-form")

public class SecurityController {

    private final MemberService memberService;

//    @GetMapping("/")
//    public String home(ModelMap model, Authentication auth)throws Exception{
//
//
//        auth = SecurityContextHolder.getContext().getAuthentication();
//        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
//        model.addAttribute("empId", principalDetails.getEmpId());
//        model.addAttribute("name", principalDetails.getUsername());
//
//        model.addAttribute("auth", principalDetails.getAuthorities());
//
//        System.out.println(auth.getName());
//        System.out.println(auth.getPrincipal());
//        System.out.println(principalDetails.getEmpId());


//        if(auth != null) {
//            Emp loginEmp = memberService.login(auth.getName());
//            if(loginEmp != null) {
//                model.addAttribute("loginSabun", loginEmp.getEmpSabun());
//            }
////        }
//        return "redirect:/index";
//    }

    @GetMapping("/access-denied")
    public String loadAccessdeniedPage() throws Exception{
        return "/index";
    }
}
