package com.metain.web.controller;

import com.metain.web.domain.Emp;
import com.metain.web.domain.PrincipalDetails;
import com.metain.web.domain.Vacation;
import com.metain.web.dto.AlarmDTO;
import com.metain.web.dto.VacationListDTO;
import com.metain.web.service.AlarmService;
import com.metain.web.service.HrService;
import com.metain.web.service.MemberService;
import com.metain.web.service.VacationService;
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
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/vacation")
public class VacationController {
    @Autowired
    private VacationService vacationService;
    @Autowired
    private HrService hrService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/vacation-list")
    public String vacationList(Model model,Authentication auth) {
        List<VacationListDTO> list=vacationService.selectAllList();
        Emp empInfo = (Emp) auth.getPrincipal();
        model.addAttribute("vacList",list);
        model.addAttribute("emp",empInfo);
        return "/vacation/vacation-list";
    }

    @RequestMapping("/vacation-applyform")
    public String vacationApplyForm(Authentication auth,Model model) {
        Emp empInfo= (Emp) auth.getPrincipal();

        Emp emp=hrService.selectEmpInfo(empInfo.getEmpId());
        String empDept=emp.getEmpDept();
        Emp admin=memberService.selectAdminInfo(empDept,"ROLE_ADMIN");

            model.addAttribute("emp",emp);
            model.addAttribute("admin",admin);
        return "/vacation/vacation-applyform";
    }
    @PostMapping("/insert-vaction")
    public String insertVacation(Vacation vacation,@RequestParam("selectedDays") String diffDays, @RequestParam("empId")Long empId){
        vacationService.insertVacation(vacation);
        //사용한 만큼 연차 차감
        empId=vacation.getEmpId();
        int selectedDays=Integer.parseInt(diffDays);
        vacationService.decreaseVacation(selectedDays,empId);
        return "redirect:/mypage/my-vac-list";
    }
    @RequestMapping("/vacation-afterapply")
    public void vacationAfterApplyForm(Authentication auth,Model model) {
        Emp empInfo= (Emp) auth.getPrincipal();
        Emp emp=hrService.selectEmpInfo(empInfo.getEmpId());
        String empDept=emp.getEmpDept();
        Emp admin=memberService.selectAdminInfo(empDept,"ROLE_ADMIN");
        model.addAttribute("emp",emp);
        model.addAttribute("admin",admin);
    }
    @PostMapping("/insert-aftervaction")
    public String insertAfterVacation(@RequestParam("file") MultipartFile file,@RequestParam("selectedDays") String diffDays ,Vacation vacation) throws IOException {
        Emp empInfo = hrService.selectEmpInfo(vacation.getEmpId());
        Long empId=empInfo.getEmpId();
        empId=vacation.getEmpId();
        int selectedDays=Integer.parseInt(diffDays);

        String type = vacation.getVacType();
        String sabun = empInfo.getEmpSabun();
        // 파일 이름=휴가타입+날짜 +uuid
        UUID uuid = UUID.randomUUID();
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); // 확장자

        String savedFileName = type + sabun + uuid.toString().substring(0, 4) + extension; // 유형사번uuid

        // 저장될 경로
        String savePath = System.getProperty("user.dir") + "/src/main/resources/static/file/" + savedFileName;

        File destFile = new File(savePath);
        file.transferTo(destFile);

        // 파일 이름을 DB의 file_name 컬럼에 저장
        vacation.setFileName(savedFileName);

        vacationService.insertAfterVacation(vacation);
        vacationService.decreaseVacation(selectedDays,empId);

        return "redirect:/mypage/my-vac-list";
    }

    @GetMapping("/vacation-detail/{vacationId}")
    public String vacationDetail(@PathVariable("vacationId") Long vacationId,Model model) {
        if (vacationId == null) {
            new ModelAndView("redirect:/vacation/vacation-list");// vacationId가 없을 경우 기본 페이지로 리다이렉션
        }
        Vacation vac=vacationService.vacationDetail(vacationId);

        //신청인 정보
        Emp emp=hrService.selectEmpInfo(vac.getEmpId());
        //관리자 정보
        Emp admin=hrService.selectEmpInfo(vac.getAdmId());
        //총 사용날짜 구하기
        java.util.Date startDate = new java.util.Date(vac.getVacStartDate().getTime());
        java.util.Date endDate = new java.util.Date(vac.getVacEndDate().getTime());

        long diff = endDate.getTime() - startDate.getTime();
        int daysDiff = (int) (diff / (24 * 60 * 60 * 1000)+1);

        model.addAttribute("vac",vac);
        model.addAttribute("emp",emp);
        model.addAttribute("admin",admin);
        model.addAttribute("diff",daysDiff);
        return "/vacation/vacation-detail";
    }

    //요청 휴가 디테일
    @RequestMapping("/request-vacation/{vacationId}")
    public String requestedVacation(@PathVariable("vacationId") Long vacationId, Model model,Authentication auth){
        Emp empInfo= (Emp) auth.getPrincipal();
        if (vacationId == null) {
            new ModelAndView("redirect:/vacation/vacation-list");// vacationId가 없을 경우 기본 페이지로 리다이렉션
        }
        Vacation vac=vacationService.vacationDetail(vacationId);
        //신청인 정보
        Emp emp=hrService.selectEmpInfo(vac.getEmpId());
        //관리자 정보
        Emp admin=hrService.selectEmpInfo(vac.getAdmId());
        //총 사용날짜 구하기
        java.util.Date startDate = new java.util.Date(vac.getVacStartDate().getTime());
        java.util.Date endDate = new java.util.Date(vac.getVacEndDate().getTime());

        long diff = endDate.getTime() - startDate.getTime();
        int daysDiff = (int) (diff / (24 * 60 * 60 * 1000)+1);

        model.addAttribute("vac",vac);
        model.addAttribute("emp",empInfo);
        model.addAttribute("req",emp);
        model.addAttribute("admin",admin);
        model.addAttribute("diff",daysDiff);
        return "/vacation/request-vacation";
    }
    //요청 휴가 목록
    @RequestMapping("/vacation-req-list")
    public String requestedVacationList(Authentication auth,Model model){
        List<VacationListDTO> list = vacationService.requestList();
        Emp empInfo= (Emp) auth.getPrincipal();

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

            return ResponseEntity.ok(alarmList);
        } else {
            // 로그인되지 않은 경우 처리
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

}
