package com.metain.web.controller;

import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;
import com.metain.web.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private AuthenticationManager authenticationManager;


    //login 창
    @GetMapping("/loginEmp")
    public String loginPage(@RequestParam(value = "error", required = false)String error,
//                            @RequestParam(value = "exception", required = false)String exception,
                            Model model) {

        model.addAttribute("error", error);
//        model.addAttribute("exception", exception);
        model.addAttribute("loginRequest", new Emp());
        System.out.println(error);
//        System.out.println(exception);
        System.out.println(model);

        return "/member/login-form";
    }

}
