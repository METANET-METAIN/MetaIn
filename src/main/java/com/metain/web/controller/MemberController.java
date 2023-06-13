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
    public String loginPage(Model model) {
        model.addAttribute("loginRequest", new Emp());
        System.out.println(model);

        return "/member/login-form";
    }


    public void authenticateUser(String empSabun, String password) {
        try {
            System.out.println(empSabun);
            // 사용자가 제공한 인증 정보로 UsernamePasswordAuthenticationToken 생성
            Authentication authentication = new UsernamePasswordAuthenticationToken(empSabun, password);

            // 실제 인증 과정 수행
            Authentication authenticated = authenticationManager.authenticate(authentication);

            // 인증 성공한 경우, SecurityContext에 인증된 사용자 정보 저장
            SecurityContextHolder.getContext().setAuthentication(authenticated);

            // 추가적인 로직 수행 가능
            System.out.println(authentication);
            System.out.println(authenticated);

        } catch (AuthenticationException e) {
            // 인증 실패한 경우 예외 처리
            // ...
        }
    }





}
