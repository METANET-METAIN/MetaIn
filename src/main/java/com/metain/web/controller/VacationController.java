package com.metain.web.controller;

import com.metain.web.domain.Vacation;
import com.metain.web.dto.VacationListDTO;
import com.metain.web.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("vacation/")
public class VacationController {
    @Autowired
    private VacationService vacationService;




    @RequestMapping("/vacation-lists")
    @ResponseBody
    public List<VacationListDTO> vacationLists(Model model) {
        List<VacationListDTO> list = vacationService.selectAllList();
        System.out.println(list);
        model.addAttribute("vacList",list);
        return list;
    }

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
