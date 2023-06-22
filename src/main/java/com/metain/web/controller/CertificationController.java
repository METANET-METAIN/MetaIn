package com.metain.web.controller;


import com.metain.web.domain.*;
import com.metain.web.dto.CertInfoDTO;
import com.metain.web.dto.ImageRequestData;

import com.metain.web.service.CertificationService;
import com.metain.web.service.HrService;
import com.metain.web.service.MyPageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;


import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping("/certification")
public class CertificationController {

    @Autowired
    private CertificationService certificationService;
    @Autowired
    private MyPageService myPageService;
    @Autowired
    private HrService hrService;


    //각 증명서 별로 apply 페이지 연결요청 및 데이터 보내기 테스트
    @RequestMapping(path = {"/emp-cert-apply", "/exper-cert-apply", "/retire-cert-apply"})
    public String empCertApply(HttpServletRequest request, Model model, Authentication auth) {

        //로그인한 유저 정보 가져오기
        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal(); //로그인해져있는 토큰가져오기
        Long empId = principalDetails.getEmpId();
        Emp empInfo = certificationService.getEmpInfoList(empId); //직급 변환 처리된 emp객체 가져오기

        // 요청 URL 가져오기
        String requestUrl = request.getRequestURI();

        if (empInfo == null) {
            // 처리할 로직이 없는 경우
            System.out.println("empInfo 못가져왔어 !");
        } else {
            model.addAttribute("emp", empInfo);
            System.out.println("empInfoList 객체 생성 후 " + empInfo);
        }//if

        // 요청 URL에 따른 처리
        if (requestUrl.equals("/certification/emp-cert-apply")) {
            return "/certification/emp-cert-apply"; // "/certification/emp-cert-apply"에 대한 결과 페이지 반환
        } else if (requestUrl.equals("/certification/exper-cert-apply")) {
            return "/certification/exper-cert-apply"; // "/certification/exper-cert-apply"에 대한 결과 페이지 반환
        } else if (requestUrl.equals("/certification/retire-cert-apply")) {
            return "/certification/retire-cert-apply"; //"/certification/retire-cert-apply"에 대한 결과 페이지 반환
        } else {
            return "/index"; // 그 외의 경우의 결과 페이지 반환
        }//if

    }//empCertApply


    //apply 정보입력 form에서 정보 넘겨받아서 각 cert테이블에 insert할 메소드
    @PostMapping("/handleEmpCertInfo")
    public String handleEmpCertInfo(@ModelAttribute("certInfoDTO") CertInfoDTO certInfoDTO, @RequestParam("certSort") String certSort, @RequestParam("selectedUseOfCert") String selectedUseOfCert, Model model, Authentication auth) throws Exception {

        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal(); //로그인해져있는 토큰가져오기
        Long empId = principalDetails.getEmpId();
        Emp empInfo = certificationService.getEmpInfoList(empId);

        if (empInfo == null) {
            System.out.println("empInfo 못가져왔어 !");
        } else {
            model.addAttribute("emp", empInfo);
        }//if

        System.out.println("certInfo 내용물  " + certInfoDTO);
        certInfoDTO.setUseOfCert(selectedUseOfCert);
        System.out.println("---------------------> tbl_empcert에 insert할때 필요한 증명서 정보 들어왔나 테스트 !!");
        System.out.println("---------------------> " + certInfoDTO.getCertSort());
        System.out.println("---------------------> " + certInfoDTO.getEmpId());
        System.out.println("---------------------> " + certInfoDTO.getUseOfCert());

        //각 증명서별로 분기
        if (certSort.equals("A01")) { //증명서종류가 재직증명서일 경우
            //EmpCert 테이블에 insert되도록
            System.out.println("!!!!!재직증명서 테이블에 insert된다!!!!!!!!!");
            EmpCert list = certificationService.applyAndSelectEmpCert(certInfoDTO);
            model.addAttribute("certList", list);

        } else if (certSort.equals("A02")) { //증명서종류가 경력증명서일 경우
            //ExperCert 테이블에 insert되도록
            System.out.println("!!!!!경력증명서 테이블에 insert된다!!!!!!!!!");
            ExperienceCert list = certificationService.applyAndSelectExperCert(certInfoDTO);
            model.addAttribute("certList", list);
        } else if (certSort.equals("A03")) { //증명서종류가 퇴직증명서일 경우
            //RetireCert 테이블에 insert되도록
            System.out.println("!!!!!퇴직증명서 테이블에 insert된다!!!!!!!!!");
            RetireCert list = certificationService.applyAndSelectRetireCert(certInfoDTO);
            model.addAttribute("certList", list);

        } else {
            System.out.println("!!!!!분기 안됐어 실패!!!!!!!!!");
            return "/index"; //에러창 만들어서 넣기
        }//if

        // certSort 값을 Model 객체에 추가하여 View로 전달
        model.addAttribute("certSort", certSort);

        //selct해온 것도 바로 model객체에 추가해서 view로 보내기 -> 증명서에 값 넣도록
        return "/certification/cert-complete";
    }//handleEmpCertInfo


    //증명서HTML to PDF 변환 & 디지털 서명 추가
    @PostMapping("/convertToPdf")
    @ResponseBody
    public void convertToPdf(@RequestBody ImageRequestData request, Model model, Authentication auth) throws IOException {


        PrincipalDetails principalDetails = (PrincipalDetails) auth.getPrincipal(); //로그인해져있는 토큰가져오기
        Long empId = principalDetails.getEmpId();
        Emp empInfo = certificationService.getEmpInfoList(empId);

        if (empInfo == null) {
            // 처리할 로직이 없는 경우
            System.out.println("empInfo 못가져왔어 !");
        } else {
            model.addAttribute("emp", empInfo);
        }

        System.out.println(" convertToPdf 메소드 불렸나 확인  ");
        certificationService.makeCertPdf(request);
        //System.out.println("ImageRequestData확인 : ~~~ "+request);

        String certSort = request.getCertSort();
        Long certId = request.getCertId();
        //pdf파일에 전자서명 추가한거 저장하기전에 저장해야할 파일명 가져오기
        String filename = myPageService.getCertFilename(certId, certSort) + ".pdf"; //다운로드할 PDF 파일명 - 디지털서명된 파일이름 empcert같은 객체에서 가져오기
        System.out.println("디지털서명파일저장할 파일이름 확인 " + filename);


        //저장된 pdf파일에 전자서명 추가하기
        try {
            System.out.println("catch문 탔나?????");
            System.out.println("filename도 넘어왔나??" + filename);
            certificationService.signPdf(filename);
            System.out.println("4!! 디지털서명 완료");
        } catch (Exception e) {
            System.out.println("디지털서명 실패 ");
            System.out.print("에러기록 : " + e.toString());
            e.printStackTrace();
        }//catch

    }//convertToPdf

}//
