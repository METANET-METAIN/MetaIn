package com.metain.web.controller;

import com.metain.web.domain.Emp;
import com.metain.web.domain.Vacation;
import com.metain.web.dto.VacationListDTO;
import com.metain.web.service.HrService;
import com.metain.web.service.VacationService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("vacation/")
public class VacationController {
    @Autowired
    private VacationService vacationService;
    @Autowired
    private HrService hrService;
    @RequestMapping("/vacation-list")
    public String vacationList(Model model) {
        List<VacationListDTO> list=vacationService.selectAllList();

        model.addAttribute("vacList",list);

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
    public String vacationDetail(@PathVariable("vacationId") Long vacationId,Model model) {
        if (vacationId == null) {
            new ModelAndView("redirect:/vacation/vacation-list");// vacationId가 없을 경우 기본 페이지로 리다이렉션
        }
        Vacation vac=vacationService.vacationDetail(vacationId);
        System.out.println("어드민 넘"+vac.getAdmId());
        //신청인 정보
        Emp emp=hrService.selectEmpInfo(vac.getEmpId());
        //관리자 정보
        Emp admin=hrService.selectEmpInfo(vac.getAdmId());
        //총 사용날짜 구하기
        java.util.Date startDate = new java.util.Date(vac.getVacStartDate().getTime());
        java.util.Date endDate = new java.util.Date(vac.getVacEndDate().getTime());

        long diff = endDate.getTime() - startDate.getTime();
        int daysDiff = (int) (diff / (24 * 60 * 60 * 1000)+1);
        System.out.println(emp);
        System.out.println(admin);
        model.addAttribute("vac",vac);
        model.addAttribute("emp",emp);
        model.addAttribute("admin",admin);
        model.addAttribute("diff",daysDiff);
        return "/vacation/vacation-detail";
    }

    //요청 휴가 디테일
    @RequestMapping("/request-vacation/{vacationId}")
    public String requestedVacation(@PathVariable("vacationId") Long vacationId, Model model){
        if (vacationId == null) {
            new ModelAndView("redirect:/vacation/vacation-list");// vacationId가 없을 경우 기본 페이지로 리다이렉션
        }
        Vacation vac=vacationService.vacationDetail(vacationId);
        System.out.println("어드민넘"+vac.getAdmId());
        //신청인 정보
        Emp emp=hrService.selectEmpInfo(vac.getEmpId());
        //관리자 정보
        Emp admin=hrService.selectEmpInfo(vac.getAdmId());
        //총 사용날짜 구하기
        java.util.Date startDate = new java.util.Date(vac.getVacStartDate().getTime());
        java.util.Date endDate = new java.util.Date(vac.getVacEndDate().getTime());

        long diff = endDate.getTime() - startDate.getTime();
        int daysDiff = (int) (diff / (24 * 60 * 60 * 1000)+1);
        //System.out.println(emp);
        //System.out.println(admin);
        model.addAttribute("vac",vac);
        model.addAttribute("emp",emp);
        model.addAttribute("admin",admin);
        model.addAttribute("diff",daysDiff);
        return "/vacation/request-vacation";
    }
    //요청 휴가 목록
    @RequestMapping("/vacation-req-list")
    public String requestedVacationList(Model model){
        List<VacationListDTO> list = vacationService.requestList();
        model.addAttribute("list",list);
        return "/vacation/vacation-req-list";
    }
    @PostMapping(value ="/approveVacationRequest")
    @ResponseBody
    public ResponseEntity<String> approveVacationRequest(@RequestBody Map<String,Long> requestData) {
        Long vacId = requestData.get("vacationId");
       vacationService.approveVacationRequest(vacId);
        return ResponseEntity.ok("성공");
    }
    @PostMapping(value ="/rejectVacationRequest")
    @ResponseBody
    public ResponseEntity<String> rejectVacationRequest(@RequestBody Map<String,Long> requestData) {
        Long vacId = requestData.get("vacationId");
       vacationService.rejectVacationRequest(vacId);
        return ResponseEntity.ok("성공");
    }

}
