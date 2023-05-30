package com.metain.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("vacation/")
public class VacationController {
    @RequestMapping("/vacation-list")
    public String vacationList() {

        return "/vacation/vacation-list";
    }

    @RequestMapping("/vacation-applyform")
    public String vacationApplyForm() {

        return "/vacation/vacation-applyform";
    }
    @RequestMapping("/vacation-afterapply")
    public void vacationAfterApplyForm() {
    }

    @GetMapping("/vacation-detail/{vacationId}")
    public String vacationDetail(@PathVariable("vacationId") Long vacationId) {
        if (vacationId == null || vacationId.equals("")) {
            new ModelAndView("redirect:/vacation/vacation-list");// vacationId가 없을 경우 기본 페이지로 리다이렉션
        }

        return "vacation/vacation-detail";
    }

    //요청 휴가 디테일
    @RequestMapping("/request-vacation/{vacationId}")
    public String requestedVacation(@PathVariable("vacationId") Long vacationId){
        if (vacationId == null || vacationId.equals("")) {
            new ModelAndView("redirect:/vacation/vacation-list");// vacationId가 없을 경우 기본 페이지로 리다이렉션
        }
        return "/vacation/request-vacation";
    }
    //요청 휴가 목록
    @RequestMapping("/vacation-req-list")
    public String requestedVacationList(){

        return "/vacation/vacation-req-list";
    }
    
}
