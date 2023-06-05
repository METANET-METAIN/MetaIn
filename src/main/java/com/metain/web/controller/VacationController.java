package com.metain.web.controller;

import com.metain.web.domain.Emp;
import com.metain.web.domain.Vacation;
import com.metain.web.dto.VacationListDTO;
import com.metain.web.mapper.FileMapper;
import com.metain.web.mapper.HrMapper;
import com.metain.web.service.HrService;
import com.metain.web.service.VacationService;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("vacation/")
public class VacationController {
    @Autowired
    private VacationService vacationService;
    @Autowired
    private HrService hrService;
    @Autowired
    private FileMapper fileMapper;

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
    @PostMapping("/insert-vaction")
    public String insertVacation(Vacation vacation){
        vacationService.insertVacation(vacation);
        return "redirect:/mypage/my-vac-list";
    }
    @RequestMapping("/vacation-afterapply")
    public void vacationAfterApplyForm() {
    }
    @PostMapping("/insert-aftervaction")
    public String insertAfterVacation(@RequestParam("file") MultipartFile file, Vacation vacation, HttpServletRequest request) throws IOException {
        Emp empInfo = hrService.selectEmpInfo(vacation.getEmpId());

        String type = vacation.getVacType();
        int sabun = empInfo.getEmpSabun();
        // 파일 이름
        UUID uuid = UUID.randomUUID();
        String originalFileName = file.getOriginalFilename();
        String extension = originalFileName.substring(originalFileName.lastIndexOf(".")); // 확장자

        String savedFileName = type + sabun + uuid.toString().substring(0, 4) + extension; // 유형사번uuid

        // 저장될 경로
        String savePath = System.getProperty("user.dir") + "/src/main/resources/static/file/" + savedFileName;

        System.out.println(savePath);
        File destFile = new File(savePath);
        file.transferTo(destFile);

        // 파일 이름을 DB의 file_name 컬럼에 저장
        vacation.setFileName(savedFileName);

        vacationService.insertAfterVacation(vacation);

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
