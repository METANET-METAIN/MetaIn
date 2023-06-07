package com.metain.web.controller;

import com.metain.web.domain.CommonCert;
import com.metain.web.domain.Emp;
import com.metain.web.service.CertificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;



@Controller
public class CertificationController {

    @Autowired
    private CertificationService certificationService;
    
    // 재직증명서 관련
    @RequestMapping("/certification/emp-cert-show")
    public String empCertShow(){

        return "/certification/emp-cert-show";
    }

    
    //테스트apply 페이지 연결요청 및 데이터 보내기 테스트
    @RequestMapping(path = {"/certification/emp-cert-apply","/certification/exper-cert-apply","/certification/retire-cert-apply"})
    public  String empCertApply(HttpServletRequest request, Model model){

        // 요청 URL 가져오기
        String requestUrl = request.getRequestURI();

        //재직증명서 신청페이지 나오면 emp객체 넘겨줄거 여기에 작성
        // 유저 정보가 재직 상태와 퇴사일자를 포함한 가상의 예시 데이터라고 가정
        
        //model객체에 데이터 하나씩 넣어줄경우
//        model.addAttribute("empName", "홍길동");
//        model.addAttribute("empIdNum", 20163044);
//        model.addAttribute("empAddr", "광주광역시 광산구");
//        model.addAttribute("empGrade", "대리");
//        model.addAttribute("empDept", "개발팀");
//        model.addAttribute("empFirstDt", 20210824);


        //model객체에 객체데이터 넣어서 전달할 경우
            // empTest 객체 생성 전에 로깅 추가
            // System.out.println("empTest 객체 생성 전");

        //Emp empTest = new Emp(20163044L, "홍길동", "97년03월06일", "광주광역시 광산구", "대리", "개발팀");
        // model.addAttribute("empTest", empTest);

            // empTest 객체 생성 후에 로깅 추가
            //System.out.println("empTest 객체 생성 후 " + empTest.getEmpName());
    
        //세션에서 userId값 가져오는 과정  추가하기 ~~
        //String userId = (String) session.getAttribute("userId");

        // 사원의 정보를 데이터베이스에서 조회해서 가져오는 로직
        Long empId = 5L; //테스트 empId값 (로그인기능완료후 세션값에서 받아오도록 수정하기)
        System.out.println("empInfoList 객체 생성 전");
        Emp empInfoList = certificationService.getEmpInfoList(empId);
        System.out.println("empInfoList 객체 생성 후 " + empInfoList);

        if (empInfoList == null) {
            // 처리할 로직이 없는 경우
            return "index"; // 에러 페이지로 이동하거나 다른 처리를 수행
        }
        model.addAttribute("empInfoList", empInfoList);


        // 요청 URL에 따른 처리
        if (requestUrl.equals("/certification/emp-cert-apply")) {
            return "/certification/emp-cert-apply"; // "/certification/emp-cert-apply"에 대한 결과 페이지 반환
        } else if (requestUrl.equals("/certification/exper-cert-apply")) {
            return "/certification/exper-cert-apply"; // "/certification/exper-cert-apply"에 대한 결과 페이지 반환
        } else if (requestUrl.equals("/certification/retire-cert-apply")){
            return "/certification/retire-cert-apply"; //"/certification/retire-cert-apply"에 대한 결과 페이지 반환
        }else {
            return "/index"; // 그 외의 경우의 결과 페이지 반환
        }

       // return "/certification/emp-cert-apply"; //뷰 이름 리턴
    }
    
    
    //apply 정보입력 form에서 정보 넘겨받아서 empcert테이블에 insert할 메소드
    @PostMapping("/handleEmpCertInfo")
    public String handleEmpCertInfo(@ModelAttribute("commonCert")CommonCert commonCert, @RequestParam("certSort") String certSort, @RequestParam("selectedUseOfCert") String selectedUseOfCert, Model model) throws Exception {
        //empInfoList.setCertSort(certSort);
        commonCert.setUseOfCert(selectedUseOfCert);
        System.out.println("---------------------> tbl_empcert에 insert할때 필요한 증명서 정보 들어왔나 테스트 !!");
        System.out.println("---------------------> " + commonCert.getCertSort());
        System.out.println("---------------------> " + commonCert.getEmpId());
        System.out.println("---------------------> " + commonCert.getUseOfCert());

        // 전달된 데이터를 처리하는 로직 작성
        //certificationService.applyEmpCert(empInfoList);
        
        //분기 시도
        if (certSort.equals("A01")){ //증명서종류가 재직증명서일 경우
            //EmpCert 테이블에 insert되도록
            System.out.println("!!!!!재직증명서 테이블에 insert된다!!!!!!!!!");
            certificationService.applyEmpCert(commonCert);
        } else if (certSort.equals("A02")){ //증명서종류가 경력증명서일 경우
            //ExperCert 테이블에 insert되도록
            System.out.println("!!!!!경력증명서 테이블에 insert된다!!!!!!!!!");
            certificationService.applyExperCert(commonCert);
        }else if (certSort.equals("A03")){ //증명서종류가 퇴직증명서일 경우
            //RetireCert 테이블에 insert되도록
            System.out.println("!!!!!퇴직증명서 테이블에 insert된다!!!!!!!!!");
            certificationService.applyRetireCert(commonCert);
        }else {
            System.out.println("!!!!!분기 안됐어 실패!!!!!!!!!");
            return "index"; //에러창 만들어서 넣기
        }

        // certSort 값을 Model 객체에 추가하여 View로 전달
        model.addAttribute("certSort", certSort);

        return "/certification/emp-cert-complete";
    }

    @RequestMapping("/certification/emp-cert-complete")
    public  String empCertComplete(){


        return "/certification/emp-cert-complete";
    }


    
    //인쇄 테스트 페이지
    @RequestMapping("/certification/my-cert-list")
    public String myCertList(){
        return "certification/my-cert-list";
    }

    //경력증명서 관련
    @RequestMapping("/certification/exper-cert-show")
    public String experCertApply(){

        return "/certification/exper-cert-show";
    }

//    @RequestMapping("/certification/exper-cert-apply")
//    public String experCertShow(){
//
//        return "/certification/exper-cert-apply";
//    }

    
    //퇴직증명서 관련
    @RequestMapping("/certification/retire-cert-show")
    public String retireCertApply(){

        return "/certification/retire-cert-show";
    }

//    @RequestMapping("/certification/retire-cert-apply")
//    public String retireCertShow(){
//
//        return "/certification/retire-cert-apply";
//    }




}
