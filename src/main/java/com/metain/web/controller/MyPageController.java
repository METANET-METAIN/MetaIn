package com.metain.web.controller;

import com.metain.web.domain.*;
import com.metain.web.dto.AlarmDTO;
import com.metain.web.dto.MyVacDTO;
import com.metain.web.service.HrService;
import com.metain.web.service.MyPageService;
import com.metain.web.service.VacationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
public class MyPageController {


    @Autowired
    private MyPageService myPageService;
    @Autowired
    private VacationService vacationService;
    @Autowired
    private HrService hrService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());



    //재직증명서 리스트
    @GetMapping("/my-empCert")
    @ResponseBody
    public List<EmpCert> selectMyEmpCert(EmpCert empCert){
        return myPageService.selectMyEmpCert(empCert);
    }


    //경력증명서 리스트
    @GetMapping("/my-experCert")
    @ResponseBody
    public List<ExperienceCert> selectMyExperCert(ExperienceCert experienceCert){
        return myPageService.selectMyExperCert(experienceCert);
    }


    //퇴직증명서 리스트
    @GetMapping("/my-retireCert")
    @ResponseBody
    public List<RetireCert> selectMyRetCert(RetireCert retireCert){
        return myPageService.selectMyRetCert(retireCert);
    }



    @GetMapping("/my-vac")
    @ResponseBody
    public List<MyVacDTO> selectMyVacList(Authentication auth,@ModelAttribute MyVacDTO myVacDTO) {
        PrincipalDetails principalDetails= (PrincipalDetails) auth.getPrincipal();
        Long empId=principalDetails.getEmpId();
        myVacDTO.setEmpId(empId);
        return myPageService.selectMyVacList(myVacDTO);
    }
    @GetMapping("/my-vac-list")
    public String myVacList(Authentication auth, Model model) {
        PrincipalDetails principalDetails= (PrincipalDetails) auth.getPrincipal();
        Long empId=principalDetails.getEmpId();
        Emp emp=hrService.selectEmpInfo(empId);
        List<MyVacDTO> myList=myPageService.myVacList(empId);
        model.addAttribute("vacList",myList);
        model.addAttribute("emp",emp);
        return "/mypage/my-vac-list";
    }

    @GetMapping("/alarm")
    public String alarmList(Authentication auth, Model model) {
        PrincipalDetails principalDetails = (PrincipalDetails)auth.getPrincipal();
        Long empId = principalDetails.getEmpId();
        Emp emp =  hrService.selectEmpInfo(empId);

        List<AlarmDTO> alarmList=myPageService.alarmList(empId);
        model.addAttribute("alarmList",alarmList);
        model.addAttribute("emp",emp);
        return "/mypage/alarm";
    }
    @GetMapping("/my-vac-detail/{vacationId}")
    public String myVacDetail(@PathVariable("vacationId") Long vacationId, Model model) {
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
        return "/mypage/my-vac-detail";
    }

    @PostMapping(value ="/cancelVacationRequest")
    @ResponseBody
    public ResponseEntity<String> cancelVacationRequest(@RequestBody Map<String,Object> requestData) {
        Long vacId = Long.parseLong(requestData.get("vacationId").toString());
        Long empId = Long.parseLong(requestData.get("empId").toString());
        String vacStatus=requestData.get("vacStatus").toString();
        int diff=Integer.parseInt(requestData.get("diff").toString());

        vacationService.cancelVacationRequest(vacId,empId,vacStatus);
        vacationService.increaseVacation(diff,empId);
        return ResponseEntity.ok("성공");
    }
    @PostMapping("/updateMy")
    public String  updateMy(Emp emp) {
        Emp dbemp = hrService.selectEmpInfo(emp.getEmpId());
        dbemp.setEmpAddr(emp.getEmpAddr());
        dbemp.setEmpPhone(emp.getEmpPhone()); //여기 비번두 추가, 맵퍼 쿼리문에도
        dbemp.setEmpZipcode(emp.getEmpZipcode());
        dbemp.setEmpDetailAddr(emp.getEmpDetailAddr());
        myPageService.updateMy(dbemp);

        return "redirect:/mypage/update-mypage";
    }


}
