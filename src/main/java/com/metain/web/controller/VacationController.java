package com.metain.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class VacationController {
    @RequestMapping("vacation/vacation-list")
    public String vacationList() {

        return "/vacation/vacation-list";
    }

    @RequestMapping("vacation/vacation-applyform")
    public String vacationApplyForm() {

        return "/vacation/vacation-applyform";
    }
    @RequestMapping("vacation/vacation-afterapply")
    public void vacationAfterApplyForm() {
    }

    @GetMapping("vacation/vacation-detail/{vacationId}")
    public String vacationDetail(@PathVariable("vacationId") Long vacationId) {
        if (vacationId == null || vacationId.equals("")) {
            new ModelAndView("redirect:/vacation/vacation-list");// vacationId가 없을 경우 기본 페이지로 리다이렉션
        }

        return "vacation/vacation-detail";
    }

    @RequestMapping("vacation/request-vacation")
    public String requestedVacation(){
        return "vacation/request-vacation";
    }
}
