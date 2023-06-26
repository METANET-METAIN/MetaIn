package com.metain.web.controller;

import com.metain.web.domain.Emp;
import com.metain.web.domain.NewEmp;
import com.metain.web.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
public class MemberController {
    @Autowired
    private MemberService memberService;
    @Autowired
    private AuthenticationManager authenticationManager;

    private Logger logger = LoggerFactory.getLogger(this.getClass());



    //login 창
    @GetMapping("/loginEmp")
    public String loginPage(@RequestParam(value = "error", required = false)String error,
                            Model model, HttpServletRequest request) {

        model.addAttribute("error", error);
        model.addAttribute("loginRequest", new Emp());
        HttpSession session = request.getSession(false);


        if (session != null) {
            session.setMaxInactiveInterval(7200); // 2시간(7200초)으로 세션 유효시간 설정
            logger.info("sessionId={}", session.getId());
            logger.info("maxInactiveInterval={}", session.getMaxInactiveInterval());
            logger.info("creationTime={}", new Date(session.getCreationTime()));
            logger.info("lastAccessedTime={}", new Date(session.getLastAccessedTime()));
            logger.info("isNew={}", session.isNew());
        } else {
            logger.info("Session does not exist.");
        }

        return "/member/login-form";
    }

}
