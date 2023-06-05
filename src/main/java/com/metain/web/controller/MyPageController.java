package com.metain.web.controller;

import com.metain.web.domain.*;
import com.metain.web.dto.MyCertDTO;
import com.metain.web.dto.MyVacDTO;
import com.metain.web.service.HrService;
import com.metain.web.service.MyPageService;
import com.metain.web.service.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/update-mypage")
    public String updateMyPage() {
        return "/mypage/update-mypage";
    }

//    @RequestMapping("/my-cert-list")
//    public String selectIssueAll(Model model) {
//        List<MyCertDTO> list = myPageService.selectIssueAll();
//
//        model.addAttribute("issueList", list);
//        return "/mypage/my-cert-list";
//    }

    //재직증명서 리스트
    @GetMapping("/my-empCert")
    @ResponseBody
    public List<EmpCert> selectMyEmpCert(EmpCert empCert){
//        empCert.setEmpId(4L);
        return myPageService.selectMyEmpCert(empCert);
    }
    //경력증명서 리스트
    @GetMapping("/my-experCert")
    @ResponseBody
    public List<ExperienceCert> selectMyExperCert(ExperienceCert experienceCert){
//        experienceCert.setEmpId(4L);
        return myPageService.selectMyExperCert(experienceCert);
    }
    //퇴직증명서 리스트
    @GetMapping("/my-retireCert")
    @ResponseBody
    public List<RetireCert> selectMyRetCert(RetireCert retireCert){
//        retireCert.setEmpId(4L);
        return myPageService.selectMyRetCert(retireCert);
    }



    @GetMapping("/my-vac")
    @ResponseBody
    public List<MyVacDTO> selectMyVacList(@ModelAttribute MyVacDTO myVacDTO) {
        myVacDTO.setEmpId(5L); //일단 시큐리티 구현전까지 하드코딩 나중에 삭제할거임
        return myPageService.selectMyVacList(myVacDTO);
    }

    @GetMapping("/my-vac-list")
    public String myVacList(Long empId, Model model) {
        empId=5L;
        List<MyVacDTO> myList=myPageService.myVacList(empId);
        model.addAttribute("vacList",myList);
        return "/mypage/my-vac-list";
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
    public ResponseEntity<String> approveVacationRequest(@RequestBody Map<String,Object> requestData) {
        Long vacId = Long.parseLong(requestData.get("vacationId").toString());
        Long empId = Long.parseLong(requestData.get("empId").toString());
        String vacStatus=requestData.get("vacStatus").toString();

        vacationService.cancelVacationRequest(vacId,empId,vacStatus);
        return ResponseEntity.ok("성공");
    }
}
