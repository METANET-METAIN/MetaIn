package com.metain.web.controller;

import com.metain.web.domain.Emp;
import com.metain.web.domain.PrincipalDetails;
import com.metain.web.domain.Vacation;
import com.metain.web.dto.AlarmDTO;
import com.metain.web.dto.VacationFileDTO;
import com.metain.web.dto.VacationListDTO;
import com.metain.web.dto.VacationWithoutFileDTO;
import com.metain.web.mapper.FileMapper;
import com.metain.web.mapper.HrMapper;
import com.metain.web.service.HrService;
import com.metain.web.service.MemberService;
import com.metain.web.service.VacationService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/vacation")
@AllArgsConstructor
public class VacationController {
    @Autowired
    private VacationService vacationService;
    @Autowired
    private HrService hrService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private FileMapper fileMapper;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping("/vacation-list")
    public String vacationList(Model model,Authentication auth) {
        List<VacationListDTO> list=vacationService.selectAllList();
        PrincipalDetails principalDetails= (PrincipalDetails) auth.getPrincipal();
        Long empId= principalDetails.getEmpId();
        Emp empInfo=hrService.selectEmpInfo(empId);

        model.addAttribute("vacList",list);
        model.addAttribute("emp",empInfo);
        return "/vacation/vacation-list";
    }

    @RequestMapping("/vacation-applyform")
    public String vacationApplyForm(Authentication auth,Model model) {
        PrincipalDetails principalDetails= (PrincipalDetails) auth.getPrincipal();
        Long empId= principalDetails.getEmpId();
        Emp emp=hrService.selectEmpInfo(empId);

        String empDept=emp.getEmpDept();
        Emp admin=memberService.selectAdminInfo(empDept,"ADMIN");

        model.addAttribute("emp",emp);
        model.addAttribute("admin",admin);


        return "/vacation/vacation-applyform";
    }
    @PostMapping("/insert-vaction")
    public String insertVacation(Vacation vacation,@RequestParam("selectedDays") String diffDays, @RequestParam("empId")Long empId){
        vacationService.insertVacation(vacation, Integer.parseInt(diffDays),empId,vacation.getAdmId());
        System.out.println(vacation);
        //사용한 만큼 연차 차감
        empId=vacation.getEmpId();
        int selectedDays=Integer.parseInt(diffDays);
        vacationService.decreaseVacation(selectedDays,empId);


        return "redirect:/mypage/my-vac-list";
    }
    @RequestMapping("/vacation-afterapply")
    public void vacationAfterApplyForm(Authentication auth,Model model) {
        PrincipalDetails principalDetails= (PrincipalDetails) auth.getPrincipal();
        Long empId= principalDetails.getEmpId();
        Emp emp=hrService.selectEmpInfo(empId);

        String empDept=emp.getEmpDept();
        Emp admin=memberService.selectAdminInfo(empDept,"ADMIN");
        model.addAttribute("emp",emp);
        model.addAttribute("admin",admin);
    }
    @PostMapping("/insert-aftervaction")
    public String insertAfterVacation(@RequestParam("file") MultipartFile file,@RequestParam("selectedDays") String diffDays ,Vacation vacation) throws IOException {
        Emp empInfo = hrService.selectEmpInfo(vacation.getEmpId());
        Long empId=empInfo.getEmpId();
        empId=vacation.getEmpId();
        int selectedDays=Integer.parseInt(diffDays);

        vacationService.insertAfterVacation(vacation,file,vacation.getAdmId());
        vacationService.decreaseVacation(selectedDays,empId);

        return "redirect:/mypage/my-vac-list";
    }

    @GetMapping("/vacation-detail/{vacationId}")
    public String vacationDetail(@PathVariable("vacationId") Long vacationId,Model model,Authentication auth) {
        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
        Long empId = principalDetails.getEmpId();
        Emp empInfo = hrService.selectEmpInfo(empId);
        logger.info("VacatioCon/vacationDetail-vacationId= ",vacationId);
        if (vacationId == null) {
            new ModelAndView("redirect:/vacation/vacation-list");// vacationId가 없을 경우 기본 페이지로 리다이렉션
        }
        VacationFileDTO vac = vacationService.vacationDetail(vacationId);
        logger.info("VacatioCon/vacationDetail-vac= ",vac);
        if (vac == null) {
            VacationWithoutFileDTO vacWithoutFile = vacationService.vacationDetailWithoutFile(vacationId);
            //신청한 사람
            Emp emp = hrService.selectEmpInfo(vacWithoutFile.getEmpId());
            //관리자 정보
            Emp admin = hrService.selectEmpInfo(vacWithoutFile.getAdmId());
            //총 사용날짜 구하기
            java.util.Date startDate = new java.util.Date(vacWithoutFile.getVacStartDate().getTime());
            java.util.Date endDate = new java.util.Date(vacWithoutFile.getVacEndDate().getTime());

            long diff = endDate.getTime() - startDate.getTime();
            int daysDiff = (int) (diff / (24 * 60 * 60 * 1000) + 1);

            model.addAttribute("vac", vacWithoutFile);
            model.addAttribute("emp", empInfo); //관리자로 로그인한 유저
            model.addAttribute("req", emp); //신청한 사람
            model.addAttribute("admin", admin);
            model.addAttribute("diff", daysDiff);

            return "/vacation/vacation-detail";
        }else{
            //신청한 사람
            Emp emp=hrService.selectEmpInfo(vac.getEmpId());
            //관리자 정보
            Emp admin=hrService.selectEmpInfo(vac.getAdmId());
            //총 사용날짜 구하기
            java.util.Date startDate = new java.util.Date(vac.getVacStartDate().getTime());
            java.util.Date endDate = new java.util.Date(vac.getVacEndDate().getTime());

            long diff = endDate.getTime() - startDate.getTime();
            int daysDiff = (int) (diff / (24 * 60 * 60 * 1000)+1);
            model.addAttribute("vac",vac);
            model.addAttribute("emp",empInfo); //관리자로 로그인한 유저
            model.addAttribute("req",emp); //신청한 사람
            model.addAttribute("admin",admin);
            model.addAttribute("diff",daysDiff);
            return "/vacation/vacation-detail";
        }
    }

    //요청 휴가 디테일
    @RequestMapping("/request-vacation/{vacationId}")
    public String requestedVacation(@PathVariable("vacationId") Long vacationId, Model model,Authentication auth){
        PrincipalDetails principalDetails= (PrincipalDetails) auth.getPrincipal();
        Long empId= principalDetails.getEmpId();
        Emp empInfo=hrService.selectEmpInfo(empId);
        logger.info("VacatioCon/requestedVacation-vacationId= ",vacationId);
        if (vacationId == null) {
            new ModelAndView("redirect:/vacation/vacation-list");// vacationId가 없을 경우 기본 페이지로 리다이렉션
        }
        VacationFileDTO vac = vacationService.vacationDetail(vacationId);
        logger.info("VacatioCon/requestedVacation-vac= ",vac);
        if(vac==null){
            VacationWithoutFileDTO vacWithoutFile = vacationService.vacationDetailWithoutFile(vacationId);
            //신청한 사람
            Emp emp=hrService.selectEmpInfo(vacWithoutFile.getEmpId());
            //관리자 정보
            Emp admin=hrService.selectEmpInfo(vacWithoutFile.getAdmId());
            //총 사용날짜 구하기
            java.util.Date startDate = new java.util.Date(vacWithoutFile.getVacStartDate().getTime());
            java.util.Date endDate = new java.util.Date(vacWithoutFile.getVacEndDate().getTime());

            long diff = endDate.getTime() - startDate.getTime();
            int daysDiff = (int) (diff / (24 * 60 * 60 * 1000)+1);

            model.addAttribute("vac",vacWithoutFile);
            model.addAttribute("emp",empInfo); //관리자로 로그인한 유저
            model.addAttribute("req",emp); //신청한 사람
            model.addAttribute("admin",admin);
            model.addAttribute("diff",daysDiff);
            return "/vacation/request-vacation";
        }else{
            //신청한 사람
            Emp emp=hrService.selectEmpInfo(vac.getEmpId());
            //관리자 정보
            Emp admin=hrService.selectEmpInfo(vac.getAdmId());
            //총 사용날짜 구하기
            java.util.Date startDate = new java.util.Date(vac.getVacStartDate().getTime());
            java.util.Date endDate = new java.util.Date(vac.getVacEndDate().getTime());

            long diff = endDate.getTime() - startDate.getTime();
            int daysDiff = (int) (diff / (24 * 60 * 60 * 1000)+1);
            model.addAttribute("vac",vac);
            model.addAttribute("emp",empInfo); //관리자로 로그인한 유저
            model.addAttribute("req",emp); //신청한 사람
            model.addAttribute("admin",admin);
            model.addAttribute("diff",daysDiff);
            return "/vacation/request-vacation";
        }
    }
    //요청 휴가 목록
    @RequestMapping("/vacation-req-list")
    public String requestedVacationList(Authentication auth,Model model){
        PrincipalDetails principalDetails= (PrincipalDetails) auth.getPrincipal();
        Long empId= principalDetails.getEmpId();
        Emp empInfo=hrService.selectEmpInfo(empId); //관리자의 emp

        List<VacationListDTO> list = vacationService.requestList(empInfo.getEmpDept());
        logger.info("requestedVacationList의 list",list);
        model.addAttribute("list",list);
        model.addAttribute("emp",empInfo);
        return "/vacation/vacation-req-list";
    }
    @PostMapping(value ="/approveVacationRequest")
    @ResponseBody
    public ResponseEntity<String> approveVacationRequest(@RequestBody Map<String,Object> requestData) {
        Long vacId = Long.parseLong(requestData.get("vacationId").toString());
        String vacStatus=requestData.get("vacStatus").toString();
        Long receiver = Long.parseLong(requestData.get("receiver").toString());


        vacationService.approveVacationRequest(vacId,vacStatus,receiver);
        return ResponseEntity.ok("성공");
    }
    @PostMapping(value ="/rejectVacationRequest")
    @ResponseBody
    public ResponseEntity<String> rejectVacationRequest(@RequestBody Map<String,Object> requestData) {
        Long vacId = Long.parseLong(requestData.get("vacationId").toString());
        String vacStatus=requestData.get("vacStatus").toString();
        Long receiver = Long.parseLong(requestData.get("receiver").toString());

        vacationService.rejectVacationRequest(vacId,vacStatus,receiver);
        return ResponseEntity.ok("성공");
    }
    @GetMapping("/calendar")
    public ResponseEntity<List<VacationListDTO>> calendar(Authentication auth) {
        PrincipalDetails principalDetails = (PrincipalDetails)auth.getPrincipal();
        Long EmpId = principalDetails.getEmpId();
        Emp emp =  hrService.selectEmpInfo(EmpId);
        if (emp != null) {
            String empDept = emp.getEmpDept();
            LocalDate today = LocalDate.now();
            List<VacationListDTO> events = vacationService.calendar(empDept,today);
            logger.info("events==================================================",events);
            return ResponseEntity.ok(events);
        } else {
            // 로그인되지 않은 경우 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @GetMapping("/alarm")
    public ResponseEntity<List<AlarmDTO>> alarm(Authentication auth) {
        PrincipalDetails principalDetails = (PrincipalDetails)auth.getPrincipal();
        Long EmpId = principalDetails.getEmpId();
        Emp emp =  hrService.selectEmpInfo(EmpId);
        if (emp != null) {
            List<AlarmDTO> alarmList = vacationService.alarmListAll(emp.getEmpId());
            return ResponseEntity.ok(alarmList);
        } else {
            // 로그인되지 않은 경우 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    @GetMapping("/today")
    public ResponseEntity<List<VacationListDTO>>todayVacation(Authentication auth) {
        PrincipalDetails principalDetails = (PrincipalDetails)auth.getPrincipal();
        Long EmpId = principalDetails.getEmpId();
        Emp emp =  hrService.selectEmpInfo(EmpId);
        if (emp != null) {
            String empDept= emp.getEmpDept();
            List<VacationListDTO> alarmList = vacationService.todayVacation(empDept);
            logger.info("VacatioCon/todayVacation-alarmList= ",alarmList);
            return ResponseEntity.ok(alarmList);
        } else {
            // 로그인되지 않은 경우 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
    public void setHrService(HrService hrService) {
    }

    public void setModel(Model model) {
    }
}
