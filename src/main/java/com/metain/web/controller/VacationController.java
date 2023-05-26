package com.metain.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
