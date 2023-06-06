package com.metain.web.controller;

import com.metain.web.domain.Emp;
import com.metain.web.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class MemberController {

    @Autowired
    private MemberService memberService;

//    @PostMapping("/loginEmp")
//    public String login(Emp emp, HttpServletRequest request,
//                        RedirectAttributes rediAttr, Model model){
//
//         HttpSession session = request.getSession();
//         Emp empUser = memberService.login(emp);
//         if(empUser == null){
//             session.setAttribute("empUser", null);
//             rediAttr.addAttribute("msg", false);
//             model.addAttribute("fail", 1);
//
//             return "member/login-form";
//
//         } else {
//             session.setAttribute("empUser", empUser);
//             session.setMaxInactiveInterval(1800);
//         }
//            return "redirect:/index";
//
//    }


    @PostMapping("/loginEmp")
    @ResponseBody
    public Emp login(@RequestBody Emp emp, HttpSession session){
        Emp loginEmp = memberService.login(emp);
        if(loginEmp != null){
            session.setAttribute("loginEmp", loginEmp);
            System.out.println("로그인!!!: " +  loginEmp);
            System.out.println(emp.getEmpId());
        }
        return loginEmp;

    }
}
