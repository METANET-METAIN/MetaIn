package com.metain.web.controller;

import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;
import com.metain.web.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;

    @GetMapping("/loginEmp")
    public String loginPage(Model model) {
        model.addAttribute("loginRequest", new Emp());

        return "/member/login-form";
    }

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

//    @PostMapping("/confirm-new-emp")
//    @ResponseBody
//    public int confirmNewEmp(@RequestBody List<NewEmp> newEmp, Emp emp) {
//        System.out.println(newEmp);
//        return memberService.confirmNewEmp(newEmp, emp);
//    }


//    @RequestMapping("/loginEmp")
//    @ResponseBody
//    public Emp login(@RequestBody Emp emp, HttpSession session){
//        Emp loginEmp = memberService.login(emp);
//        if(loginEmp != null) {
//            session.setAttribute("loginEmp", loginEmp);
//            System.out.println("로그인!!!: " + loginEmp);
//            System.out.println("empId : " + emp.getEmpId());
//
//        }
//        return loginEmp;
//
//    }
}
