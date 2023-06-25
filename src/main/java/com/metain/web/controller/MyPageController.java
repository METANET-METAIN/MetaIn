package com.metain.web.controller;

import com.metain.web.domain.*;
import com.metain.web.dto.AlarmDTO;
import com.metain.web.dto.MyVacDTO;
import com.metain.web.dto.VacationFileDTO;
import com.metain.web.dto.VacationWithoutFileDTO;
import com.metain.web.mapper.FileMapper;
import com.metain.web.service.AwsS3Service;
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
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

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
    private AwsS3Service awsS3Service;

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

    public List<EmpCert> selectMyEmpCert(Authentication auth) {

        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal(); //로그인해져있는 토큰가져오기
        Long empId = principalDetails.getEmpId();

        return myPageService.selectMyEmpCert(empId);
    }


    //경력증명서 리스트
    @GetMapping("/my-experCert")
    @ResponseBody
    public List<ExperienceCert> selectMyExperCert(Authentication auth) {
        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal(); //로그인해져있는 토큰가져오기
        Long empId = principalDetails.getEmpId();

        return myPageService.selectMyExperCert(empId);
    }


    //퇴직증명서 리스트
    @GetMapping("/my-retireCert")
    @ResponseBody
    public List<RetireCert> selectMyRetCert(Authentication auth) {
        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal(); //로그인해져있는 토큰가져오기
        Long empId = principalDetails.getEmpId();

        return myPageService.selectMyRetCert(empId);
    }

    //파일 다운로드 S3적용 시도
    @ResponseBody
    @Transactional
    @PostMapping("/downloadCert/{certId}/{certSort}")
    public ResponseEntity<String> downloadCert(@PathVariable("certId") Long certId, @PathVariable("certSort") String certSort) throws IOException {

        logger.info("MyPgaeCon/ downloadCert/ certid= ",certId);
        logger.info("MyPgaeCon/ downloadCert/ certSort= ",certSort);
        //받아온 certId를 이용해서 파일이름 을 얻어오기
        String filename = myPageService.getCertFilename(certId, certSort) + ".pdf"; //다운로드할 PDF 파일명 - 디지털서명된 파일이름 empcert같은 객체에서 가져오기

        logger.info("MyPgaeCon/ downloadCert의 filename= ",filename);

        //다운로드한번 받으면 issueStatus 값 1로 업데이트 서비스메소드
        myPageService.updateIssueStatus(certId, certSort);

        //해당객체 S3 url 생성 -> 뷰단에 넘길 url
        String signedCertURL = "https://metain2.s3.ap-northeast-2.amazonaws.com/certification/" + filename;
        logger.info("MyPgaeCon/ downloadCert의 signedCertURL= ",signedCertURL);

        return ResponseEntity.ok(signedCertURL);
    }//downloadCert

    @GetMapping("/my-vac-list")

    public String myVacList(Authentication auth, Model model) {
        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
        Long empId = principalDetails.getEmpId();
        Emp emp = hrService.selectEmpInfo(empId);
        List<MyVacDTO> myList = myPageService.myVacList(empId);
        logger.info("MypageCon의 myVacList의 myList= ",myList);
        model.addAttribute("vacList", myList);
        model.addAttribute("emp", emp);
        return "/mypage/my-vac-list";
    }

    @GetMapping("/alarm")
    public String alarmList(Authentication auth, Model model) {
        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
        Long empId = principalDetails.getEmpId();
        Emp emp = hrService.selectEmpInfo(empId);

        List<AlarmDTO> alarmList = myPageService.alarmList(empId);
        model.addAttribute("alarmList", alarmList);
        model.addAttribute("emp", emp);
        return "/mypage/alarm";
    }

    @GetMapping("/my-vac-detail/{vacationId}")
    public String myVacDetail(@PathVariable("vacationId") Long vacationId, Model model, Authentication auth) {
        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal();
        Long empId = principalDetails.getEmpId();
        Emp empInfo = hrService.selectEmpInfo(empId);
        if (vacationId == null) {
            new ModelAndView("redirect:/vacation/vacation-list");// vacationId가 없을 경우 기본 페이지로 리다이렉션
        }
        VacationFileDTO vac = vacationService.vacationDetail(vacationId);
        logger.info("MyPgaeCon의 myVacDetail의 vac= ",vac);

        if (vac == null) {
            VacationWithoutFileDTO vacWithoutFile = vacationService.vacationDetailWithoutFile(vacationId);
            logger.info("MyPgaeCon의 myVacDetail의 vacWithoutFile= ",vacWithoutFile);
            //신청한 사람이자
            Emp emp = hrService.selectEmpInfo(vacWithoutFile.getEmpId());
            //관리자 정보
            Emp admin = hrService.selectEmpInfo(vacWithoutFile.getAdmId());
            //총 사용날짜 구하기
            java.util.Date startDate = new java.util.Date(vacWithoutFile.getVacStartDate().getTime());
            java.util.Date endDate = new java.util.Date(vacWithoutFile.getVacEndDate().getTime());

            long diff = endDate.getTime() - startDate.getTime();
            int daysDiff = (int) (diff / (24 * 60 * 60 * 1000) + 1);
            logger.info("MyPgaeCon의 myVacDetail의 vacWithoutFile2= ",vacWithoutFile);

            model.addAttribute("vac", vacWithoutFile);
            model.addAttribute("emp", empInfo);
            model.addAttribute("admin", admin);
            model.addAttribute("diff", daysDiff);
            model.addAttribute("req", emp); //신청한 사람
            return "/mypage/my-vac-detail";
        } else {
            //신청한 사람
            Emp emp = hrService.selectEmpInfo(vac.getEmpId());
            //관리자 정보
            Emp admin = hrService.selectEmpInfo(vac.getAdmId());
            //총 사용날짜 구하기
            java.util.Date startDate = new java.util.Date(vac.getVacStartDate().getTime());
            java.util.Date endDate = new java.util.Date(vac.getVacEndDate().getTime());

            long diff = endDate.getTime() - startDate.getTime();
            int daysDiff = (int) (diff / (24 * 60 * 60 * 1000) + 1);
            String filePath = fileMapper.getFilePath(vac.getFileId());
            model.addAttribute("vac", vac);
            model.addAttribute("emp", empInfo); //관리자로 로그인한 유저
            model.addAttribute("admin", admin);
            model.addAttribute("diff", daysDiff);
            model.addAttribute("file", filePath);
            model.addAttribute("req", emp); //신청한 사람
            return "/mypage/my-vac-detail";
        }
    }

    @PostMapping(value = "/cancelVacationRequest")
    @ResponseBody
    public ResponseEntity<String> cancelVacationRequest(@RequestBody Map<String, Object> requestData) {
        Long vacId = Long.parseLong(requestData.get("vacationId").toString());
        Long empId = Long.parseLong(requestData.get("empId").toString());

        String vacStatus = requestData.get("vacStatus").toString();
        int diff = Integer.parseInt(requestData.get("diff").toString());

        vacationService.cancelVacationRequest(vacId, empId, vacStatus);
        vacationService.increaseVacation(diff, empId);
        return ResponseEntity.ok("성공");
    }

    @PostMapping("/updateMy")
    public String updateMy(Emp emp, @RequestParam(value = "file") MultipartFile file) throws IOException {

        logger.info("MyPgaeCon의 updateMy emp= ",emp);
        logger.info("MyPgaeCon의 updateMy file= ",file);

        myPageService.updateMy(emp, file);

        return "redirect:/index";
    }

    @PostMapping("/updatePwd")
    public String updatePassword(Emp emp) {

        myPageService.updatePwd(emp);
        // 업데이트 성공 시 응답 처리
        return "redirect:/index";
    }
}