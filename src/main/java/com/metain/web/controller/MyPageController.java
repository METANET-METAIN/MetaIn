package com.metain.web.controller;

import com.metain.web.domain.*;
import com.metain.web.dto.AlarmDTO;
import com.metain.web.dto.MyVacDTO;
import com.metain.web.dto.VacationFileDTO;
import com.metain.web.dto.VacationWithoutFileDTO;
import com.metain.web.mapper.FileMapper;
import com.metain.web.service.HrService;
import com.metain.web.service.MyPageService;
import com.metain.web.service.VacationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.nio.file.Files;


import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/mypage")
public class MyPageController {
    @Autowired
    private FileMapper fileMapper;

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

    public List<EmpCert> selectMyEmpCert(Authentication auth){

        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal(); //로그인해져있는 토큰가져오기
        Long empId = principalDetails.getEmpId();

        return myPageService.selectMyEmpCert(empId);
    }


    //경력증명서 리스트
    @GetMapping("/my-experCert")
    @ResponseBody
    public List<ExperienceCert> selectMyExperCert(Authentication auth){
        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal(); //로그인해져있는 토큰가져오기
        Long empId = principalDetails.getEmpId();

        return myPageService.selectMyExperCert(empId);
    }


    //퇴직증명서 리스트
    @GetMapping("/my-retireCert")
    @ResponseBody
    public List<RetireCert> selectMyRetCert(Authentication auth){
        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal(); //로그인해져있는 토큰가져오기
        Long empId = principalDetails.getEmpId();

        return myPageService.selectMyRetCert(empId);
    }


//    //파일 다운로드
//    @ResponseBody
//    @Transactional
//    @PostMapping("/downloadCert/{certId}/{certSort}")
//    public ResponseEntity<Resource> downloadCert(@PathVariable("certId") Long certId, @PathVariable("certSort") String certSort) throws IOException {
//
//        System.out.println("뷰단에서 값잘받아왔나 확인 :  certid랑 certsort " + certId + " , " + certSort);
//
//        //받아온 certId를 이용해서 파일이름 을 얻어오기
//        String filename = myPageService.getCertFilename(certId, certSort) + ".pdf"; //다운로드할 PDF 파일명 - 디지털서명된 파일이름 empcert같은 객체에서 가져오기
//
//        System.out.println("증명서파일이름 가져왔나 확인 : " + filename);
//        //Path filepath = Paths.get("/src/main/resources/static/certPdfFile", filename); // PDF 파일의 경로
//        //Resource fileResource = new PathResource(filepath);
//        Resource fileResource = new ClassPathResource("static/certPdfFile/" + filename);
//
//        //둘중 뭐가 낫지? 뭔차이?
//        // 디지털 서명이 포함된 PDF 파일을 PathResource로 생성
//        //Resource fileResource = new FileSystemResource(filePath);
//        //Resource fileResource = new PathResource(filePath);
//
//
//        //다운로드한번 받으면 issueStatus 값 1로 업데이트 서비스메소드
//        //myPageService.updateIssueStatus(certId,certSort);
//
//        if (fileResource.exists()) {
//
//            return ResponseEntity
//                    .ok()
//                    .contentType(MediaType.APPLICATION_PDF)
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
//                    .body(fileResource);
//        } else {
//            // 파일이 존재하지 않을 경우 에러 처리 로직
//            // 예를 들어 404 Not Found 응답을 반환하거나 다른 방식으로 처리 가능
//            return ResponseEntity.notFound().build();
//        }
//    }//downloadCert



    //파일 다운로드 시도 ==> 이게더 빠름 이걸 기준으로 하기 !!
    @ResponseBody
    @Transactional
    @PostMapping("/downloadCert/{certId}/{certSort}")
    public ResponseEntity<Resource> downloadCert(@PathVariable("certId") Long certId, @PathVariable("certSort") String certSort) throws IOException {

        System.out.println("뷰단에서 값잘받아왔나 확인 :  certid랑 certsort " + certId + " , " + certSort);

        //받아온 certId를 이용해서 파일이름 을 얻어오기
        //String filename = myPageService.getCertFilename(certId, certSort) + ".pdf"; //다운로드할 PDF 파일명 - 디지털서명된 파일이름 empcert같은 객체에서 가져오기
        String filename = "converted.pdf";
        //System.out.println("증명서파일이름 가져왔나 확인 : " + filename);
        //Resource fileResource = new ClassPathResource("static/certPdfFile/" + filename);  //로컬용
        Resource fileResource = new ClassPathResource("/metainfiles/converted.pdf");


        // 파일을 byte 배열로 읽어옴
        byte[] fileData = Files.readAllBytes(fileResource.getFile().toPath());
        ByteArrayResource byteArrayResource = new ByteArrayResource(fileData);


        //다운로드한번 받으면 issueStatus 값 1로 업데이트 서비스메소드
        //myPageService.updateIssueStatus(certId,certSort);

        if (byteArrayResource.exists()) {

            return ResponseEntity
                    .ok()
                    .contentType(MediaType.APPLICATION_PDF)
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                    .body(byteArrayResource);
        } else {
            // 파일이 존재하지 않을 경우 에러 처리 로직
            // 예를 들어 404 Not Found 응답을 반환하거나 다른 방식으로 처리 가능
            return ResponseEntity.notFound().build();
        }
    }//downloadCert




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
    public String myVacDetail(@PathVariable("vacationId") Long vacationId, Model model,Authentication auth) {
        PrincipalDetails principalDetails= (PrincipalDetails) auth.getPrincipal();
        Long empId= principalDetails.getEmpId();
        Emp empInfo=hrService.selectEmpInfo(empId);
        if (vacationId == null) {
            new ModelAndView("redirect:/vacation/vacation-list");// vacationId가 없을 경우 기본 페이지로 리다이렉션
        }
        VacationFileDTO vac = vacationService.vacationDetail(vacationId);
        System.out.println(vac);

        if(vac==null){
            VacationWithoutFileDTO vacWithoutFile = vacationService.vacationDetailWithoutFile(vacationId);
            System.out.println(vacWithoutFile);
            //신청한 사람이자
            Emp emp=hrService.selectEmpInfo(vacWithoutFile.getEmpId());
            //관리자 정보
            Emp admin=hrService.selectEmpInfo(vacWithoutFile.getAdmId());
            //총 사용날짜 구하기
            java.util.Date startDate = new java.util.Date(vacWithoutFile.getVacStartDate().getTime());
            java.util.Date endDate = new java.util.Date(vacWithoutFile.getVacEndDate().getTime());

            long diff = endDate.getTime() - startDate.getTime();
            int daysDiff = (int) (diff / (24 * 60 * 60 * 1000)+1);
            System.out.println(vacWithoutFile);
            model.addAttribute("vac",vacWithoutFile);
            model.addAttribute("emp",empInfo);
            model.addAttribute("admin",admin);
            model.addAttribute("diff",daysDiff);
            model.addAttribute("req",emp); //신청한 사람
            return "/mypage/my-vac-detail";
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
            String filePath=fileMapper.getFilePath(vac.getFileId());
            model.addAttribute("vac",vac);
            model.addAttribute("emp",empInfo); //관리자로 로그인한 유저
            model.addAttribute("admin",admin);
            model.addAttribute("diff",daysDiff);
            model.addAttribute("file",filePath);
            model.addAttribute("req",emp); //신청한 사람
            return "/mypage/my-vac-detail";
        }
    }

    @PostMapping(value = "/cancelVacationRequest")
    @ResponseBody
    public ResponseEntity<String> cancelVacationRequest(@RequestBody Map<String, Object> requestData) {
        Long vacId = Long.parseLong(requestData.get("vacationId").toString());
        Long empId = Long.parseLong(requestData.get("empId").toString());

        String vacStatus=requestData.get("vacStatus").toString();
        int diff=Integer.parseInt(requestData.get("diff").toString());

        vacationService.cancelVacationRequest(vacId,empId,vacStatus);
        vacationService.increaseVacation(diff,empId);
        return ResponseEntity.ok("성공");
    }

    @PostMapping("/updateMy")
    @PreAuthorize("isAuthenticated()") // 인증된 사용자만 접근 가능
    public String  updateMy(Emp emp, @RequestParam(value ="file") MultipartFile file , Model model , HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        Emp sessionEmp = (Emp) session.getAttribute("emp");

        if (sessionEmp != null) {
            // 세션에 저장된 사용자 정보와 요청으로 전달된 emp 정보 비교
            if (sessionEmp.getEmpId().equals(emp.getEmpId())) {
                System.out.println("updateMyController" + emp);
                System.out.println(file);

                myPageService.updateMy(emp, file);

                model.addAttribute("emp", emp);


                return "redirect:/index";
            }
        }
        return "error/403"; // 권한 없음
    }


//        return "redirect:/mypage/update-mypage";

//    @PostMapping("/updateMy")
//    public String  updateMy(Emp emp) {
//        Emp dbemp = hrService.selectEmpInfo(emp.getEmpId());
//        dbemp.setEmpPwd(emp.getEmpPwd());
//        dbemp.setEmpAddr(emp.getEmpAddr());
//        dbemp.setEmpPhone(emp.getEmpPhone()); //여기 비번두 추가, 맵퍼 쿼리문에도
//        dbemp.setEmpZipcode(emp.getEmpZipcode());
//        dbemp.setEmpDetailAddr(emp.getEmpDetailAddr());
//        myPageService.updateMy(dbemp);
//
//        return "redirect:/mypage/update-mypage";
//    }


}